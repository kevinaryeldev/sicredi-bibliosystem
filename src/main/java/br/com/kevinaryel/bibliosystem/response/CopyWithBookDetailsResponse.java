package br.com.kevinaryel.bibliosystem.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CopyWithBookDetailsResponse extends CopyResponse {

    private Integer id_book;
    private String title;

    private String subtitle;

}
