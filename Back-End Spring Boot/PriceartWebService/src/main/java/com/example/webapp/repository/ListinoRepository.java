package com.example.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webapp.entity.Listini;

@Repository
public interface ListinoRepository extends JpaRepository<Listini,String> {

}
