package br.com.kevinaryel.bibliosystem.service;

import br.com.kevinaryel.bibliosystem.entity.ClientEntity;
import br.com.kevinaryel.bibliosystem.exception.BusinessRuleException;
import br.com.kevinaryel.bibliosystem.exception.NotFoundException;
import br.com.kevinaryel.bibliosystem.repository.ClientRepository;
import br.com.kevinaryel.bibliosystem.request.ClientCreateRequest;
import br.com.kevinaryel.bibliosystem.request.ClientEditRequest;
import br.com.kevinaryel.bibliosystem.response.ClientResponse;
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
public class ClientService {
    private final ObjectMapper objectMapper;
    private final ClientRepository clientRepository;
    private final Validate validate = new Validate();

    @Transactional
    public ResponseEntity<ClientResponse> create(ClientCreateRequest client) throws BusinessRuleException {
        validate.validateName(client.getName());
        validate.validateEmail(client.getEmail());
        validate.validateGender(client.getGender());
        validate.validateDocument(client.getDocument(),clientRepository);
        ClientEntity clientEntity = objectMapper.convertValue(client, ClientEntity.class);
        ClientEntity clientSaved = clientRepository.save(clientEntity);
        ClientResponse clientResponse = objectMapper.convertValue(clientSaved, ClientResponse.class);
        return new ResponseEntity<>(clientResponse,HttpStatus.CREATED);
    }
    @Transactional
    public ResponseEntity<ClientResponse> edit(Integer id, ClientEditRequest client) throws NotFoundException, BusinessRuleException {
        ClientEntity clientSaved = getClientById(id);
        validate.validateName(client.getName());
        validate.validateEmail(client.getEmail());
        validate.validateGender(client.getGender());
        clientSaved.setName(client.getName());
        clientSaved.setEmail(client.getEmail());
        clientSaved.setGender(client.getGender());
        ClientResponse clientResponse = objectMapper.convertValue(clientRepository.save(clientSaved), ClientResponse.class);
        return new ResponseEntity<>(clientResponse, HttpStatus.OK);
    }
    public ResponseEntity<PageResponse<ClientResponse>> list(Integer page, Integer size){
        if(page==null){
            page=0;
        }
        if (size==null){
            size=10;
        }
        Sort orderBy = Sort.by("id");
        PageRequest pageRequest = PageRequest.of(page,size,orderBy);
        Page<ClientEntity> repositoryPage = clientRepository.findAll(pageRequest);
        List<ClientResponse> clientList = repositoryPage
                .getContent()
                .stream()
                .map(clientEntity -> objectMapper.convertValue(clientEntity, ClientResponse.class))
                .toList();
        PageResponse<ClientResponse> pageResponse = new PageResponse<>(repositoryPage.getTotalElements(),
                repositoryPage.getTotalPages(), page,size,clientList);
        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }
    private ClientEntity getClientById(Integer id) throws NotFoundException {
        Optional<ClientEntity> clientOpt = clientRepository.findById(id);
        if(clientOpt.isEmpty()){
            throw new NotFoundException("Cliente não encontrado");
        }
        return clientOpt.get();
    }
    @Transactional
    public ResponseEntity<Void> delete(Integer id) throws NotFoundException {
        getClientById(id);
        clientRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    public ResponseEntity<ClientResponse> findById(Integer id) throws NotFoundException {
        ClientEntity client = getClientById(id);
        ClientResponse clientResponse = objectMapper.convertValue(client, ClientResponse.class);
        return new ResponseEntity<>(clientResponse, HttpStatus.OK);
    }
}
