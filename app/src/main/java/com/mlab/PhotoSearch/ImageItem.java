package com.mlab.PhotoSearch;

import android.widget.ImageView;

public class ImageItem {
    private String thumbsID;
    private String title;
    private String date;

    public ImageItem(String thumbsID, String title, String date) {
        super();
        this.thumbsID = thumbsID;
        this.title = title;
        this.date = date;
    }

    public String getThumbsID() {
        return thumbsID;
    }

    public void setThumbsID(String thumbsID) {
        this.thumbsID = thumbsID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
