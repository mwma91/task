package com.chanho.task.controller;

import com.chanho.task.dto.*;
import com.chanho.task.service.BoardService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/")
    public ResponseEntity<?> main() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/boards"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    // 게시판 목록 보기
    @GetMapping("/boards")
    public List<BoardResponseDto> listBoards() {
        return boardService.listBoards();
    }

    // 특정 게시판의 게시글 목록 보기
    @GetMapping("/boards/{boardId}")
    public List<ArticleListResponseDto> listArticles(@PathVariable int boardId) {
        return boardService.listArticles(boardId);
    }

    // 게시글 작성하기
    @PostMapping("/articles")
    public int createArticle(@RequestBody ArticleRequestDto requestDto) {
        return boardService.createArticle(requestDto);
    }

    // 게시글 상세 보기
    @GetMapping("/articles/{articleId}")
    public ArticleResponseDto retrieveArticle(@PathVariable int articleId) {
        return boardService.retrieveArticle(articleId);
    }

    // 게시글 삭제하기
    @DeleteMapping("/articles/{articleId}")
    public int deleteArticle(@PathVariable int articleId) {
        return boardService.deleteArticle(articleId);
    }

    // 검색기능
    // 1. 게시판 이름으로 검색
    @GetMapping("/search/boards/{boardName}")
    public List<ArticleListResponseDto> searchBoards(@PathVariable String boardName) {
        return boardService.searchBoards(boardName);
    }

    // 2. 게시물 제목, 내용으로 검색
    @GetMapping("/search/articles/{keyword}")
    public List<ArticleListResponseDto> searchArticles(@PathVariable String keyword) {
        return boardService.searchArticles(keyword);
    }

    // 3. 특정 기간 내 검색
    @GetMapping("/search/articles")
    public List<ArticleListResponseDto> searchArticlesBetween(@RequestBody DateDto date) {
        return boardService.searchArticlesBetween(date);
    }

}
