package com.example.social_media_app.Model;

public class NotiModel {

private String notiBy,type,postId,postedBy,notificationId,notiText;


    public NotiModel(String notiBy, String type, String postId, String postedBy) {
        this.notiBy = notiBy;
        this.type = type;
        this.postId = postId;
        this.postedBy = postedBy;
    }

    public NotiModel() {
    }

    public String getNotiBy() {
        return notiBy;
    }

    public void setNotiBy(String notiBy) {
        this.notiBy = notiBy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotiText() {
        return notiText;
    }

    public void setNotiText(String notiText) {
        this.notiText = notiText;
    }
}
