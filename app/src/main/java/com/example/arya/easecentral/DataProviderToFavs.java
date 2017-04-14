package com.example.arya.easecentral;

/**
 * Created by arya's on 4/10/2017.
 */
public class DataProviderToFavs {
    /**
     * This class provides the constructor, getter and setter methods
     * which are needed for the DataProviderToFavsAdapter.java class
     */
    private String image;
    private String title;
    private String url;
    private String comments;

    public DataProviderToFavs(String image,String title, String url, String comments) {
        this.image = image;
        this.title = title;
        this.url = url;
        this.comments = comments;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
