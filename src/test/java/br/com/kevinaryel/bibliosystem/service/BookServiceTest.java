package br.com.kevinaryel.bibliosystem.service;

import br.com.kevinaryel.bibliosystem.entity.BookEntity;
import br.com.kevinaryel.bibliosystem.exception.BusinessRuleException;
import br.com.kevinaryel.bibliosystem.exception.NotFoundException;
import br.com.kevinaryel.bibliosystem.repository.BookRepository;
import br.com.kevinaryel.bibliosystem.request.BookCreateRequest;
import br.com.kevinaryel.bibliosystem.response.BookResponse;
import br.com.kevinaryel.bibliosystem.response.PageResponse;
import br.com.kevinaryel.bibliosystem.utils.validate.Validate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @InjectMocks
    private BookService service;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private Validate validate;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    public void testCreateBookComSucesso() throws BusinessRuleException {
        BookCreateRequest bookCreateRequest = mock(BookCreateRequest.class);
        BookEntity bookEntity = mock(BookEntity.class);
        BookResponse bookResponse = mock(BookResponse.class);
        when(objectMapper.convertValue(bookCreateRequest, BookEntity.class)).thenReturn(bookEntity);
        when(bookRepository.save(bookEntity)).thenReturn(bookEntity);
        when(objectMapper.convertValue(bookEntity, BookResponse.class)).thenReturn(bookResponse);
        ResponseEntity<BookResponse> result = service.create(bookCreateRequest);
        Assert.assertEquals(HttpStatus.CREATED, result.getStatusCode());
        Assert.assertEquals(result.getBody(),bookResponse);
        verify(validate).validateString(bookCreateRequest.getTitle());
        verify(validate,times(2)).validateName(bookCreateRequest.getAuthor());
        verify(validate).validateIsbn(bookCreateRequest.getCode());
        verify(validate).validateCdd(bookCreateRequest.getCdd());
    }

    @Test
    public void testListBookComSucesso(){
        List<BookEntity> bookEntities = mock(List.class);
        Page<BookEntity> bookEntityPage = mock(Page.class);
        when(bookRepository
                .findAll(PageRequest.of(0,10, Sort.by("id"))))
                .thenReturn(bookEntityPage);
        when(bookEntityPage.getContent()).thenReturn(bookEntities);
        ResponseEntity<PageResponse<BookResponse>> result = service.list(null,null);
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertEquals(0,result.getBody().getPage().intValue());
        Assert.assertEquals(10,result.getBody().getSize().intValue());
    }

    @Test
    public void testFindBookByIdComSucesso() throws NotFoundException {
        BookEntity bookEntity = mock(BookEntity.class);
        BookResponse bookResponse = mock(BookResponse.class);
        when(bookRepository.findById(0)).thenReturn(Optional.ofNullable(bookEntity));
        when(objectMapper.convertValue(bookEntity, BookResponse.class)).thenReturn(bookResponse);
        ResponseEntity<BookResponse> result = service.findById(0);
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertEquals(bookResponse, result.getBody());
    }

    @Test
    public void testFindBookByIdBookNotFoundException() throws NotFoundException {
        when(bookRepository.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> service.findById(1));
    }

    @Test
    public void testDeleteBookComSucesso() throws NotFoundException {
        BookEntity bookEntity = mock(BookEntity.class);
        when(bookRepository.findById(0)).thenReturn(Optional.ofNullable(bookEntity));
        ResponseEntity<Void> result = service.delete(0);
        Assert.assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }
}