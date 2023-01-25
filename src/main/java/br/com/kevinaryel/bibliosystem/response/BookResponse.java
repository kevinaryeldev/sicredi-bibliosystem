package br.com.kevinaryel.bibliosystem.response;

import br.com.kevinaryel.bibliosystem.request.BookCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse extends BookCreateRequest {
    private Integer id;
}
