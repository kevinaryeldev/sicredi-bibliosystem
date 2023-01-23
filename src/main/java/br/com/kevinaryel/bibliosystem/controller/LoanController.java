package br.com.kevinaryel.bibliosystem.controller;

import br.com.kevinaryel.bibliosystem.exception.BusinessRuleException;
import br.com.kevinaryel.bibliosystem.exception.NotFoundException;
import br.com.kevinaryel.bibliosystem.request.LoanCreateRequest;
import br.com.kevinaryel.bibliosystem.response.LoanResponse;
import br.com.kevinaryel.bibliosystem.response.PageResponse;
import br.com.kevinaryel.bibliosystem.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emprestimo")
@AllArgsConstructor
public class LoanController {
    private final LoanService loanService;

    @PostMapping("/emprestar")
    public ResponseEntity<LoanResponse> create(@RequestBody LoanCreateRequest loan) throws BusinessRuleException {
        return loanService.create(loan);
    }
    @PatchMapping("/devolver/{id}")
    public ResponseEntity<Void> edit(@PathVariable Integer id) throws NotFoundException {
        return loanService.edit(id);
    }
    @GetMapping("/listar-ativos")
    public ResponseEntity<PageResponse<LoanResponse>> listActiveLoans(Integer page, Integer size){
        return loanService.findAllActive(page, size);
    }
    @GetMapping("/listar-por-id-cliente/{id}")
    public ResponseEntity<PageResponse<LoanResponse>> listLoansByClientId(@PathVariable Integer id, Integer page, Integer size) throws NotFoundException {
        return loanService.findByClientId(page, size,id);
    }
}
