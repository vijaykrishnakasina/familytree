package com.oracle.familytree.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.familytree.dto.Person;
import com.oracle.familytree.repository.PersonRepository;

@Service
public class personService {


	@Autowired
	PersonRepository personRepo;
	
	public Person addPerson(Person person) {
		return personRepo.save(person);
	}

	public List<Person> getAllPersons() {
		return personRepo.findAll();
	}
}
