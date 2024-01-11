package com.example.social_media_app.Model;

public class PostModel {
   private String postId;
    private String postImage;
    private String postedBy;
    private String writePost;





   private int postLike,commentCount;


    public PostModel(String postId, String postImage, String postedBy, String postDes) {
        this.postId = postId;
        this.postImage = postImage;
        this.postedBy = postedBy;

    }

    public PostModel() {
    }



    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }


    public String getWritePost() {
        return writePost;
    }

    public void setWritePost(String writePost) {
        this.writePost = writePost;
    }

    public int getPostLike() {
        return postLike;
    }

    public void setPostLike(int postLike) {
        this.postLike = postLike;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
