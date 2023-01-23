package br.com.kevinaryel.bibliosystem.client;

import br.com.kevinaryel.bibliosystem.exception.BusinessRuleException;
import br.com.kevinaryel.bibliosystem.repository.ClientRepository;
import br.com.kevinaryel.bibliosystem.request.ClientCreateRequest;
import br.com.kevinaryel.bibliosystem.response.ClientResponse;
import br.com.kevinaryel.bibliosystem.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientCreate {
    ClientCreateRequest fonteOrigem = null;

    @InjectMocks
    private ClientService service;
    @Mock
    private ClientRepository clientRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testCreateClientComSucesso() throws BusinessRuleException {
        fonteOrigem = mock(ClientCreateRequest.class);
        fonteOrigem.setName("Kevin Aryel");
        fonteOrigem.setDocument("12345678910");
        fonteOrigem.setGender('M');
        fonteOrigem.setBirth_date(LocalDate.of(1990, 1, 1));
        ResponseEntity<ClientResponse> fonteDestino = mock(ResponseEntity.class);
        when(service.create(fonteOrigem)).thenReturn(fonteDestino);
        ResponseEntity<ClientResponse> result = service.create(fonteOrigem);
        Assert.assertEquals(result, fonteDestino);
    }
}
