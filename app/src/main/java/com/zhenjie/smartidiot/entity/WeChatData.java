package com.zhenjie.smartidiot.entity;

/**
 * 文件名: WeChatData
 * 创建者: Jack Yan
 * 创建日期: 2018/9/26 6:58 PM
 * 邮箱: cn.zhenjie@gmail.com
 * 描述：微信精选信息实体类
 */
public class WeChatData {
    private String title;
    private String source;
    private String imgUrl;
    private String newsUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }
}
