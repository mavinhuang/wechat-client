package com.github.dadiyang.openwx.api.spring;

import com.github.dadiyang.httpinvoker.HttpApiProxyFactory;
import com.github.dadiyang.openwx.api.client.ClientApi;
import com.github.dadiyang.openwx.api.friend.FriendApi;
import com.github.dadiyang.openwx.api.group.GroupApi;
import com.github.dadiyang.openwx.api.media.MediaApi;
import com.github.dadiyang.openwx.api.message.MessageApi;
import com.github.dadiyang.openwx.api.user.UserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 整合spring，将所有的 api 接口注册为Bean
 *
 * @author huangxuyang
 * @date 2019/1/1
 */
@Configuration
public class ApiConfiguration {
    private static final String CLASSPATH_PRE = "classpath:";
    @Autowired(required = false)
    private HttpApiProxyFactory httpApiProxyFactory;
    private @Value("${openwx.api.configPath:}")
    String configPath;

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        if (httpApiProxyFactory != null) {
            return;
        }
        if (configPath != null && !configPath.isEmpty()) {
            Properties p = new Properties();
            if (configPath.startsWith(CLASSPATH_PRE)) {
                // load from class path;
                try {
                    p.load(getClass().getClassLoader().getResourceAsStream(configPath.replace(CLASSPATH_PRE, "")));
                } catch (IOException e) {
                    throw new IllegalStateException("connot initialize openwx apis, read config error: " + configPath, e);
                }

            } else {
                // load from file
                try {
                    p.load(new FileInputStream(configPath));
                } catch (IOException e) {
                    throw new IllegalStateException("connot initialize openwx apis, read config error: " + configPath, e);
                }
            }
            this.httpApiProxyFactory = new HttpApiProxyFactory(p);
        } else {
            this.httpApiProxyFactory = new HttpApiProxyFactory();
        }
    }

    @Bean
    public ClientApi clientApi() {
        return httpApiProxyFactory.getProxy(ClientApi.class);
    }

    @Bean
    public FriendApi friendApi() {
        return httpApiProxyFactory.getProxy(FriendApi.class);
    }

    @Bean
    public GroupApi groupApi() {
        return httpApiProxyFactory.getProxy(GroupApi.class);
    }

    @Bean
    public MediaApi mediaApi() {
        return httpApiProxyFactory.getProxy(MediaApi.class);
    }

    @Bean
    public MessageApi messageApi() {
        return httpApiProxyFactory.getProxy(MessageApi.class);
    }

    @Bean
    public UserApi userApi() {
        return httpApiProxyFactory.getProxy(UserApi.class);
    }
}
