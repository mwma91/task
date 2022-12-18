package com.chanho.task.dto;

import java.util.List;

public class ImageRequestDto {
    private int articleId;
    private List<String> images;

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
