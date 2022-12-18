package com.chanho.task.mapper;

import com.chanho.task.dto.ArticleListResponseDto;
import com.chanho.task.dto.ArticleResponseDto;
import com.chanho.task.dto.BoardResponseDto;
import com.chanho.task.dto.DateDto;
import com.chanho.task.entity.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardResponseDto> listBoards();

    List<ArticleListResponseDto> listArticles(int boardId);

    int createArticle(Article article);
    
    ArticleResponseDto retrieveArticle(int articleId);

    int deleteArticle(int articleId);

    List<ArticleListResponseDto> searchBoards(String boardName);

    List<ArticleListResponseDto> searchArticles(String keyword);

    List<ArticleListResponseDto> searchArticlesBetween(DateDto dateBetween);
}
