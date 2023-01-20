package br.com.kevinaryel.bibliosystem.request;

import lombok.Data;

import java.util.Date;
@Data
public class ClientCreateRequest {

    private String name;

    private Integer document;

    private String email;

    private Character gender;

    private Date birthDate;
}
