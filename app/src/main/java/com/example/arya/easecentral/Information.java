package com.example.arya.easecentral;

/**
 * Created by arya's on 4/10/2017.
 */
public class Information {
    /**
     * This class provides the constructor, getter and setter method which are needed for the InformationAdapter.java class
     */
    private String image;
    private String title;
    private String url;
    private String comments;

    public Information(String image,String title,String url, String comments){
        this.setImage(image);
        this.setTitle(title);
        this.setUrl(url);
        this.setComments(comments);

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
