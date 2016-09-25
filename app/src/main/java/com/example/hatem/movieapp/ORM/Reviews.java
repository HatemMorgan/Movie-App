package com.example.hatem.movieapp.ORM;

/**
 * Created by hatem on 9/24/16.
 */
public class Reviews {

    private  String reviewID;
    private String authorName ;
    private String reviewContent;
    private  String authorID;
    private String author_profile_path ;

    public String getReviewID() {
        return reviewID;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public String getAuthorID() {
        return authorID;
    }

    public String getAuthor_profile_path() {
        return author_profile_path;
    }

    public Reviews(String  reviewID, String reviewContent, String authorName, String authorID, String author_profile_path) {

        this.reviewID = reviewID;
        this.reviewContent = reviewContent;
        this.authorName = authorName;
        this.authorID = authorID;
        this.author_profile_path = author_profile_path;
    }
}
