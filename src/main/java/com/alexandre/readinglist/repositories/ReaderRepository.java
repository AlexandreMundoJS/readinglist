package com.alexandre.readinglist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.alexandre.readinglist.entities.Reader;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, String> {
    UserDetails findByUsername(String username);
}
