package edu.cmu.cs.cs214.hw1;

/**
 * Class to help you test your graph implementation.
 */
public class Main {
	/**
	 * Main method to help you test your graph implementation.
	 * 
	 * @param args Arguments to the program.
	 */
	public static void main(String[] args) {
		// Un-comment the following code (CTRL + /).
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addEdge(rachel, ross);
		graph.addEdge(ross, ben);
		System.out.println(graph.getDistance(rachel, ross));   //should print 1
		System.out.println(graph.getDistance(rachel, ben));    //should print 2
		System.out.println(graph.getDistance(rachel, rachel)); //should print 0
		System.out.println(graph.getDistance(rachel, kramer)); //should print -1
		
		// More complicated and edge cases.
		FriendshipGraph graph2 = new FriendshipGraph();
        Person aa = new Person("AA");
        Person ba = new Person("BA");
        Person cc = new Person("CC");
        Person cd = new Person("CD");
        Person ab = new Person("AB");
        Person ec = new Person("EC");
        Person ce = new Person("CE");
        Person dd = new Person("DD");
        
        graph2.addVertex(aa);
        graph2.addVertex(ba);
        graph2.addVertex(cc);
        graph2.addVertex(cd);
        graph2.addVertex(ab);
        graph2.addVertex(ec);
        graph2.addVertex(ce);
        graph2.addVertex(dd);

        graph2.addEdge(aa, ba);
        graph2.addEdge(aa, cc);
        graph2.addEdge(aa, cd);
        graph2.addEdge(ba, ce);
        graph2.addEdge(cc, ec);
        graph2.addEdge(ec, ce);
        graph2.addEdge(dd, ce);


        System.out.println(graph2.getDistance(aa, dd)); //should print 3
        System.out.println(graph2.getDistance(ab, cc)); //should print -1
        System.out.println(graph2.getDistance(aa, aa)); //should print 0
        System.out.println(graph2.getDistance(aa, ce)); //should print 2
        System.out.println(graph2.getDistance(aa, ec)); //should print 2
        System.out.println(graph2.getDistance(cd, ec)); //should print 3
        System.out.println(graph2.getDistance(cd, dd)); //should print 5
        

	}
}
