package br.com.kevinaryel.bibliosystem.controller;

import br.com.kevinaryel.bibliosystem.exception.BusinessRuleException;
import br.com.kevinaryel.bibliosystem.exception.NotFoundException;
import br.com.kevinaryel.bibliosystem.request.ClientCreateRequest;
import br.com.kevinaryel.bibliosystem.request.ClientEditRequest;
import br.com.kevinaryel.bibliosystem.response.ClientResponse;
import br.com.kevinaryel.bibliosystem.response.PageResponse;
import br.com.kevinaryel.bibliosystem.service.ClientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
@AllArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/listar")
    public ResponseEntity<PageResponse<ClientResponse>> list(Integer page, Integer size){
        return clientService.list(page,size);
    }
    @PostMapping("/cadastrar")
    public ResponseEntity<ClientResponse> create(@RequestBody ClientCreateRequest client) throws BusinessRuleException {
        return clientService.create(client);
    }
    @PatchMapping("/editar/{id}")
    public ResponseEntity<ClientResponse> update(@PathVariable Integer id, @Valid @RequestBody ClientEditRequest client) throws NotFoundException, BusinessRuleException {
        return clientService.edit(id, client);
    }
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws NotFoundException {
        return clientService.delete(id);
    }
    @GetMapping("/busca-por-id/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable Integer id) throws NotFoundException {
        return clientService.findById(id);
    }
}
