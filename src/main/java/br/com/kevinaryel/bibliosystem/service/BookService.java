package br.com.kevinaryel.bibliosystem.service;

import br.com.kevinaryel.bibliosystem.entity.BookEntity;
import br.com.kevinaryel.bibliosystem.exception.BusinessRuleException;
import br.com.kevinaryel.bibliosystem.exception.NotFoundException;
import br.com.kevinaryel.bibliosystem.repository.BookRepository;
import br.com.kevinaryel.bibliosystem.request.BookCreateRequest;
import br.com.kevinaryel.bibliosystem.response.BookResponse;
import br.com.kevinaryel.bibliosystem.response.PageResponse;
import br.com.kevinaryel.bibliosystem.utils.Validate;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final ObjectMapper objectMapper;
    private final Validate validate = new Validate();

    @Transactional
    public ResponseEntity<BookResponse> create(BookCreateRequest book) throws BusinessRuleException {
        validate.validateString(book.getTitle());
        validate.validateName(book.getAuthor());
        validate.validateName(book.getPublisher());
        validate.validateIsbn(book.getCode());
        validate.validateCdd(book.getCdd());
        BookEntity bookEntity = objectMapper.convertValue(book, BookEntity.class);
        BookEntity bookSaved = bookRepository.save(bookEntity);
        BookResponse bookResponse = objectMapper.convertValue(bookSaved, BookResponse.class);
        return new ResponseEntity<>(bookResponse,HttpStatus.CREATED);
    }
    public ResponseEntity<PageResponse<BookResponse>> list(Integer page, Integer size){
        if(page==null){
            page=0;
        }
        if (size==null){
            size=10;
        }
        Sort orderBy = Sort.by("id");
        PageRequest pageRequest = PageRequest.of(page,size,orderBy);
        Page<BookEntity> repositoryPage = bookRepository.findAll(pageRequest);
        List<BookResponse> bookList = repositoryPage
                .getContent()
                .stream()
                .map(bookEntity -> objectMapper.convertValue(bookEntity, BookResponse.class))
                .toList();
        PageResponse<BookResponse> pageResponse = new PageResponse<>(repositoryPage.getTotalElements(),
                repositoryPage.getTotalPages(), page,size,bookList);
        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }
    private BookEntity getBookById(Integer id) throws NotFoundException{
        Optional<BookEntity> book = bookRepository.findById(id);
        if(book.isEmpty()){
            throw new NotFoundException("Book not found");
        }
        return book.get();
    }
    public ResponseEntity<BookResponse> findById(Integer id) throws NotFoundException {
        BookEntity bookEntity = getBookById(id);
        BookResponse bookResponse = objectMapper.convertValue(bookEntity, BookResponse.class);
        return new ResponseEntity<>(bookResponse, HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity<Void> delete(Integer id) throws NotFoundException {
        BookEntity bookEntity = getBookById(id);
        bookRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
