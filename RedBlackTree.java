// --== CS400 File Header Information ==--
// Name: Ryan Stevenson
// Email: rstevenson5@wisc.edu
// Team: KD
// Role: Backend Developer
// TA: Keren
// Lecturer: Gary Dahl
// Notes to Grader: none
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Stack;
import java.lang.IllegalArgumentException;

/**
 * Red-Black Tree implementation with a Node inner class for representing
 * the nodes of the tree. Currently, this implements a Binary Search Tree that
 * we will turn into a red black tree by modifying the insert functionality.
 * In this activity, we will start with implementing rotations for the binary
 * search tree insert algorithm. You can use this class' insert method to build
 * a regular binary search tree, and its toString method to display a level-order
 * traversal of the tree.
 */
public class RedBlackTree<T extends Comparable<T>> implements SortedCollectionInterface<T> {

    /**
     * This class represents a node holding a single value within a binary tree
     * the parent, left, and right child references are always maintained.
     */
    protected static class Node<T> {
        public T data;
        public String name;
        public Node<T> parent; // null for root node
        public Node<T> leftChild; 
        public Node<T> rightChild;
        private int blackHeight;
        public Node(T data) { this.data = data; }
        /**
         * Two-argument constructor for Node object that assigns a name for the node.
         * This is necessary for creating a score board for the trivia game
         * @param data The comparable data that is stored in the node
         * @param name The node's name
         */
        public Node(T data, String name) {
            this.data = data;
            this.name = name;
        }
        public boolean isBlack = false;
        /**
         * @return true when this node has a parent and is the left child of
         * that parent, otherwise return false
         */
        public boolean isLeftChild() {
            return parent != null && parent.leftChild == this;
        }
        /**
         * @return true when this node has a parent and is the right child of
         * that parent, otherwise return false
         */
        public boolean isRightChild() {
            return parent != null && parent.rightChild == this;
        }
        /**
	 * This method is used for testing. It checks to see if a node is red. If it is,
	 * return true if either a parent or child is also red. 
         * @return true if this node and either its parent or one of its children are red,
         * false otherwise
         */
        public boolean doubleRed() {
            if (isBlack) {
        	if (parent != null) {
        	    if (parent.isBlack) {
        		return true;
        	    }
        	}
        	if (leftChild != null) {
        	    if (leftChild.isBlack) {
        		return true;
        	    }
        	}
        	if (rightChild != null) {
        	    if (rightChild.isBlack) {
        		return true;
        	    }
        	}
            }
            return false;
        }
        /**
         * @return The black height for this node
         */
        public int blackHeight() {
            calculateBlackHeight(this);
            return blackHeight;
        }
        /**
         * Calculates the black height. This is a recursive helper method for blackHeight()
         * @param subtree The current subtree
         */
        private void calculateBlackHeight(Node<T> subtree) {
            if (subtree.isBlack) {
        	blackHeight++;
            }
            if (subtree.parent != null) {
        	calculateBlackHeight(subtree.parent);
            }
        }
        /**
         * This method performs a level order traversal of the tree rooted
         * at the current node. The string representations of each data value
         * within this tree are assembled into a comma separated string within
         * brackets (similar to many implementations of java.util.Collection).
         * Note that the Node's implementation of toString generates a level
         * order traversal. The toString of the RedBlackTree class below
         * produces an inorder traversal of the nodes / values of the tree.
         * This method will be helpful as a helper for the debugging and testing
         * of your rotation implementation.
         * @return string containing the values of this tree in level order
         */
        @Override
        public String toString() {
            String output = "[";
            LinkedList<Node<T>> q = new LinkedList<>();
            q.add(this);
            while(!q.isEmpty()) {
                Node<T> next = q.removeFirst();
                if(next.leftChild != null) q.add(next.leftChild);
                if(next.rightChild != null) q.add(next.rightChild);
                output += next.data.toString();
                if(!q.isEmpty()) output += ", ";
            }
            return output + "]";
        }
    }

    protected Node<T> root; // reference to root node of tree, null when empty
    protected int size = 0; // the number of values in the tree
    private static int i;
    private static String topVals;
    private ArrayList<String> reverseOrder;
    
