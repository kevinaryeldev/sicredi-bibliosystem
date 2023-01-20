package br.com.kevinaryel.bibliosystem.service;

import br.com.kevinaryel.bibliosystem.entity.ClientEntity;
import br.com.kevinaryel.bibliosystem.exception.BusinessRuleException;
import br.com.kevinaryel.bibliosystem.exception.NotFoundException;
import br.com.kevinaryel.bibliosystem.repository.ClientRepository;
import br.com.kevinaryel.bibliosystem.request.ClientCreateRequest;
import br.com.kevinaryel.bibliosystem.request.ClientEditRequest;
import br.com.kevinaryel.bibliosystem.response.ClientResponse;
import br.com.kevinaryel.bibliosystem.response.PageResponse;
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

    private void validateName(String name) throws BusinessRuleException {
        if ( name != null){
            if (name.length() >= 10 ) {
                return;
            }
        }
        throw new BusinessRuleException("Erro no nome do cliente");
    }
    private void validateEmail(String email) throws BusinessRuleException{
        if (email != null){
            if (email.matches("^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$" )){
                return;
            }
        }
        throw new BusinessRuleException("Erro no campo email");
    }
    private void validateGender(Character gender) throws BusinessRuleException {
        if (gender != null){
            if (gender.equals('M')||gender.equals('F')||gender.equals('O')){
                return;
            }
        }
        throw new BusinessRuleException("Erro no campo gender");
    }
    private void validateDocument(String document) throws BusinessRuleException {
        if (document != null){
            boolean exists = clientRepository.existsByDocument(document);
            if (document.matches("[0-9]{11,13}")&& !exists){
                return;
            }
        }
        throw new BusinessRuleException("Erro no campo document");
    }
    @Transactional
    public ResponseEntity<ClientResponse> create(ClientCreateRequest client) throws BusinessRuleException {
        validateName(client.getName());
        validateEmail(client.getEmail());
        validateGender(client.getGender());
        validateDocument(client.getDocument());
        ClientEntity clientEntity = objectMapper.convertValue(client, ClientEntity.class);
        ClientEntity clientSaved = clientRepository.save(clientEntity);
        ClientResponse clientResponse = objectMapper.convertValue(clientSaved, ClientResponse.class);
        return new ResponseEntity<>(clientResponse,HttpStatus.CREATED);
    }
    @Transactional
    public ResponseEntity<ClientResponse> edit(Integer id, ClientEditRequest client) throws NotFoundException, BusinessRuleException {
        ClientEntity clientSaved = getClientById(id);
        validateName(client.getName());
        validateEmail(client.getEmail());
        validateGender(client.getGender());
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
