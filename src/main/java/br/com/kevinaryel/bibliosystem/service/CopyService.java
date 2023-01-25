package br.com.kevinaryel.bibliosystem.service;

import br.com.kevinaryel.bibliosystem.entity.CopyEntity;
import br.com.kevinaryel.bibliosystem.exception.BusinessRuleException;
import br.com.kevinaryel.bibliosystem.exception.NotFoundException;
import br.com.kevinaryel.bibliosystem.repository.BookRepository;
import br.com.kevinaryel.bibliosystem.repository.CopyRepository;
import br.com.kevinaryel.bibliosystem.request.CopyCreateRequest;
import br.com.kevinaryel.bibliosystem.response.CopyResponse;
import br.com.kevinaryel.bibliosystem.response.CopyWithBookDetailsResponse;
import br.com.kevinaryel.bibliosystem.response.PageResponse;
import br.com.kevinaryel.bibliosystem.utils.validate.Validate;
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
public class CopyService {

    private final CopyRepository copyRepository;
    private final BookRepository bookRepository;
    private final ObjectMapper objectMapper;
    private final Validate validate;

    public void checkIdBook(Integer id) throws NotFoundException{
        if (bookRepository.existsById(id)){
         return;
        }
        throw new NotFoundException("Id do livro inválido");
    }

    @Transactional
    public ResponseEntity<CopyResponse> create(CopyCreateRequest copy) throws BusinessRuleException, NotFoundException {
        checkIdBook(copy.getId_book());
        validate.validateString(copy.getProperty_code());
        validate.validateYear(copy.getYear());
        validate.validateEdition(copy.getEdition());
        CopyEntity copyEntity = objectMapper.convertValue(copy, CopyEntity.class);
        copyEntity.setBook(bookRepository.findById(copy.getId_book()).get());
        CopyEntity copySaved = copyRepository.save(copyEntity);
        CopyResponse copyResponse = objectMapper.convertValue(copySaved, CopyResponse.class);
        copyResponse.setId_book(copy.getId_book());
        return new ResponseEntity<>(copyResponse, HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<Void> delete(Integer id) throws NotFoundException {
        getCopyEntityById(id);
        copyRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    private CopyEntity getCopyEntityById(Integer id) throws NotFoundException {
        Optional<CopyEntity> copy = copyRepository.findById(id);
        if (copy.isEmpty()){
            throw new NotFoundException("Id da cópia inválido");
        }
        return copy.get();
    }

    public ResponseEntity<CopyWithBookDetailsResponse> findById(Integer id) throws NotFoundException {
        CopyEntity copyEntity = getCopyEntityById(id);
        CopyWithBookDetailsResponse copyResponse = objectMapper.convertValue(copyEntity, CopyWithBookDetailsResponse.class);
        copyResponse.setId_book(copyEntity.getBook().getId());
        copyResponse.setTitle(copyEntity.getBook().getTitle());
        copyResponse.setSubtitle(copyEntity.getBook().getSubtitle());
        return new ResponseEntity<>(copyResponse, HttpStatus.OK);
    }

    public ResponseEntity<PageResponse<CopyResponse>> list(Integer page, Integer size, Integer id_book) throws NotFoundException {
        checkIdBook(id_book);
        if(page==null){
            page=0;
        }
        if (size==null){
            size=10;
        }
        Sort orderBy = Sort.by("id");
        PageRequest pageRequest = PageRequest.of(page,size,orderBy);
        Page<CopyEntity> repositoryPage = copyRepository.findCopyEntitiesByBook_Id(id_book, pageRequest);
        List<CopyResponse> copyList = repositoryPage
                .getContent()
                .stream()
                .map(copyEntity -> objectMapper.convertValue(copyEntity, CopyResponse.class))
                .map(copyResponse -> {
                    copyResponse.setId_book(id_book);
                    return copyResponse;
                })
                .toList();
        PageResponse<CopyResponse> pageResponse = new PageResponse<>(repositoryPage.getTotalElements(),
            repositoryPage.getTotalPages(), page,size, copyList);
        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

}
