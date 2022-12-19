package com.chanho.task;

import com.chanho.task.controller.BoardController;
import com.chanho.task.dto.ArticleRequestDto;
import com.chanho.task.dto.ArticleResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@Transactional
class BoardTest {

    @Autowired
    private BoardController boardController;

    @Test
    @DisplayName("게시판 목록 조회")
    void listBoards() {
        var boards = boardController.listBoards();
        assertThat(boards).isNotNull();
        assertThat(boards).isNotEmpty();
    }

    @Test
    @DisplayName("게시글 목록 조회")
    void listArticles() {
        // boardId에 잘못된 값이 들어가면 기본값(1)으로 수정
        var articles = boardController.listArticles(-1);
        assertThat(articles).isNotNull();
    }

    @Test
    @DisplayName("제시글 작성, 조회, 삭제")
    void createArticle() {
        // 작성할 게시글 개수
        int articleCount = 2;
        // 게시글 pk를 저장할 리스트
        List<Long> articleIds = new ArrayList<>();

        var requestDto = new ArticleRequestDto();
        requestDto.setBoardId(2);

        for (int i = 0; i < articleCount; i++) {
            // 이미 어딘가에 이미지 파일이 저장된 상태라고 가정하고, 해당 이미지 url과 파일명을 hashmap 형태로 전달
            Map<String, String> images = new HashMap<>();
            for (int j = 0; j < 3; j++) {
                images.put("FileName" + j, "URL" + j);
            }
            // 이미지 테이블에 저장하고 PK를 반환
            var imageIds = boardController.createImages(images);

            requestDto.setTitle("테스트용 제목" + i);
            requestDto.setContent("테스트용 내용" + i);
            requestDto.setImageIds(imageIds);
            articleIds.add(boardController.createArticle(requestDto));
        }

        // articleId값(PK)이 제대로 증가되는지
        assertThat(articleIds.get(0) + 1).isEqualTo(articleIds.get(1));

        // 작성한 게시글 조회하기
        ArticleResponseDto selectedArticle1 = boardController.retrieveArticle(articleIds.get(0));
        assertThat(selectedArticle1).isNotNull();
        // 조회시 잘못된 값을 넣으면 예외 발생
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> boardController.retrieveArticle(0));

        // 입력한 값이 제대로 들어갔는지
        assertThat(selectedArticle1.getTitle()).isEqualTo("테스트용 제목0");
        assertThat(selectedArticle1.getContentHtml()).isEqualTo("테스트용 내용0");

        // 작성한 게시글 삭제하기
        assertThat(boardController.deleteArticle(articleIds.get(0))).isEqualTo(1);
        // 잘못된 값을 넣으면 예외 발생
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> boardController.deleteArticle(-1));

        // 삭제한 게시글을 조회하면 null이 나오는지
        assertThat(boardController.retrieveArticle(articleIds.get(0))).isNull();
    }

    @Test
    @DisplayName("게시판 이름으로 검색")
    void searchBoards() {
        // 공백이나 null이 들어가면 예외 발생
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            boardController.searchBoards("");
            boardController.searchBoards(null);
        });
    }

    @Test
    void searchArticles() {
        var requestDto = new ArticleRequestDto();
        requestDto.setBoardId(2);
        // 제목과 내용에 "테스트"가 들어가는 게시글을 생성하고
        for (int i = 0; i < 3; i++) {
            requestDto.setTitle("테스트용 제목" + i);
            requestDto.setContent("테스트용 내용" + i);
            boardController.createArticle(requestDto);
        }
        // "테스트"가 들어가지 않는 게시글을 생성한 뒤
        requestDto.setTitle("제목");
        requestDto.setContent("내용");
        boardController.createArticle(requestDto);
        // "테스트"로 검색했을 때 들어가지 않은 게시글이 나오는지
        assertThat(boardController.searchArticles("테스트").size()).isEqualTo(3);
    }
}
