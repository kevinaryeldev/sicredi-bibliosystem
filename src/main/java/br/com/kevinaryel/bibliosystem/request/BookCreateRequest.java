package br.com.kevinaryel.bibliosystem.request;

import lombok.*;

@Data
public class BookCreateRequest {
    private String code;

    private String title;

    private String subtitle;

    private String publisher;

    private String author;

    private String cdd;
}
