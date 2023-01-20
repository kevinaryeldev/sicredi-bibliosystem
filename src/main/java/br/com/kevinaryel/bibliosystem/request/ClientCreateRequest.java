package br.com.kevinaryel.bibliosystem.request;

import lombok.Data;

import java.time.LocalDate;
@Data
public class ClientCreateRequest {

    private String name;

    private Integer document;

    private String email;

    private Character gender;

    private LocalDate birth_date;
}
