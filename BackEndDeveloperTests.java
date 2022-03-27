// --== CS400 File Header Information ==--
// Name: Ryan Stevenson
// Email: rstevenson5@wisc.edu
// Team: KD
// Role: Backend Developer
// TA: Keren
// Lecturer: Gary Dahl
// Notes to Grader: none
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BackEndDeveloperTests {

	/**
	 * Tests to make sure that the root node is the first node
	 * added to the Red red black tree until other nodes are added
	 * that make it change. Test again to make sure the root does
	 * change when it is supposed to.
	 */
	@Test
	public void testRoot() {
		// Need to make internal class for RedBlackTree so it can
		// store scores and names
		RedBlackTree<Integer> rbTree = new RedBlackTree<Integer>();
		rbTree.insert(50, "P1");
		assertEquals("P1", rbTree.root.name);
		rbTree.insert(100, "P2");
		assertEquals("P1", rbTree.root.name);
		rbTree.insert(150, "P3");
		assertEquals("P2", rbTree.root.name);
	}

	/**
	 * Tests the insert method of the red black tree to make sure
	 * it inserts every node in the correct spot. The nodes added to
	 * this tree follow the pattern in the Red Black Tree Insert
	 * Demonstration video.
	 */
	@Test
	public void testInsert() {
		// Need to make the internal class for RedBlackTree able to
		// access children of each node
		RedBlackTree<Integer> rbTree = new RedBlackTree<Integer>();
		rbTree.insert(50, "P1");
		rbTree.insert(100, "P2");
		assertEquals("P2", rbTree.root.rightChild.name);
		rbTree.insert(150, "P3");
		assertEquals("P1", rbTree.root.leftChild.name);
		assertEquals("P3", rbTree.root.rightChild.name);
		rbTree.insert(200, "P4");
		assertEquals("P1", rbTree.root.leftChild.name);
		assertEquals("P3", rbTree.root.rightChild.name);
		assertEquals("P4", rbTree.root.rightChild.rightChild.name);
		rbTree.insert(250, "P5");
		assertEquals("P1", rbTree.root.leftChild.name);
		assertEquals("P3", rbTree.root.rightChild.leftChild.name);
		assertEquals("P4", rbTree.root.rightChild.name);
		assertEquals("P5", rbTree.root.rightChild.rightChild.name);
		rbTree.insert(300, "P6");
		assertEquals("P1", rbTree.root.leftChild.name);
		assertEquals("P3", rbTree.root.rightChild.leftChild.name);
		assertEquals("P4", rbTree.root.rightChild.name);
		assertEquals("P5", rbTree.root.rightChild.rightChild.name);
		assertEquals("P6", rbTree.root.rightChild.rightChild.rightChild.name);
		rbTree.insert(350, "P7");
		assertEquals("P1", rbTree.root.leftChild.name);
		assertEquals("P3", rbTree.root.rightChild.leftChild.name);
		assertEquals("P4", rbTree.root.rightChild.name);
		assertEquals("P5", rbTree.root.rightChild.rightChild.leftChild.name);
		assertEquals("P6", rbTree.root.rightChild.rightChild.name);
		assertEquals("P7", rbTree.root.rightChild.rightChild.rightChild.name);
		rbTree.insert(60, "P8");
		// The right subtree from the node will not be affected from now on
		assertEquals("P1", rbTree.root.leftChild.name);
		assertEquals("P8", rbTree.root.leftChild.rightChild.name);
		rbTree.insert(70, "P9");
		assertEquals("P1", rbTree.root.leftChild.leftChild.name);
		assertEquals("P8", rbTree.root.leftChild.name);
		assertEquals("P9", rbTree.root.leftChild.rightChild.name);
		rbTree.insert(80, "P10");
		assertEquals("P1", rbTree.root.leftChild.leftChild.name);
		assertEquals("P8", rbTree.root.leftChild.name);
		assertEquals("P9", rbTree.root.leftChild.rightChild.name);
		assertEquals("P10", rbTree.root.leftChild.rightChild.rightChild.name);
	}

	/**
	 * Tests the search method of the red black tree to make sure
	 * that searching for a name returns the correct score. This method
	 * creates a red black tree with the same nodes as the previous
	 * method and searches for every node
	 */
	@Test
	public void testSearch() {
		RedBlackTree<Integer> rbTree = new RedBlackTree<Integer>();
		rbTree.insert(50, "P1");
		rbTree.insert(100, "P2");
		rbTree.insert(150, "P3");
		rbTree.insert(200, "P4");
		rbTree.insert(250, "P5");
		rbTree.insert(300, "P6");
		rbTree.insert(350, "P7");
		rbTree.insert(60, "P8");
		rbTree.insert(70, "P9");
		rbTree.insert(80, "P10");
		assertEquals(50, rbTree.search("P1"));
		assertEquals(100, rbTree.search("P2"));
		assertEquals(150, rbTree.search("P3"));
		assertEquals(200, rbTree.search("P4"));
		assertEquals(250, rbTree.search("P5"));
		assertEquals(300, rbTree.search("P6"));
		assertEquals(350, rbTree.search("P7"));
		assertEquals(60, rbTree.search("P8"));
		assertEquals(70, rbTree.search("P9"));
		assertEquals(80, rbTree.search("P10"));
	}

	/**
	 * Tests the containsName() method of the red black tree to make sure
	 * that it returns true when calling the method with a name that
	 * is in the tree and false otherwise. This method creates the same
	 * red black tree as before and makes sure containsName() returns true
	 * when searching for names that are in the tree and false otherwise.
	 */
	@Test
	public void testContainsName() {
		RedBlackTree<Integer> rbTree = new RedBlackTree<Integer>();
		rbTree.insert(50, "P1");
		rbTree.insert(100, "P2");
		rbTree.insert(150, "P3");
		rbTree.insert(200, "P4");
		rbTree.insert(250, "P5");
		rbTree.insert(300, "P6");
		rbTree.insert(350, "P7");
		rbTree.insert(60, "P8");
		rbTree.insert(70, "P9");
		rbTree.insert(80, "P10");
		assertEquals(true, rbTree.containsName("P1"));
		assertEquals(true, rbTree.containsName("P2"));
		assertEquals(true, rbTree.containsName("P3"));
		assertEquals(true, rbTree.containsName("P4"));
		assertEquals(true, rbTree.containsName("P5"));
		assertEquals(true, rbTree.containsName("P6"));
		assertEquals(true, rbTree.containsName("P7"));
		assertEquals(true, rbTree.containsName("P8"));
		assertEquals(true, rbTree.containsName("P9"));
		assertEquals(true, rbTree.containsName("P10"));
		assertEquals(false, rbTree.containsName("P11"));
		assertEquals(false, rbTree.containsName("Scott"));
	}

	/**
	 * Tests the integrity of the red black tree by making sure there
	 * are never two red nodes in a row and that the black height is
	 * the same for every path. This method creates the same red black
	 * tree as before and makes sure it returns the correct black height
	 * for each node. It also makes sure that there is not two reds
	 * in a row anywhere in the tree.
	 */
	@Test
	public void testIntegrity() {
		// Need to make a blackHeight() method for the internal
		// class for RedBlackTree so we can see the black height
		// for each node
		// Also need to make a doubleRed() method for the internal
		// class that checks to see if the node is red. If it is,
		// return true if either the parent or a child is red.
		// This method should return false otherwise, and it will
		// always return false if the tree has no violations.
		// Also need a getNode() method in RedBlackTree that returns
		// the node when searching for a name. This is different
		// from the search() method that returns the score.
		RedBlackTree<Integer> rbTree = new RedBlackTree<Integer>();
		rbTree.insert(50, "P1");
		rbTree.insert(100, "P2");
		rbTree.insert(150, "P3");
		rbTree.insert(200, "P4");
		rbTree.insert(250, "P5");
		rbTree.insert(300, "P6");
		rbTree.insert(350, "P7");
		rbTree.insert(60, "P8");
		rbTree.insert(70, "P9");
		rbTree.insert(80, "P10");
		assertEquals(2, rbTree.root.leftChild.leftChild.blackHeight()); //P1
		assertEquals(false, rbTree.root.leftChild.leftChild.doubleRed());
		assertEquals(1, rbTree.root.blackHeight()); //P2
		assertEquals(false, rbTree.root.doubleRed());
		assertEquals(2, rbTree.root.rightChild.leftChild.blackHeight()); //P3
		assertEquals(false, rbTree.root.rightChild.leftChild.doubleRed());
		assertEquals(1, rbTree.root.rightChild.blackHeight()); //P4
		assertEquals(false, rbTree.root.rightChild.doubleRed());
		assertEquals(2, rbTree.root.rightChild.rightChild.leftChild.blackHeight()); //P5
		assertEquals(false, rbTree.root.rightChild.rightChild.leftChild.doubleRed());
		assertEquals(2, rbTree.root.rightChild.rightChild.blackHeight()); //P6
		assertEquals(false, rbTree.root.rightChild.rightChild.doubleRed());
		assertEquals(2, rbTree.root.rightChild.rightChild.rightChild.blackHeight()); //P7
		assertEquals(false, rbTree.root.rightChild.rightChild.rightChild.doubleRed());
		assertEquals(1, rbTree.root.leftChild.blackHeight()); //P8
		assertEquals(false, rbTree.root.leftChild.doubleRed());
		assertEquals(2, rbTree.root.leftChild.rightChild.blackHeight()); //P9
		assertEquals(false, rbTree.root.leftChild.rightChild.doubleRed());
		assertEquals(2, rbTree.root.leftChild.rightChild.rightChild.blackHeight()); //P10
		assertEquals(false, rbTree.root.leftChild.rightChild.rightChild.doubleRed());
	}
}
