package br.com.kevinaryel.bibliosystem.service;

import br.com.kevinaryel.bibliosystem.entity.ClientEntity;
import br.com.kevinaryel.bibliosystem.exception.BusinessRuleException;
import br.com.kevinaryel.bibliosystem.exception.NotFoundException;
import br.com.kevinaryel.bibliosystem.repository.ClientRepository;
import br.com.kevinaryel.bibliosystem.request.ClientCreateRequest;
import br.com.kevinaryel.bibliosystem.request.ClientEditRequest;
import br.com.kevinaryel.bibliosystem.response.ClientResponse;
import br.com.kevinaryel.bibliosystem.response.PageResponse;
import br.com.kevinaryel.bibliosystem.service.ClientService;
import br.com.kevinaryel.bibliosystem.utils.validate.Validate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
public class ClientServiceTest {
    @InjectMocks
    private ClientService service;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private Validate validate;
    @Mock
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testCreateClientComSucesso() throws BusinessRuleException {
        ClientCreateRequest clientCreateRequest = mock(ClientCreateRequest.class);
        ClientEntity clientEntity = mock(ClientEntity.class);
        ClientResponse clientResponse = mock(ClientResponse.class);
        when(objectMapper.convertValue(clientCreateRequest, ClientEntity.class)).thenReturn(clientEntity);
        when(clientRepository.save(clientEntity)).thenReturn(clientEntity);
        when(objectMapper.convertValue(clientEntity, ClientResponse.class)).thenReturn(clientResponse);
        ResponseEntity<ClientResponse> result = service.create(clientCreateRequest);
        Assert.assertEquals(result.getBody(),clientResponse );
        Assert.assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(validate).validateName(clientCreateRequest.getName());
        verify(validate).validateEmail(clientCreateRequest.getEmail());
        verify(validate).validateDocument(clientCreateRequest.getDocument(),clientRepository);
        verify(validate).validateGender(clientCreateRequest.getGender());
    }

    @Test
    public void testEditClientComSucesso()  throws NotFoundException, BusinessRuleException{
        ClientEditRequest clientEditRequest = mock(ClientEditRequest.class);
        ClientEntity clientEntity = mock(ClientEntity.class);
        ClientResponse clientResponse = mock(ClientResponse.class);
        when(clientRepository.findById(0)).thenReturn(Optional.ofNullable(clientEntity));
        when(clientRepository.save(clientEntity)).thenReturn(clientEntity);
        when(objectMapper.convertValue(clientEntity, ClientResponse.class)).thenReturn(clientResponse);
        ResponseEntity<ClientResponse> result = service.edit(0,clientEditRequest);
        Assert.assertEquals(result.getBody(),clientResponse );
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(validate).validateName(clientEditRequest.getName());
        verify(validate).validateEmail(clientEditRequest.getEmail());
        verify(validate).validateGender(clientEditRequest.getGender());
    }
    @Test
    public void testListClientComSucesso(){
        List<ClientEntity> clientEntities = mock(List.class);
        Page<ClientEntity> clientEntityPage = mock(Page.class);
        when(clientRepository
                .findAll(PageRequest.of(0,10, Sort.by("id"))))
                .thenReturn(clientEntityPage);
        when(clientEntityPage.getContent()).thenReturn(clientEntities);
        ResponseEntity<PageResponse<ClientResponse>> result = service.list(null,null);
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertEquals(0,result.getBody().getPage().intValue());
        Assert.assertEquals(10,result.getBody().getSize().intValue());
    }

    @Test
    public void testDeleteClientComSucesso() throws NotFoundException {
        ClientEntity clientEntity = mock(ClientEntity.class);
        when(clientRepository.findById(0)).thenReturn(Optional.ofNullable(clientEntity));
        ResponseEntity<Void> result = service.delete(0);
        Assert.assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    public void testFindByIdComSucesso() throws NotFoundException {
        ClientEntity clientEntity = mock(ClientEntity.class);
        ClientResponse clientResponse = mock(ClientResponse.class);
        when(clientRepository.findById(0)).thenReturn(Optional.ofNullable(clientEntity));
        when(objectMapper.convertValue(clientEntity, ClientResponse.class)).thenReturn(clientResponse);
        ResponseEntity<ClientResponse> result = service.findById(0);
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertEquals(clientResponse, result.getBody());
    }

    @Test
    public void testFindByIdClientNotFoundException() throws NotFoundException {
        when(clientRepository.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> service.findById(1));
    }

}
