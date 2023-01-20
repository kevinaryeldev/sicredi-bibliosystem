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
@Entity(name = "author")
@Table(name = "author")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AuthorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUTHOR_SEQ")
    @SequenceGenerator(name = "AUTHOR_SEQ", sequenceName = "SEQ_AUTHOR", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @Column(name = "cater")
    private Integer cater;

    @Column(name = "name")
    private String name;
}
