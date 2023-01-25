package br.com.kevinaryel.bibliosystem.response;

import br.com.kevinaryel.bibliosystem.request.ClientCreateRequest;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponse {
    private String name;

    private String document;

    private String email;

    private Character gender;

    private LocalDate birth_date;
    private Integer id;
}
