package br.com.kevinaryel.bibliosystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Loan")
@Table(name = "Loan")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LoanEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOAN_SEQ")
    @SequenceGenerator(name = "LOAN_SEQ", sequenceName = "SEQ_LOAN", allocationSize = 1)
    private Integer id;

    @Column(name = "status")
    private Character status;

    @Column(name = "loan_date")
    private LocalDate loan_date;

    @Column(name = "return_date")
    private LocalDate return_date;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_copy", referencedColumnName = "id")
    private CopyEntity copy;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client", referencedColumnName = "id")
    private ClientEntity client;
}
