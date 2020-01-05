package com.oracle.familytree.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class RelationDTO {

	Long personId;
	Long relativeId;
	
	RelationType relationType;
}
