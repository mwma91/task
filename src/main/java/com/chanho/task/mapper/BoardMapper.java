package com.chanho.task.mapper;

import com.chanho.task.dto.ArticleListResponseDto;
import com.chanho.task.dto.ArticleResponseDto;
import com.chanho.task.dto.BoardResponseDto;
import com.chanho.task.entity.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BoardMapper {
    @Select("SELECT * FROM cms__board")
    List<BoardResponseDto> listBoards();

    @Insert("""
            INSERT INTO cms__article (board_id, title, content_html, content_string)
            VALUES(
                   #{article.boardId},
                   #{article.title},
                   #{article.contentHtml},
                   #{article.contentString}
                  )
            """)
    @Options(useGeneratedKeys = true, keyProperty = "articleId")
    int createArticle(@Param("article") Article article);

    @Select("SELECT * FROM cms__article WHERE board_id = #{board_id}")
    List<ArticleListResponseDto> listArticles(@Param("board_id") int boardId);

    @Select("SELECT * FROM cms__article WHERE article_id = #{id}")
    ArticleResponseDto findByArticleId(@Param("article_id") int id);

    @Select("""
            SELECT a.*, b.name_ko
            FROM cms__article AS a
            INNER JOIN cms__board AS b
            ON a.board_id = b.board_id
            WHERE a.article_id = #{article_id}
            """)
    ArticleResponseDto retrieveArticle(@Param("article_id") int articleId);

    @Delete("DELETE FROM cms__article WHERE article_id = #{articleId}")
    int deleteArticle(int articleId);
}
