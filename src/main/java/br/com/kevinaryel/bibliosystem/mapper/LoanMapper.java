package br.com.kevinaryel.bibliosystem.mapper;

import br.com.kevinaryel.bibliosystem.entity.ClientEntity;
import br.com.kevinaryel.bibliosystem.entity.CopyEntity;
import br.com.kevinaryel.bibliosystem.entity.LoanEntity;
import br.com.kevinaryel.bibliosystem.response.ClientResponse;
import br.com.kevinaryel.bibliosystem.response.CopyResponse;
import br.com.kevinaryel.bibliosystem.response.LoanResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoanMapper {
    private final ObjectMapper objectMapper;

    public LoanResponse mapToResponse(LoanEntity loanEntity) {
        LoanResponse loanResponse = objectMapper.convertValue(loanEntity, LoanResponse.class);
        CopyEntity copyEntity = loanEntity.getCopy();
        CopyResponse copyResponse = objectMapper.convertValue(copyEntity, CopyResponse.class);
        copyResponse.setId_book(copyEntity.getBook().getId());
        loanResponse.setCopy(copyResponse);
        loanResponse.setClient(objectMapper.convertValue(loanEntity.getClient(), ClientResponse.class));
        return loanResponse;
    }

}
