package br.com.kevinaryel.bibliosystem.response;

import lombok.Data;

@Data
public class CopyWithBookDetailsResponse extends CopyResponse {
    private String title;

    private String subtitle;

}
