package br.com.kevinaryel.bibliosystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Book")
@Table(name = "Book")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOK_SEQ")
    @SequenceGenerator(name = "BOOK_SEQ", sequenceName = "SEQ_BOOK", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "title")
    private String title;

    @Column(name =  "subtitle")
    private String subtitle;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "author")
    private String author;

    @Column(name = "cdd")
    private String cdd;
}
