package br.com.kevinaryel.bibliosystem.service;

import br.com.kevinaryel.bibliosystem.entity.LoanEntity;
import br.com.kevinaryel.bibliosystem.exception.BusinessRuleException;
import br.com.kevinaryel.bibliosystem.exception.NotFoundException;
import br.com.kevinaryel.bibliosystem.repository.ClientRepository;
import br.com.kevinaryel.bibliosystem.repository.CopyRepository;
import br.com.kevinaryel.bibliosystem.repository.LoanRepository;
import br.com.kevinaryel.bibliosystem.request.LoanCreateRequest;
import br.com.kevinaryel.bibliosystem.response.ClientResponse;
import br.com.kevinaryel.bibliosystem.response.CopyResponse;
import br.com.kevinaryel.bibliosystem.response.LoanResponse;
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

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final ClientRepository clientRepository;
    private final CopyRepository copyRepository;
    private final ObjectMapper objectMapper;
    private final Validate validate = new Validate();

    private LoanResponse generateLoanResponse(LoanEntity loanSaved) {
        LoanResponse loanResponse = objectMapper.convertValue(loanSaved, LoanResponse.class);
        CopyResponse copy = objectMapper.convertValue(loanSaved.getCopy(), CopyResponse.class);
        copy.setId_book(loanSaved.getCopy().getBook().getId());
        loanResponse.setCopy(copy);
        loanResponse.setClient(objectMapper.convertValue(loanSaved.getClient(), ClientResponse.class));
        return loanResponse;
    }


    @Transactional
    public ResponseEntity<Void> edit(Integer id) throws NotFoundException {
        if (!loanRepository.existsById(id)){
            throw new NotFoundException("Empréstimo não encontrado");
        }
        LoanEntity loan = loanRepository.findById(id).get();
        loan.setStatus('I');
        loanRepository.save(loan);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @Transactional
    public ResponseEntity<LoanResponse> create(LoanCreateRequest loanCreateRequest) throws BusinessRuleException {
        validate.validateIdCopy(loanCreateRequest.getId_copy(), copyRepository);
        validate.validateIdClient(loanCreateRequest.getId_client(), clientRepository);
        LoanEntity loan = new LoanEntity(null,'A',
                LocalDate.now(), LocalDate.now().plusDays(7),
                copyRepository.findById(loanCreateRequest.getId_copy()).get(),
                clientRepository.findById(loanCreateRequest.getId_client()).get()
                );
        LoanEntity loanSaved = loanRepository.save(loan);
        LoanResponse loanResponse = generateLoanResponse(loanSaved);
        return new ResponseEntity<>(loanResponse, HttpStatus.CREATED);
    }

    public ResponseEntity<PageResponse<LoanResponse>> findByClientId(Integer page, Integer size, Integer id) throws NotFoundException {
        if(page==null){
            page=0;
        }
        if (size==null){
            size=10;
        }
        Sort orderBy = Sort.by(Sort.Direction.ASC, "status").and(Sort.by(Sort.Direction.DESC, "loan_date"));
        PageRequest pageRequest = PageRequest.of(page, size, orderBy);
        Page<LoanEntity> repositoryPage = loanRepository.findByClient_Id(id, pageRequest);
        List<LoanResponse> loans = repositoryPage.getContent()
                .stream()
                .map(loan -> {
                    LoanResponse loanResponse = generateLoanResponse(loan);
                    return loanResponse;
                }).toList();
        PageResponse<LoanResponse> pageResponse = new PageResponse<>(repositoryPage.getTotalElements(),
                repositoryPage.getTotalPages(),page,size, loans);
        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    public ResponseEntity<PageResponse<LoanResponse>> findAllActive(Integer page, Integer size){
        if(page==null){
            page=0;
        }
        if (size==null){
            size=10;
        }
        Sort orderBy = Sort.by("id");
        PageRequest pageRequest = PageRequest.of(page, size, orderBy);
        Page<LoanEntity> repositoryPage = loanRepository.findByStatus('A', pageRequest);
        List<LoanResponse> loans = repositoryPage.getContent()
                .stream()
                .map(loan -> {
                    LoanResponse loanResponse = generateLoanResponse(loan);
                    return loanResponse;
                }).toList();
        PageResponse<LoanResponse> pageResponse = new PageResponse<>(repositoryPage.getTotalElements(),
                repositoryPage.getTotalPages(),page,size, loans);
        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }
}
