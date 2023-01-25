package br.com.kevinaryel.bibliosystem.service;

import br.com.kevinaryel.bibliosystem.entity.BookEntity;
import br.com.kevinaryel.bibliosystem.entity.CopyEntity;
import br.com.kevinaryel.bibliosystem.exception.BusinessRuleException;
import br.com.kevinaryel.bibliosystem.exception.NotFoundException;
import br.com.kevinaryel.bibliosystem.repository.BookRepository;
import br.com.kevinaryel.bibliosystem.repository.CopyRepository;
import br.com.kevinaryel.bibliosystem.request.CopyCreateRequest;
import br.com.kevinaryel.bibliosystem.response.BookResponse;
import br.com.kevinaryel.bibliosystem.response.CopyResponse;
import br.com.kevinaryel.bibliosystem.response.CopyWithBookDetailsResponse;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CopyServiceTest {
    @InjectMocks
    private CopyService service;

    @Mock
    private BookRepository bookRepository;
    @Mock
    private CopyRepository copyRepository;

    @Mock
    private Validate validate;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    void testCreateCopyComSucesso() throws NotFoundException, BusinessRuleException {
        CopyCreateRequest copyCreateRequest = mock(CopyCreateRequest.class);
        CopyEntity copyEntity = mock(CopyEntity.class);
        BookEntity bookEntity = mock(BookEntity.class);
        CopyResponse copyResponse = mock(CopyResponse.class);
        when(bookRepository.findById(copyCreateRequest.getId_book())).thenReturn(Optional.ofNullable(bookEntity));
        when(objectMapper.convertValue(copyCreateRequest, CopyEntity.class)).thenReturn(copyEntity);
        when(copyRepository.save(copyEntity)).thenReturn(copyEntity);
        when(objectMapper.convertValue(copyEntity, CopyResponse.class)).thenReturn(copyResponse);
        ResponseEntity<CopyResponse> result = service.create(copyCreateRequest);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(copyResponse, result.getBody());

    }

    @Test
    void testDeleteCopyComSucesso() throws NotFoundException {
        CopyEntity copyEntity = mock(CopyEntity.class);
        when(copyRepository.findById(0)).thenReturn(Optional.of(copyEntity));
        ResponseEntity<Void> result = service.delete(0);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    public void testFindBookByIdComSucesso() throws NotFoundException {
        CopyEntity copyEntity = mock(CopyEntity.class);
        BookEntity bookEntity = mock(BookEntity.class);
        CopyWithBookDetailsResponse copyWithBookDetailsResponse = mock(CopyWithBookDetailsResponse.class);
        when(copyRepository.findById(0)).thenReturn(Optional.ofNullable(copyEntity));
        when(objectMapper.convertValue(copyEntity, CopyWithBookDetailsResponse.class)).thenReturn(copyWithBookDetailsResponse);
        when(copyEntity.getBook()).thenReturn(bookEntity);
        ResponseEntity<CopyWithBookDetailsResponse> result = service.findById(0);
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertEquals(copyWithBookDetailsResponse, result.getBody());
    }

    @Test
    public void testFindBookByIdBookNotFoundException() throws NotFoundException {
        when(copyRepository.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> service.findById(1));
    }

    @Test
    void testlistCopyComSucesso() throws NotFoundException, BusinessRuleException {
        List<CopyEntity> copyEntities = mock(List.class);
        Page<CopyEntity> copyEntityPage = mock(Page.class);
        when(copyRepository
                .findCopyEntitiesByBook_Id(0,PageRequest.of(0,10, Sort.by("id"))))
                .thenReturn(copyEntityPage);
        when(copyEntityPage.getContent()).thenReturn(copyEntities);
        ResponseEntity<PageResponse<CopyResponse>> result = service.list(null,null,0);
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertEquals(0,result.getBody().getPage().intValue());
        Assert.assertEquals(10,result.getBody().getSize().intValue());
    }
}