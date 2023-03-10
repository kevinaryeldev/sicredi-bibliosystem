package br.com.kevinaryel.bibliosystem.utils.validate;

import br.com.kevinaryel.bibliosystem.exception.BusinessRuleException;
import br.com.kevinaryel.bibliosystem.repository.BookRepository;
import br.com.kevinaryel.bibliosystem.repository.ClientRepository;
import br.com.kevinaryel.bibliosystem.repository.CopyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("validate")
@RequiredArgsConstructor
public class Validate {
    private final BookRepository bookRepository;
    public void validateIdCopy(Integer id_copy, CopyRepository repository) throws BusinessRuleException {
        if (id_copy != null){
            if (repository.existsById(id_copy)) {
                return;
            }
        }
        throw new BusinessRuleException("Erro no id da cópia");
    }
    public void validateEdition(String edition) throws BusinessRuleException {
        if (edition != null){
            if (edition.matches("[0-9]{1,2}") ) {
                return;
            }
        }
        throw new BusinessRuleException("Erro na edição do livro");
    }
    public void validateYear(String year) throws BusinessRuleException {
        if (year != null){
            if (year.matches("[0-9]{4}") ) {
                return;
            }
        }
        throw new BusinessRuleException("Erro no ano do livro");
    }
    public void validateString(String string) throws BusinessRuleException {
        if (string != null){
            return;
        }
        throw new BusinessRuleException("Erro no campo string");
    }
    public void validateName(String name) throws BusinessRuleException {
        if ( name != null){
            if (name.matches("[A-Za-zÀ-ȕ ']{1,200}") ) {
                return;
            }
        }
        throw new BusinessRuleException("Erro no nome do cliente");
    }
    public void validateIsbn(String isbn) throws BusinessRuleException {
        if ( isbn != null){
            if (isbn.matches("[0-9]{10,13}") ) {
                return;
            }
        }
        throw new BusinessRuleException("Erro no ISBN do livro");
    }
    public void validateCdd(String cdd) throws BusinessRuleException {
        if ( cdd != null){
            if (cdd.matches("[0-9]{1,3}(?:.[0-9]{1,3})?") ) {
                return;
            }
        }
        throw new BusinessRuleException("Erro no CDD do livro");
    }
    public void validateEmail(String email) throws BusinessRuleException{
        if (email != null){
            if (email.matches("^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$" )){
                return;
            }
        }
        throw new BusinessRuleException("Erro no campo email");
    }
    public void validateGender(Character gender) throws BusinessRuleException {
        if (gender != null){
            if (gender.equals('M')||gender.equals('F')||gender.equals('O')){
                return;
            }
        }
        throw new BusinessRuleException("Erro no campo gender");
    }
    public void validateDocument(String document, ClientRepository repository) throws BusinessRuleException {
        if (document != null){
            boolean exists = repository.existsByDocument(document);
            if (document.matches("[0-9]{11,13}")&& !exists){
                return;
            }
        }
        throw new BusinessRuleException("Erro no campo document");
    }

    public void validateIdClient(Integer id_client, ClientRepository clientRepository) throws BusinessRuleException {
        if (id_client != null){
            if (clientRepository.existsById(id_client)) {
                return;
            }
        }
        throw new BusinessRuleException("Erro no id do cliente");
    }

    public void validateIdBook(Integer id_book) throws BusinessRuleException {
        if (id_book != null){
            if (bookRepository.existsById(id_book))
                return;
        }
        throw new BusinessRuleException("Erro no id do livro");
    }
}
