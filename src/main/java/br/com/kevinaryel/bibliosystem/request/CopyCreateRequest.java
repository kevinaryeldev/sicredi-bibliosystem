package br.com.kevinaryel.bibliosystem.request;


import lombok.Data;

@Data
public class CopyCreateRequest {
    private String year;

    private String property_code;

    private String edition;

    private Integer id_book;
}
