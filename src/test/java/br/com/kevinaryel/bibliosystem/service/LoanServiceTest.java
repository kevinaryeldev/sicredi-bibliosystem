package br.com.kevinaryel.bibliosystem.service;

import br.com.kevinaryel.bibliosystem.entity.BookEntity;
import br.com.kevinaryel.bibliosystem.entity.ClientEntity;
import br.com.kevinaryel.bibliosystem.entity.CopyEntity;
import br.com.kevinaryel.bibliosystem.entity.LoanEntity;
import br.com.kevinaryel.bibliosystem.exception.BusinessRuleException;
import br.com.kevinaryel.bibliosystem.exception.NotFoundException;
import br.com.kevinaryel.bibliosystem.mapper.LoanMapper;
import br.com.kevinaryel.bibliosystem.repository.ClientRepository;
import br.com.kevinaryel.bibliosystem.repository.CopyRepository;
import br.com.kevinaryel.bibliosystem.repository.LoanRepository;
import br.com.kevinaryel.bibliosystem.request.LoanCreateRequest;
import br.com.kevinaryel.bibliosystem.response.ClientResponse;
import br.com.kevinaryel.bibliosystem.response.CopyResponse;
import br.com.kevinaryel.bibliosystem.response.LoanResponse;
import br.com.kevinaryel.bibliosystem.response.PageResponse;
import br.com.kevinaryel.bibliosystem.utils.validate.Validate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {
    @InjectMocks
    private LoanService service;

    @Mock
    private LoanRepository loanRepository;
    @Mock
    private Validate validate;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private CopyRepository copyRepository;
    @Mock
    private ClientRepository clientRepository;

    @Mock
    LoanMapper loanMapper;


    @Test
    void testCreateLoanComSucesso() throws BusinessRuleException {
        LoanCreateRequest loanCreateRequest = mock(LoanCreateRequest.class);
        LoanEntity loanEntity = mock(LoanEntity.class);
        LoanResponse loanResponse = mock(LoanResponse.class);
        when(objectMapper.convertValue(loanCreateRequest,LoanEntity.class)).thenReturn(loanEntity);
        when(loanRepository.save(loanEntity)).thenReturn(loanEntity);
        //GenerateLoanResponse
        when(loanMapper.toLoanResponse(loanEntity)).thenReturn(loanResponse);
        ResponseEntity<LoanResponse> result = service.create(loanCreateRequest);
        Assert.assertEquals(result.getBody(), loanResponse);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.CREATED);
    }
    @Test
    void testEditLoanComSucesso() throws NotFoundException {
        LoanEntity loanEntity = mock(LoanEntity.class);
        when(loanRepository.findById(0)).thenReturn(Optional.of(loanEntity));
        when(loanRepository.save(loanEntity)).thenReturn(loanEntity);
        when(loanRepository.existsById(0)).thenReturn(true);
        ResponseEntity<Void> result = service.edit(0);
        Assert.assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
    }
    @Test
    public void testFindAllActiveSuccess() {
        List<LoanEntity> loansList = new ArrayList<>();
        loansList.add(new LoanEntity(1, 'A', LocalDate.now(),
                LocalDate.now().plusDays(7), new CopyEntity(), new ClientEntity()));
        Page<LoanEntity> loanPage = new PageImpl<>(loansList);
        Sort orderBy = Sort.by("id");
        PageRequest pageRequest = PageRequest.of(0,10,orderBy);
        when(loanRepository.findByStatus('A', pageRequest)).thenReturn(loanPage);
        ResponseEntity<PageResponse<LoanResponse>> result = service.findAllActive(0,10);
        Long longValue = loansList.size() * 1L;
        PageResponse<LoanResponse> comparator = new PageResponse<>(longValue,1,0,
                10, loansList.stream().map(loanMapper::toLoanResponse).collect(Collectors.toList()));
        Assert.assertEquals(result.getBody(), comparator);
    }
    @Test
    public void testFindAllByClientIdSucess() throws NotFoundException, BusinessRuleException {
        List<LoanEntity> loansList = new ArrayList<>();
        loansList.add(new LoanEntity(1, 'A', LocalDate.now(),
                LocalDate.now().plusDays(7), new CopyEntity(), new ClientEntity()));
        Page<LoanEntity> loanPage = new PageImpl<>(loansList);
        Sort orderBy = Sort.by(Sort.Direction.ASC, "status").and(Sort.by(Sort.Direction.DESC, "loan_date"));
        PageRequest pageRequest = PageRequest.of(0, 10, orderBy);
        when(loanRepository.findLoanEntitiesByClient_Id(0, pageRequest)).thenReturn(loanPage);
        ResponseEntity<PageResponse<LoanResponse>> result = service.findByClientId(null, null, 0);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(loanPage.getTotalElements(), result.getBody().getTotalElements());
        assertEquals(loanPage.getTotalPages(), result.getBody().getPageQuantity());
        assertEquals(0, result.getBody().getPage());
        assertEquals(10, result.getBody().getSize());
    }

}