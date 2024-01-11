package com.example.social_media_app.Model;

import android.widget.ImageView;

public class FriendsModel {
    private String followedBy;
    private long followedAt;

    public FriendsModel() {

    }


    public String getFollowedBy() {
        return followedBy;
    }

    public void setFollowedBy(String followedBy) {
        this.followedBy = followedBy;
    }

    public long getFollowedAt() {
        return followedAt;
    }

    public void setFollowedAt(long followedAt) {
        this.followedAt = followedAt;
    }
}
