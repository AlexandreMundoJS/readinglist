package com.alexandre.readinglist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alexandre.readinglist.entities.Book;
import java.util.List;

@Repository
public interface ReadingListRepository extends JpaRepository<Book, Long>{
    List<Book> findByReader(String reader); 
}
