package com.chanho.task.controller;

import com.chanho.task.dto.*;
import com.chanho.task.service.BoardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 게시판 목록 보기
    @GetMapping("/boards")
    public List<BoardResponseDto> listBoards() {
        return boardService.listBoards();
    }

    // 특정 게시판의 게시글 목록 보기
    @GetMapping("/boards/{boardId}")
    public List<ArticleListResponseDto> listArticles(@PathVariable int boardId) {
        if (boardId < 1) {
            boardId = 1;
        }
        return boardService.listArticles(boardId);
    }

    // 이미지 업로드하기
    @PostMapping("/images")
    public List<Long> createImages(@RequestBody Map<String, String> images) {
        if (images == null || images.size() == 0) {
            throw new IllegalArgumentException();
        }
        return boardService.createImages(images);
    }

    // 게시글 작성하기
    @PostMapping("/articles")
    public long createArticle(@RequestBody ArticleRequestDto requestDto) {
        if (requestDto.getBoardId() < 1
                || requestDto.getTitle() == null
                || requestDto.getTitle().isBlank()
                || requestDto.getContent() == null
                || requestDto.getContent().isBlank()) {
            throw new IllegalArgumentException();
        }

        if (requestDto.getImageIds() != null && requestDto.getImageIds().size() > 0) {
            for (long imageId : requestDto.getImageIds()) {
                if (imageId < 1) {
                    throw new IllegalArgumentException();
                }
            }
        }

        return boardService.createArticle(requestDto);
    }

    // 게시글 상세 보기
    @GetMapping("/articles/{articleId}")
    public ArticleResponseDto retrieveArticle(@PathVariable long articleId) {
        if (articleId < 1) {
            throw new IllegalArgumentException();
        }
        return boardService.retrieveArticle(articleId);
    }

    // 게시글 삭제하기
    @DeleteMapping("/articles/{articleId}")
    public int deleteArticle(@PathVariable long articleId) {
        if (articleId < 1) {
            throw new IllegalArgumentException();
        }
        return boardService.deleteArticle(articleId);
    }

    // 검색기능
    // 1. 게시판 이름으로 검색
    @GetMapping("/search/boards/{boardName}")
    public List<ArticleListResponseDto> searchBoards(@PathVariable String boardName) {
        if (boardName == null || boardName.isBlank()) {
            throw new IllegalArgumentException();
        }
        return boardService.searchBoards(boardName);
    }

    // 2. 게시물 제목, 내용으로 검색
    @GetMapping("/search/articles/{keyword}")
    public List<ArticleListResponseDto> searchArticles(@PathVariable String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException();
        }
        return boardService.searchArticles(keyword);
    }

    // 3. 특정 기간 내 검색
    @GetMapping("/search/articles")
    public List<ArticleListResponseDto> searchArticlesBetween(@RequestBody DateDto date) {
        if (date.getFrom().isAfter(date.getUntil())) {
            throw new IllegalArgumentException();
        }

        return boardService.searchArticlesBetween(date);
    }

}
