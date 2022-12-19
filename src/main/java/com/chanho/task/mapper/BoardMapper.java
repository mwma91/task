package com.chanho.task.mapper;

import com.chanho.task.dto.*;
import com.chanho.task.entity.Article;
import com.chanho.task.entity.Image;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardResponseDto> listBoards();

    List<ArticleListResponseDto> listArticles(@Param("boardId") int boardId);

    List<Image> findThumbnails(@Param("articles") List<ArticleListResponseDto> articles);

    int createImage(@Param("imageDto") ImageDto imageDto);

    int createArticle(@Param("article") Article article);

    int updateImages(@Param("articleId") long articleId, @Param("imageIds") List<Long> imageIds);

    ArticleResponseDto retrieveArticle(@Param("articleId") long articleId);

    List<String> findImages(@Param("articleId") long articleId);

    int increaseViewcount(@Param("articleId") long articleId);

    int deleteArticle(@Param("articleId") long articleId);

    List<ArticleListResponseDto> searchBoards(@Param("boardName") String boardName);

    List<ArticleListResponseDto> searchArticles(@Param("keyword") String keyword);

    List<ArticleListResponseDto> searchArticlesBetween(@Param("date") DateDto date);
}
