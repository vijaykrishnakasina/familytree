package com.oracle.familytree.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oracle.familytree.dto.Person;
import com.oracle.familytree.dto.Relation;
import com.oracle.familytree.dto.RelationType;

public interface RelationRepository extends JpaRepository<Relation, Long> {

	List<Relation> findAllByPersonAndRelationType(Person person, RelationType grandParent);
	List<Relation> findAllByPerson_id(Long person_id); 	
	List<Relation> findAllByPersonAndRelateeAndRelationType(Person person,Person Relatee, RelationType relationType);

}
