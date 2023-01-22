package br.com.kevinaryel.bibliosystem.response;

import br.com.kevinaryel.bibliosystem.request.CopyCreateRequest;
import lombok.Data;

@Data
public class CopyResponse extends CopyCreateRequest {
    private Integer id;
}
