package br.com.kevinaryel.bibliosystem.repository;

import br.com.kevinaryel.bibliosystem.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity,Integer> {
    boolean existsByDocument(String document);
}
