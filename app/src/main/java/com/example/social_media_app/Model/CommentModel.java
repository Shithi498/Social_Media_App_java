package com.example.social_media_app.Model;

public class CommentModel {
    int profile;
    String comment,commentedBy;

    public CommentModel(int profile, String comment, String time) {
        this.profile = profile;
        this.comment = comment;

    }

    public CommentModel() {
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public String getCommentedBy() {
        return commentedBy;
    }

    public void setCommentedBy(String commentedBy) {
        this.commentedBy = commentedBy;
    }


}
