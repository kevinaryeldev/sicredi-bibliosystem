package br.com.kevinaryel.bibliosystem.request;

import lombok.Data;

@Data
public class LoanCreateRequest {
    private Integer id_copy;
    private Integer id_client;
}
