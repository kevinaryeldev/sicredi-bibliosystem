package br.com.kevinaryel.bibliosystem.utils;

import br.com.kevinaryel.bibliosystem.exception.BusinessRuleException;
import br.com.kevinaryel.bibliosystem.repository.ClientRepository;
import org.springframework.context.annotation.Bean;

public class Validate {
    public void validateName(String name) throws BusinessRuleException {
        if ( name != null){
            if (name.length() >= 10 ) {
                return;
            }
        }
        throw new BusinessRuleException("Erro no nome do cliente");
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
}