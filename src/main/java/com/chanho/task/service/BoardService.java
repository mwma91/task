package com.chanho.task.service;

import com.chanho.task.dto.*;
import com.chanho.task.entity.Article;
import com.chanho.task.entity.Image;
import com.chanho.task.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BoardService {

    private final BoardMapper boardMapper;

    @Autowired
    public BoardService(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    public List<BoardResponseDto> listBoards() {
        return boardMapper.listBoards();
    }

    public List<ArticleListResponseDto> listArticles(int boardId) {
        List<ArticleListResponseDto> articles = boardMapper.listArticles(boardId);
        findThumbnails(articles);

        return articles;
    }

    @Transactional(rollbackFor = Exception.class)
    public long createArticle(ArticleRequestDto requestDto) {
        Article article = new Article(requestDto);
        List<Long> imageIds = requestDto.getImageIds();
        if (boardMapper.createArticle(article) == 0) {
            throw new IllegalArgumentException();
        }
        if (imageIds == null || imageIds.size() == 0) {
            return article.getArticleId();
        }
        if (boardMapper.updateImages(article.getArticleId(), imageIds) == 0) {
            throw new IllegalArgumentException();
        }
        return article.getArticleId();
    }

    @Transactional(rollbackFor = Exception.class)
    public ArticleResponseDto retrieveArticle(long articleId) {
        ArticleResponseDto articleResponseDto = boardMapper.retrieveArticle(articleId);
        if (articleResponseDto != null) {
            List<String> urls = boardMapper.findImages(articleResponseDto.getArticleId());
            articleResponseDto.setImageUrls(urls);
            if (boardMapper.increaseViewcount(articleId) == 0) {
                throw new IllegalArgumentException();
            }
        }
        return articleResponseDto;
    }

    public int deleteArticle(long articleId) {
        if (boardMapper.deleteArticle(articleId) == 0) {
            throw new IllegalArgumentException();
        }
        return 1;
    }

    public List<ArticleListResponseDto> searchBoards(String boardName) {
        List<ArticleListResponseDto> articles = boardMapper.searchBoards(boardName);
        findThumbnails(articles);
        return articles;
    }

    public List<ArticleListResponseDto> searchArticles(String keyword) {
        List<ArticleListResponseDto> articles = boardMapper.searchArticles(keyword);
        findThumbnails(articles);
        return articles;
    }

    public List<ArticleListResponseDto> searchArticlesBetween(DateDto date) {
        List<ArticleListResponseDto> articles = boardMapper.searchArticlesBetween(date);
        findThumbnails(articles);
        return articles;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Long> createImages(Map<String, String> images) {
        List<Long> imageIds = new ArrayList<>();
        for (var imageFileName : images.keySet()) {
            ImageDto imageDto = new ImageDto();
            imageDto.setImageFileName(imageFileName);
            imageDto.setImageUrl(images.get(imageFileName));
            if (boardMapper.createImage(imageDto) == 0) {
                throw new IllegalArgumentException();
            }
            imageIds.add(imageDto.getImageId());
        }
        return imageIds;
    }

    private void findThumbnails(List<ArticleListResponseDto> articles) {
        List<Image> thumbnails = boardMapper.findThumbnails(articles);

        for (var thumbnail : thumbnails) {
            for (var article : articles) {
                if (thumbnail.getArticleId() == article.getArticleId()) {
                    article.setThumbnailUrl(thumbnail.getUrl());
                    break;
                }
            }
        }
    }
}
