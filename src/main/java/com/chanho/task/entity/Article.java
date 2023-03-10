package com.chanho.task.entity;

import com.chanho.task.dto.ArticleRequestDto;

public class Article {
    private long articleId;
    private int boardId;
    private String createdDatetime;
    private boolean isPinned;
    private int viewCount;
    private String title;
    private String contentHtml;
    private String contentString;

    public Article(ArticleRequestDto requestDto) {
        setBoardId(requestDto.getBoardId());
        setTitle(requestDto.getTitle());
        setContentHtml(requestDto.getContent());
        setContentString(requestDto.getContent());
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public String getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(String createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean pinned) {
        isPinned = pinned;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public String getContentString() {
        return contentString;
    }

    public void setContentString(String contentString) {
        this.contentString = contentString;
    }
}
