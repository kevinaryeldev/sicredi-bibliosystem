package br.com.kevinaryel.bibliosystem.response;

import br.com.kevinaryel.bibliosystem.request.BookCreateRequest;
import lombok.Data;

@Data
public class BookResponse extends BookCreateRequest {
    private Integer id;
}
