package com.chanho.task.service;

import com.chanho.task.dto.*;
import com.chanho.task.entity.Article;
import com.chanho.task.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return boardMapper.listArticles(boardId);
    }

    public int createArticle(ArticleRequestDto requestDto) {
        Article article = new Article(requestDto);
        if (boardMapper.createArticle(article) == 1) {
            return article.getArticleId();
        }
        return 0;
    }

    public ArticleResponseDto retrieveArticle(int articleId) {
        return boardMapper.retrieveArticle(articleId);
    }

    public int deleteArticle(int articleId) {
        return boardMapper.deleteArticle(articleId);
    }

    public List<ArticleListResponseDto> searchBoards(String boardName) {
        return boardMapper.searchBoards(boardName);
    }

    public List<ArticleListResponseDto> searchArticles(String keyword) {
        return boardMapper.searchArticles(keyword);
    }

    public List<ArticleListResponseDto> searchArticlesBetween(DateDto date) {
        return boardMapper.searchArticlesBetween(date);
    }
}
