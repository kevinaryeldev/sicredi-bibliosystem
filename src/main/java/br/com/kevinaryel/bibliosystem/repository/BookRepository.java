package br.com.kevinaryel.bibliosystem.repository;

import br.com.kevinaryel.bibliosystem.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity,Integer> {

}
