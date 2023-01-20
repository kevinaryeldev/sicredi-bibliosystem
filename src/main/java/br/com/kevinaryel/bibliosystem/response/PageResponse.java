package br.com.kevinaryel.bibliosystem.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {
    private Long totalElements;
    private Integer pageQuantity;
    private Integer page;
    private Integer size;
    private List<T> elements;
}
