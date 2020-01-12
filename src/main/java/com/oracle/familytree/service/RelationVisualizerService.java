package com.oracle.familytree.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.oracle.familytree.dto.Location;
import com.oracle.familytree.dto.Person;
import com.oracle.familytree.dto.RelationType;
import com.oracle.familytree.dto.StackData;

@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Service
public class RelationVisualizerService {

	
	private static final int OFFSET_X = 2;

	private static final int OFFSET_Y = 2;

	@Autowired
	RelationService relationService;
	
	String[][] disp = new String[4][4];
	
	
	/**
	 * returns the family tree of the given person.
	 * 
	 * @param id
	 * @return
	 */
	public String visualize(Long id) {
		
		fillArray();
		
		Location initialLocation = new Location(0,0);
		Location parentLocation = initialLocation;
		Location currentCursorLocation = initialLocation;
		Stack<StackData> parents = new Stack<StackData>();
		parents.add(new StackData(id, parentLocation));
		disp[0][0] = String.valueOf("***");
		
		// recursively add parental information to the disp array
		while (!parents.isEmpty()) {
			
			for (int i =0 ; i< parents.size(); i++) {
				StackData stackData = parents.pop();
				parentLocation = stackData.getLocation();
				
				Location nextLocation = getNextLocation(parentLocation, currentCursorLocation);
				verifyCapacity(nextLocation);
				
				placePersonInLocation(stackData.getPersonId(), nextLocation, parentLocation);
				currentCursorLocation = nextLocation;
				
				Map<RelationType, List<Person>> rels = relationService.getRelatives(stackData.getPersonId());
				if (CollectionUtils.isEmpty(rels) || CollectionUtils.isEmpty(rels.get(RelationType.PARENT))) {
					//return "Person doesn't have any relatives";
				}else {
					
					for (Person p : rels.get(RelationType.PARENT)) {
						parents.add(new StackData(p.getId(), nextLocation));
					}
				}
			}
			
		}
		
		return displayTree();
	}

	
	private void fillArray() {
		for (String[] row : disp) {
			Arrays.fill(row, "   ");
		}
		
	}


	/**
	 * if next location is going out of bounds we will inflate the array
	 * @param nextLocation
	 */
	private void verifyCapacity(Location nextLocation) {
		if (disp.length <= nextLocation.getX() || disp[0].length <= nextLocation.getY()) {
			String[][] destination=Arrays.stream(disp)
                    .map(a ->  Arrays.copyOf(a, a.length*2))
                    .toArray(String[][]::new);
			
			destination = Arrays.copyOf(destination, destination.length*2);
			
			for (int i = disp.length; i < destination.length ; i ++) {
				destination[i] = new String[destination[0].length];
			}
			
			this.disp = destination;
			fillArray();
			verifyCapacity(nextLocation);
		}
		
	}


	private String displayTree() {
		StringBuilder result = new StringBuilder();
		for (String[] row : disp) {
			System.out.println(Arrays.toString(row));
			result.append(String.join("", row)+"\n");
		}
		return result.toString();
	}


	/**
	 * places vertical lines and horizontal lines in corresponding locations and finally places the person in location
	 * @param person
	 * @param nextLocation
	 * @param parentLocation
	 */
	private void placePersonInLocation(Long person, Location nextLocation, Location parentLocation) {
		placeVerticleLines(nextLocation, parentLocation);
		placeHoriZontalLines(nextLocation, parentLocation);	
		disp[nextLocation.getY()][nextLocation.getX()]= String.valueOf(person);
	}


	private void placeHoriZontalLines(Location nextLocation, Location parentLocation) {
		for (int i = parentLocation.getX()+1; i < nextLocation.getX(); i++) {
			disp[nextLocation.getY()][i] = "___";
		}
		
	}


	private void placeVerticleLines(Location nextLocation, Location parentLocation) {
		for (int i = parentLocation.getY()+1; i < nextLocation.getY(); i++) {
			disp[i][parentLocation.getX()] = " | ";
		}
		
	}


	/**
	 * 
	 * based on parent location and current cursor location it will calculates next location to place the person
	 * @param parentLocation
	 * @param currentCursorLocation
	 * @return
	 */
	private Location getNextLocation(Location parentLocation, Location currentCursorLocation) {
		
		return new Location(parentLocation.getX()+OFFSET_X, currentCursorLocation.getY()+OFFSET_Y);
	}

}
