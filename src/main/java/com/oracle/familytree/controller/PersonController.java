package com.oracle.familytree.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.familytree.dto.Person;
import com.oracle.familytree.service.personService;

@RestController
public class PersonController {

	@Autowired
	personService personService;
	
	@RequestMapping(value = "/person", method = RequestMethod.POST )
	public Person addPerson(@RequestBody Person person) {
		return personService.addPerson(person);
	}
	
	@RequestMapping(value = "/person", method = RequestMethod.GET)
	public List<Person> addPerson() {
		return personService.getAllPersons();
	}
}
