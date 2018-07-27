package com.bluewebspark.happyservice.model;

/**
 * Created by abc on 07-Mar-18.
 */

public class ArticlesModel {
    String articleId;
    String articleName;
    int articleImage;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public int getArticleImage() {
        return articleImage;
    }

    public void setArticleImage(int articleImage) {
        this.articleImage = articleImage;
    }
}
