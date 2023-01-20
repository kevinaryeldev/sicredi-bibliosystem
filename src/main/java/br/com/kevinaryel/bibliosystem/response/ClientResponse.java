package br.com.kevinaryel.bibliosystem.response;

import br.com.kevinaryel.bibliosystem.request.ClientCreateRequest;
import lombok.*;

@Data
public class ClientResponse extends ClientCreateRequest {
    private Integer id;
}
