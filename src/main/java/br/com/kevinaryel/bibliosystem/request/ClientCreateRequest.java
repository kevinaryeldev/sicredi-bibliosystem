package br.com.kevinaryel.bibliosystem.request;

import lombok.*;

import java.time.LocalDate;
@Data
public class ClientCreateRequest {

    private String name;

    private String document;

    private String email;

    private Character gender;

    private LocalDate birth_date;
}
