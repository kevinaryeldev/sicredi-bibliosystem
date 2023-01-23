package br.com.kevinaryel.bibliosystem.controller;

import br.com.kevinaryel.bibliosystem.exception.BusinessRuleException;
import br.com.kevinaryel.bibliosystem.exception.NotFoundException;
import br.com.kevinaryel.bibliosystem.request.BookCreateRequest;
import br.com.kevinaryel.bibliosystem.response.BookResponse;
import br.com.kevinaryel.bibliosystem.response.PageResponse;
import br.com.kevinaryel.bibliosystem.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/livro")
@AllArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/listar")
    public ResponseEntity<PageResponse<BookResponse>> list(Integer page, Integer size){
        return bookService.list(page,size);
    }

    @GetMapping("/busca-por-id/{id}")
    public ResponseEntity<BookResponse> findById(@PathVariable Integer id) throws NotFoundException {
        return bookService.findById(id);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<BookResponse> create(@RequestBody BookCreateRequest book) throws BusinessRuleException {
        return bookService.create(book);
    }
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws NotFoundException {
        return bookService.delete(id);
    }
}
