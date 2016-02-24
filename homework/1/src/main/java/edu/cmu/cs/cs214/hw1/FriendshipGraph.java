package edu.cmu.cs.cs214.hw1;

import java.util.LinkedList;
/**
 * The Class of FriendShip Graph.
 */
public class FriendshipGraph {
	private LinkedList<Person> friendshipGraph;
	/**
	 * Create a new LinkeList for friendship graph.
	 */
	public FriendshipGraph() {
		friendshipGraph = new LinkedList<>();
	}
	
	/**
	 * Add person as vertex in graph.
	 * @param person The person to be added to the graph.
	 */
	public void addVertex(Person person) {
		friendshipGraph.add(person);
	}
	
	/**
	 * Create relationship between two people in graph.
	 * @param person1 The 1st person in relationship.
	 * @param person2 The 2nd person in relationship.
	 */
	public void addEdge(Person person1, Person person2) {
		person1.addFriend(person2);
		person2.addFriend(person1);
	}
	
	/**
	 * Calculate distance between two people.
	 * @param person1 The 1st person in relationship.
	 * @param person2 The 2nd person in relationship.
	 * @return distance: The distance between two people.
	 */
	public int getDistance(Person person1, Person person2) {
		// return shortest distance with BFS method
		int distance = 0; // the default distance is 0
		if (person1 == person2) {
			return distance;
		} else {
			LinkedList<Person> queue = new LinkedList<>();
			queue.add(person1);
			person1.visit();
			while (!queue.isEmpty()) {
				Person p = queue.remove();
				LinkedList<Person> friends = p.getFriends();				
				if (friends.contains(person2)) {
					distance += 1;
					return distance;
				} else {
					for (Person friend:friends) {
						if (!friend.getVisit()) {
							queue.add(friend);
							friend.visit();
						}
					}
					distance += 1;
				}
			}
			if (person2.getVisit() == false) {
				distance = -1;
			}
			for (Person person : friendshipGraph) {
				person.resetVisit();
			}
			return distance;
		}
	}
}
