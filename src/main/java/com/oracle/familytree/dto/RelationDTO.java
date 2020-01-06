package com.oracle.familytree.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RelationDTO {

	Long personId;
	Long relativeId;
	
	RelationType relationType;
}