    /**
     * Performs a naive insertion into a binary search tree: adding the input
     * data value to a new node in a leaf position within the tree. After  
     * this insertion, no attempt is made to restructure or balance the tree.
     * This tree will not hold null references, nor duplicate data values.
     * @param data to be added into this binary search tree
     * @return true if the value was inserted, false if not
     * @throws NullPointerException when the provided data argument is null
     * @throws IllegalArgumentException when the newNode and subtree contain
     *      equal data references
     */
    @Override
    public boolean insert(T data) throws NullPointerException, IllegalArgumentException {
        // null references cannot be stored within this tree
        if (data == null) {
            throw new NullPointerException("This RedBlackTree cannot store null references.");
        }
        Node<T> newNode = new Node<>(data);
        if (root == null) { 
            root = newNode; 
            size++; 
            root.isBlack = true;
            return true; 
        } // add first node to an empty tree
        else {
            boolean returnValue = insertHelper(newNode,root); // recursively insert into subtree
            if (returnValue) {
        	size++;
            }
	    else {
		throw new IllegalArgumentException("This RedBlackTree already contains that value.");
	    }
            root.isBlack = true;
            return returnValue;
        }
    }
    
    /**
     * Two-argument insert method used to insert a node with a name. This method is the same as
     * the other insert method, except the node that is added will have a value and a name instead
     * of just having a value.
     * @param data to be added into this binary search tree
     * @param name The name assigned to the node to be added to the tree
     * @return true if the value was inserted, false if not
     * @throws NullPointerException when the provided data argument is null
     * @throws IllegalArgumentException when the newNode and subtree contain equal data references
     */
    public boolean insert(T data, String name) throws NullPointerException, IllegalArgumentException {
        // null references cannot be stored within this tree
        if (data == null) {
            throw new NullPointerException("This RedBlackTree cannot store null references.");
        }
        Node<T> newNode = new Node<>(data, name);
        if (root == null) { 
            root = newNode; 
            size++; 
            root.isBlack = true;
            return true; 
        } // add first node to an empty tree
        else {
            boolean returnValue = insertHelper(newNode,root); // recursively insert into subtree
            if (returnValue) {
        	size++;
            }
	    else {
		throw new IllegalArgumentException("This RedBlackTree already contains that value.");
	    }
            root.isBlack = true;
            return returnValue;
        }
    }

    /** 
     * Recursive helper method to find the subtree with a null reference in the
     * position that the newNode should be inserted, and then extend this tree
     * by the newNode in that position.
     * @param newNode is the new node that is being added to this tree
     * @param subtree is the reference to a node within this tree which the 
     *      newNode should be inserted as a descenedent beneath
     * @return true is the value was inserted in subtree, false if not
     */
    private boolean insertHelper(Node<T> newNode, Node<T> subtree) {
        int compare = newNode.data.compareTo(subtree.data);
        // do not allow duplicate values to be stored within this tree
        if(compare == 0) return false;

        // store newNode within left subtree of subtree
        else if(compare < 0) {
            if(subtree.leftChild == null) { // left subtree empty, add here
                subtree.leftChild = newNode;
                newNode.parent = subtree;
                enforceRBTreePropertiesAfterInsert(newNode);
                return true;
            // otherwise continue recursive search for location to insert
            } else return insertHelper(newNode, subtree.leftChild);
        }

        // store newNode within the right subtree of subtree
        else { 
            if(subtree.rightChild == null) { // right subtree empty, add here
                subtree.rightChild = newNode;
                newNode.parent = subtree;
                enforceRBTreePropertiesAfterInsert(newNode);
                return true;
            // otherwise continue recursive search for location to insert
            } else return insertHelper(newNode, subtree.rightChild);
        }
    }
    
