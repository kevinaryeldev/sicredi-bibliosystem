package br.com.kevinaryel.bibliosystem.repository;

import br.com.kevinaryel.bibliosystem.entity.LoanEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<LoanEntity, Integer> {

    Page<LoanEntity> findLoanEntitiesByClient_Id(Integer clientId, PageRequest pageRequest);

    Page<LoanEntity> findByStatus(Character status, PageRequest pageRequest);
}
