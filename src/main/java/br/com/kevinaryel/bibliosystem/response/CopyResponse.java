package br.com.kevinaryel.bibliosystem.response;

import br.com.kevinaryel.bibliosystem.request.CopyCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CopyResponse extends CopyCreateRequest {
    private Integer id;

    private String year;

    private String property_code;

    private String edition;
}
