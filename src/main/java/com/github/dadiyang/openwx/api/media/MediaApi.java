package com.github.dadiyang.openwx.api.media;

import com.github.dadiyang.httpinvoker.annotation.HttpApi;
import com.github.dadiyang.httpinvoker.annotation.HttpReq;
import com.github.dadiyang.httpinvoker.annotation.Param;

/**
 * @author huangxuyang
 * @date 2019/1/1
 */
@HttpApi(prefix = "${openwx.host}/openwx")
public interface MediaApi {
    /**
     * 上传媒体文件，获取media_id, 用于稍后发送
     *
     * @param mediaPath 媒体的路径，可以是本地路径或url地址
     * @return 媒体信息
     */
    @HttpReq(value = "/upload_media", method = "POST")
    MediaInfo uploadMedia(@Param("client") String client, @Param("media_path") String mediaPath);
}
