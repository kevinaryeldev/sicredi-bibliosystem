package br.com.kevinaryel.bibliosystem.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanResponse {
    private Integer id;
    private Character status;
    private String loan_date;
    private String return_date;
}
