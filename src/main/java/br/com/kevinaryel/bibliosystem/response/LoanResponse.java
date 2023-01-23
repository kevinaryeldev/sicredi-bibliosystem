package br.com.kevinaryel.bibliosystem.response;

import lombok.Data;

@Data
public class LoanResponse {
    private Integer id;
    private Character status;
    private String loan_date;
    private String return_date;
    private CopyResponse copy;
    private ClientResponse client;
}
