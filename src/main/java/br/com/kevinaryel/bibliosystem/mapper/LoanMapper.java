package br.com.kevinaryel.bibliosystem.mapper;

//import br.com.kevinaryel.bibliosystem.entity.ClientEntity;
//import br.com.kevinaryel.bibliosystem.entity.CopyEntity;
import br.com.kevinaryel.bibliosystem.entity.LoanEntity;
//import br.com.kevinaryel.bibliosystem.repository.CopyRepository;
//import br.com.kevinaryel.bibliosystem.response.ClientResponse;
//import br.com.kevinaryel.bibliosystem.response.CopyResponse;
import br.com.kevinaryel.bibliosystem.response.LoanResponse;
//import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
//import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("loanMapper")public class LoanMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.createTypeMap(LoanEntity.class, LoanResponse.class)
                .addMappings(mapper -> {
                    mapper.map(LoanEntity::getId, LoanResponse::setId);
                    mapper.map(LoanEntity::getStatus, LoanResponse::setStatus);
                    mapper.map(LoanEntity::getLoan_date, LoanResponse::setLoan_date);
                    mapper.map(LoanEntity::getReturn_date, LoanResponse::setReturn_date);
                })
                .setPostConverter(toResponseConverter());
    }

    private static Converter<LoanEntity, LoanResponse> toResponseConverter() {
        return context -> {
            LoanResponse response = context.getDestination();
            LoanEntity entity = context.getSource();
            return response;
        };
    }
    public  LoanResponse toLoanResponse(LoanEntity loanEntity) {
        return modelMapper.map(loanEntity, LoanResponse.class);
    }
}
