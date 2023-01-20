package br.com.kevinaryel.bibliosystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Client")
@Table(name = "Client")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLIENT_SEQ")
    @SequenceGenerator(name = "CLIENT_SEQ", sequenceName = "SEQ_CLIENT", allocationSize = 1)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "document")
    private Integer document;
    @Column(name="email")
    private String email;
    @Column(name="gender")
    private Character gender;
    @Column(name = "birth_date")
    private Date birth_date;
}
