package br.com.kevinaryel.bibliosystem.repository;

import br.com.kevinaryel.bibliosystem.entity.CopyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CopyRepository extends JpaRepository<CopyEntity, Integer> {

    Page<CopyEntity> findCopyEntitiesByBook_Id(Integer bookId, PageRequest pageRequest);
}
