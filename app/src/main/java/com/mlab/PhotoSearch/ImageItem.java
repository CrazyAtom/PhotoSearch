package com.mlab.PhotoSearch;

public class ImageItem {
    private String mediaID;
    private String thumbsID;
    private String date;

    public ImageItem(String mediaID, String thumbsID, String date) {
        super();
        this.mediaID = mediaID;
        this.thumbsID = thumbsID;
        this.date = date;
    }

    public String getMediaID() {
        return mediaID;
    }

    public void setMediaID(String mediaID) {
        this.mediaID = mediaID;
    }

    public String getThumbsID() {
        return thumbsID;
    }

    public void setThumbsID(String thumbsID) {
        this.thumbsID = thumbsID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
