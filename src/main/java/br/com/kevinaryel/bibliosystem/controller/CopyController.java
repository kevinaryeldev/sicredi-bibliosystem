package br.com.kevinaryel.bibliosystem.controller;

import br.com.kevinaryel.bibliosystem.exception.BusinessRuleException;
import br.com.kevinaryel.bibliosystem.exception.NotFoundException;
import br.com.kevinaryel.bibliosystem.request.CopyCreateRequest;
import br.com.kevinaryel.bibliosystem.response.CopyResponse;
import br.com.kevinaryel.bibliosystem.response.CopyWithBookDetailsResponse;
import br.com.kevinaryel.bibliosystem.response.PageResponse;
import br.com.kevinaryel.bibliosystem.service.CopyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/copy")
@AllArgsConstructor
public class CopyController {

    private final CopyService copyService;

    @GetMapping("/listar/{id_book}")
    public ResponseEntity<PageResponse<CopyResponse>> list(Integer page, Integer size, @PathVariable Integer id_book) throws NotFoundException {
        return copyService.list(page,size,id_book);
    }
    @GetMapping("/busca-por-id/{id}")
    public ResponseEntity<CopyWithBookDetailsResponse> findById(@PathVariable Integer id) throws NotFoundException {
        return copyService.findById(id);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<CopyResponse> create(@RequestBody CopyCreateRequest copy) throws NotFoundException, BusinessRuleException {
        return copyService.create(copy);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws NotFoundException {
        return copyService.delete(id);
    }

}