    /**
     * Recursive helper method used to fix any red node violations when inserting a new node
     * @param newNode The new red node added to the tree
     */
    private void enforceRBTreePropertiesAfterInsert(Node<T> newNode) {
	if (newNode.parent != null) {
	    // if the new node is red and has a red parent, there is a violation
	    if (!newNode.isBlack && !newNode.parent.isBlack) {
		// case 1: parent's sibling is black and on OPPOSITE side from newNode
		// rotate and color swap parent with grandparent
		// here, the black node is a null node
		if ((newNode.isLeftChild() && newNode.parent.parent.rightChild == null) ||
			(!newNode.isLeftChild() && newNode.parent.parent.leftChild == null)) {
		    newNode.parent.isBlack = true;
		    newNode.parent.parent.isBlack = false;
		    rotate(newNode.parent, newNode.parent.parent);
		}
		// case 1 without null values
		else if ((newNode.isLeftChild() && newNode.parent.parent.rightChild.isBlack) ||
			(!newNode.isLeftChild() && newNode.parent.parent.leftChild.isBlack)) {
		    newNode.parent.isBlack = true;
		    newNode.parent.parent.isBlack = false;
		    rotate(newNode.parent, newNode.parent.parent);
		}
		// case 2: parent's sibling is black and on SAME side as newNode
		// rotate newNode with parent, then follow steps for case 1
		// here, the black node is a null node
		else if ((newNode.isLeftChild() && newNode.parent.parent.leftChild == null) ||
			(!newNode.isLeftChild() && newNode.parent.parent.rightChild == null)) {
		    rotate(newNode, newNode.parent);
		    // after the first rotate, newNode becomes the parent of the problem node,
		    // so instead of color swapping and roatating the parent with the grandparent,
		    // color swap and rotate newNode with its parent
		    newNode.isBlack = true;
		    newNode.parent.isBlack = false;
		    rotate(newNode, newNode.parent);
		}
		// case 2 without null values
		else if ((newNode.isLeftChild() && newNode.parent.parent.leftChild.isBlack) ||
			(!newNode.isLeftChild() && newNode.parent.parent.rightChild.isBlack)) {
		    rotate(newNode, newNode.parent);
		    newNode.parent.isBlack = true;
		    newNode.parent.parent.isBlack = false;
		    rotate(newNode, newNode.parent);
		}
		// case 3: parent's sibling is red
		// set parent and parent's sibling to black, grandparent to red, and recursively call
		// this method with the grandparent as the potential violating node
		else if (newNode.parent.isLeftChild() && !newNode.parent.parent.rightChild.isBlack) {
		    newNode.parent.isBlack = true;
		    newNode.parent.parent.rightChild.isBlack = true;
		    newNode.parent.parent.isBlack = false;
		    enforceRBTreePropertiesAfterInsert(newNode.parent.parent);
		}
		// still case 3
		else if (!newNode.parent.isLeftChild() && !newNode.parent.parent.leftChild.isBlack) {
		    newNode.parent.isBlack = true;
		    newNode.parent.parent.leftChild.isBlack = true;
		    newNode.parent.parent.isBlack = false;
		    enforceRBTreePropertiesAfterInsert(newNode.parent.parent);
		}
	    }
	}
	
    }
    
    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a leftChild of the provided parent, this
     * method will perform a right rotation. When the provided child is a
     * rightChild of the provided parent, this method will perform a left rotation.
     * When the provided nodes are not related in one of these ways, this method
     * will throw an IllegalArgumentException.
     * @param child is the node being rotated from child to parent position
     *      (between these two node arguments)
     * @param parent is the node being rotated from parent to child position
     *      (between these two node arguments)
     * @throws IllegalArgumentException when the provided child and parent
     *      node references are not initially (pre-rotation) related that way
     */
    private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
	Node<T> temp;
	if (parent.leftChild == child) {
	    temp = child.rightChild;
	    if (parent.parent != null) {
		if (parent.parent.leftChild != null) {
		    if (parent.parent.leftChild.equals(parent)) {
			parent.parent.leftChild = child;
		    }
		}
		if (parent.parent.rightChild != null) {
		    if (parent.parent.rightChild.equals(parent)) {
			parent.parent.rightChild = child;
		    }
		}
	    }
	    else {
		root = child;
	    }
	    child.rightChild = parent;
	    parent.leftChild = temp;
	    child.parent = parent.parent;
	    parent.parent = child;
	}
	else if (parent.rightChild == child) {
	    temp = child.leftChild;
	    if (parent.parent != null) {
		if (parent.parent.leftChild != null) {
		    if (parent.parent.leftChild.equals(parent)) {
			parent.parent.leftChild = child;
		    }
		}
		if (parent.parent.rightChild != null) {
		    if (parent.parent.rightChild.equals(parent)) {
			parent.parent.rightChild = child;
		    }
		}
	    }
	    else {
		root = child;
	    }
	    child.leftChild = parent;
	    parent.rightChild = temp;
	    child.parent = parent.parent;
	    parent.parent = child;
	}
	else {
	    throw new IllegalArgumentException("Must select a valid child-parent pair to rotate");
	}
    }
    
    /**
     * Returns a string representation of the top values in the tree
     * @param numOfVals The number of values to be stored into the string
     * @return A string representation of the tree's top values
     */
    public String getTopValues(int numOfVals) {
	this.i = 0;
	this.topVals = "";
	return getTopValuesHelper(root, numOfVals, this.i);
    }
    
    /**
     * Recursive helper method used to search through the tree looking for the highest values
     * @param subtree The current subtree that is being searched
     * @param numOfVals The total number of values to be stored into the string
     * @param i The amount of values already stored into the string
     * @return A string representation of the tree's top values
     */
    private String getTopValuesHelper(Node<T> subtree, int numOfVals, int i) {
	if (subtree == null) {
	    return "";
	}
	getTopValuesHelper(subtree.rightChild, numOfVals, this.i);
	if (this.i < numOfVals) {
	    topVals += subtree.name + ": " + subtree.data + "\n";
	    this.i++;
	}
	getTopValuesHelper(subtree.leftChild, numOfVals, this.i);
	return topVals;
    }
    
    /**
     * Gets placement of the node with the specified name based on its data. 
     * @param name The name of the node to be searched for
     * @return The placement of the node with the specified name
     * @throws NoSuchElementExcetpion when no node in the tree has the given name
     */
    public int getPlacement(String name) {
	reverseOrder = new ArrayList<>();
	getPlacementHelper(root);
	if (reverseOrder.indexOf(name) > -1) {
	    return reverseOrder.indexOf(name) + 1;
	}
	else {
	    throw new NoSuchElementException("The given name is not in the red black tree");
	}
    }
    
    /**
     * Private recursive helper method used to create an array list of names of nodes in 
     * reverse order based on the nodes' data. This makes it so getPlacement() can find 
     * the placement of a node given a name.
     * @param subtree The current subtree
     * @return An array list of strings representing the nodes of the tree in reverse order
     */
    private void getPlacementHelper(Node<T> subtree) {
	if (subtree.rightChild != null) {
	    getPlacementHelper(subtree.rightChild);
	}
	if (subtree.name != null) {
	    reverseOrder.add(subtree.name);
	}
	else if(subtree != null) {
	    reverseOrder.add("NoName");
	}
	if (subtree.leftChild != null) {
	    getPlacementHelper(subtree.leftChild);
	}
    }
    
    /**
     * Get the size of the tree (its number of nodes).
     * @return the number of nodes in the tree
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Method to check if the tree is empty (does not contain any node).
     * @return true of this.size() return 0, false if this.size() > 0
     */
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Checks whether the tree contains the value *data*.
     * @param data the data value to test for
     * @return true if *data* is in the tree, false if it is not in the tree
     */
    @Override
    public boolean contains(T data) {
        // null references will not be stored within this tree
        if(data == null) throw new NullPointerException(
            "This RedBlackTree cannot store null references.");
        return this.containsHelper(data, root);
    }

    /**
     * Recursive helper method that recurses through the tree and looks
     * for the value *data*.
     * @param data the data value to look for
     * @param subtree the subtree to search through
     * @return true of the value is in the subtree, false if not
     */
    private boolean containsHelper(T data, Node<T> subtree) {
        if (subtree == null) {
            // we are at a null child, value is not in tree
            return false;
        } else {
            int compare = data.compareTo(subtree.data);
            if (compare < 0) {
                // go left in the tree
                return containsHelper(data, subtree.leftChild);
            } else if (compare > 0) {
                // go right in the tree
                return containsHelper(data, subtree.rightChild);
            } else {
                // we found it :)
                return true;
            }
        }
    }
    
    /**
     * Checks whether the tree contains a specified name
     * @param name The name of the player to look for
     * @return true if the name is in the tree, false otherwise
     */
    public boolean containsName(String name) {
        if(name.equals(null)) {
            throw new NullPointerException("Cannot search for null names");
        }
        return this.containsNameHelper(name, root);
    }
    
    /**
     * Recursive helper method that recursed through the whole tree to find the given name.
     * This method also acts as a helper method for the searchHelper() method
     * @param name The name of the player to look for
     * @param subtree The subtree to search through
     * @return true if the name is in the subtree, false otherwise
     */
    private boolean containsNameHelper(String name, Node<T> subtree) {
        if (subtree == null) {
            // we are at a null child, value is not in tree
            return false;
        } 
        else {
            if (name.equals(subtree.name)) {
        	return true;
            }
            else {
        	if (this.containsNameHelper(name, subtree.leftChild)) {
        	    return true;
        	}
        	else if (this.containsNameHelper(name, subtree.rightChild)) {
        	    return true;
        	}
        	else {
        	    return false;
        	}
            }
        }
    }
    
    /**
     * Searches for a given name in the tree
     * @param name The name of the player to look for
     * @return The data stored in the node associated with the given name
     * @throws NullPointerException if the given name is null
     */
    public T search(String name) {
        if(name.equals(null)) {
            throw new NullPointerException("Cannot search for null names");
        }
        return this.searchHelper(name, root);
    }
    
    /**
     * Recursive helper method that recursed through the whole tree to find the given name
     * @param name The name of the player to look for
     * @param subtree The subtree to search through
     * @return The data stored in the node associated with the given name
     * @throws NoSuchElementException if the given name is not in the subtree
     */
    private T searchHelper(String name, Node<T> subtree) {
        if (subtree == null) {
            // we are at a null child, value is not in tree
            throw new NoSuchElementException("The given name was not found in the subtree");
        } 
        else {
            if (name.equals(subtree.name)) {
        	return subtree.data;
            }
            else {
        	if (this.containsNameHelper(name, subtree.leftChild)) {
        	    return this.searchHelper(name, subtree.leftChild);
        	}
        	else if (this.containsNameHelper(name, subtree.rightChild)) {
        	    return this.searchHelper(name, subtree.rightChild);
        	}
        	else {
        	    throw new NoSuchElementException("The given name was not found in the subtree");
        	}
            }
        }
    }
    

    /**
     * Returns an iterator over the values in in-order (sorted) order.
     * @return iterator object that traverses the tree in in-order sequence
     */
    @Override
    public Iterator<T> iterator() {
        // use an anonymous class here that implements the Iterator interface
        // we create a new on-off object of this class everytime the iterator
        // method is called
        return new Iterator<T>() {
            // a stack and current reference store the progress of the traversal
            // so that we can return one value at a time with the Iterator
            Stack<Node<T>> stack = null;
            Node<T> current = root;

            /**
             * The next method is called for each value in the traversal sequence.
             * It returns one value at a time.
             * @return next value in the sequence of the traversal
             * @throws NoSuchElementException if there is no more elements in the sequence
             */
            public T next() {
                // if stack == null, we need to initialize the stack and current element
                if (stack == null) {
                    stack = new Stack<Node<T>>();
                    current = root;
                }
                // go left as far as possible in the sub tree we are in until we hit a null
                // leaf (current is null), pushing all the nodes we fund on our way onto the
                // stack to process later
                while (current != null) {
                    stack.push(current);
                    current = current.leftChild;
                }
                // as long as the stack is not empty, we haven't finished the traversal yet;
                // take the next element from the stack and return it, then start to step down
                // its right subtree (set its right sub tree to current)
                if (!stack.isEmpty()) {
                    Node<T> processedNode = stack.pop();
                    current = processedNode.rightChild;
                    return processedNode.data;
                } else {
                    // if the stack is empty, we are done with our traversal
                    throw new NoSuchElementException("There are no more elements in the tree");
                }

            }

            /**
             * Returns a boolean that indicates if the iterator has more elements (true),
             * or if the traversal has finished (false)
             * @return boolean indicating whether there are more elements / steps for the traversal
             */
            public boolean hasNext() {
                // return true if we either still have a current reference, or the stack
                // is not empty yet
                return !(current == null && (stack == null || stack.isEmpty()) );
            }
            
        };
    }

    /**
     * This method performs an inorder traversal of the tree. The string 
     * representations of each data value within this tree are assembled into a
     * comma separated string within brackets (similar to many implementations 
     * of java.util.Collection, like java.util.ArrayList, LinkedList, etc).
     * Note that this RedBlackTree class implementation of toString generates an
     * inorder traversal. The toString of the Node class class above
     * produces a level order traversal of the nodes / values of the tree.
     * @return string containing the ordered values of this tree (in-order traversal)
     */
    @Override
    public String toString() {
        // use the inorder Iterator that we get by calling the iterator method above
        // to generate a string of all values of the tree in (ordered) in-order
        // traversal sequence
        Iterator<T> treeNodeIterator = this.iterator();
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        if (treeNodeIterator.hasNext())
            sb.append(treeNodeIterator.next());
        while (treeNodeIterator.hasNext()) {
            T data = treeNodeIterator.next();
            sb.append(", ");
            sb.append(data.toString());
        }
        sb.append(" ]");
        return sb.toString();
    }

}
