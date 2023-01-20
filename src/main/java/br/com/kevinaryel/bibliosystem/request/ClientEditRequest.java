package br.com.kevinaryel.bibliosystem.request;

import lombok.*;

@Data
public class ClientEditRequest {
    private String email;
    private String name;
    private Character gender;
}
