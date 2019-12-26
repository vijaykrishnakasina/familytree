package com.oracle.familytree.dto;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Relation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long relation_id;
	
	@OneToOne
	Person person;
	
	@OneToOne
	Person relatee;
	
	@Enumerated(EnumType.STRING)
	RelationType relationType;
}
