package edu.cmu.cs.cs214.hw1;

import java.util.LinkedList;
/**
 * The Class of each person in relationship graph.
 * @author Alexander
 */
public class Person {
	private String name;
	private LinkedList<Person> friends;
	private boolean visited;
	
	/**
	 * The attributes of Person
	 * @param name The name of the person
	 */
	public Person(String name) {
		this.name = name;
		friends = new LinkedList<>();
		visited = false;
	}
	
	/**
	 * Get name of one person.
	 * @return The name of the person.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Add one person as friend.
	 * @param person The another person as friend.
	 */
	public void addFriend(Person person) {
		friends.add(person);
	}
	
	/**
	 * Get the person's friend list.
	 * @return friend list.
	 */
	public LinkedList<Person> getFriends() {
		return friends;
	}
	
	/**
	 * represent have visited this person.
	 */
	public void visit() {
		visited = true;
	}
	
	/**
	 * Get whether this person has been visited.
	 * @return boolean value whether has been visited.
	 */
	public boolean getVisit() {
		return visited;
	}
	
	/**
	 * reset the person's state of visited.
	 */
	public void resetVisit() {
		visited = false;
	}
	
	/**
	 * Return the name of this person.
	 * @return the name of this person.
	 */
	public String toString(){
		return name;
	}
}
