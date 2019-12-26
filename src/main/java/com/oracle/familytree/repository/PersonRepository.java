package com.oracle.familytree.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.oracle.familytree.dto.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

}