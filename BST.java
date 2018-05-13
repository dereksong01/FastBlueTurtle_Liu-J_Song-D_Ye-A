/*====================================
So what we still need is a way for the remove() method to remove a node who has 2 non-leaf children


=====================================*/

/*****************************************************
 * class BST - v1:partial
 * Implementation of the BINARY SEARCH TREE abstract data type (ADT)
 *
 * A BST maintains the invariant that, for any node N with value V,
 * LV && VR, where L and R are node values in N's left and right
 * subtrees, respectively.
 * (Any value in a node's left subtree must be less than its value,
 *  and any value in its right subtree must be greater.)
 * This BST only holds ints (its nodes have int cargo)
 *****************************************************/

public class BST
{

    //instance variables / attributes of a BST:
    private TreeNode _root;

    /*****************************************************
     * default constructor
     *****************************************************/
    public BST( )
    {
	_root = null;
    }


    /*****************************************************
     * void insert( int )
     * Adds a new data element to tree.
     *****************************************************/
    public void insert( int newVal )
    {
	TreeNode newNode = new TreeNode( newVal );

	if ( _root == null ) {
	    _root = newNode;
	    return;
	}
	insert( _root, newNode );
    }
    //recursive helper for insert(int)
    public void insert( TreeNode stRoot, TreeNode newNode )
    {
	if ( newNode.getValue() < stRoot.getValue() ) {
	    //if no left child, make newNode the left child
	    if ( stRoot.getLeft() == null )
		stRoot.setLeft( newNode );
	    else //recurse down left subtree
		insert( stRoot.getLeft(), newNode );
	    return;
	}
	else { // new val >= curr, so look down right subtree
	    //if no right child, make newNode the right child
	    if ( stRoot.getRight() == null )
		stRoot.setRight( newNode );
	    else //recurse down right subtree
		insert( stRoot.getRight(), newNode );
	    return;
	}
    }//end insert()


    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //~~~~~~~~~~~~~v~~TRAVERSALS~~v~~~~~~~~~~~~~~~~~~~~~

    // each traversal should simply print to standard out
    // the nodes visited, in order

    //process root, recurse left, recurse right
    public void preOrderTrav()
    {
	preOrderTrav( _root );
    }
    public void preOrderTrav( TreeNode currNode )
    {
	if ( currNode == null ) //stepped beyond leaf
	    return;
	System.out.print( currNode.getValue() + " " );
	preOrderTrav( currNode.getLeft() );
	preOrderTrav( currNode.getRight() );
    }

    //recurse left, process root, recurse right
    public void inOrderTrav()
    {
	inOrderTrav( _root );
    }
    public void inOrderTrav( TreeNode currNode )
    {
	if ( currNode == null ) //stepped beyond leaf
	    return;
	inOrderTrav( currNode.getLeft() );
	System.out.print( currNode.getValue() + " " );
	inOrderTrav( currNode.getRight() );
    }

    //recurse left, recurse right, process root
    public void postOrderTrav()
    {
	postOrderTrav( _root );
    }
    public void postOrderTrav( TreeNode currNode )
    {
	if ( currNode == null ) //stepped beyond leaf
	    return;
	postOrderTrav( currNode.getLeft() );
	postOrderTrav( currNode.getRight() );
	System.out.print( currNode.getValue() + " "  );
    }

    //~~~~~~~~~~~~~^~~TRAVERSALS~~^~~~~~~~~~~~~~~~~~~~~~
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


    /*****************************************************
     * TreeNode search(int)
     * returns pointer to node containing target,
     * or null if target not found
     **Precondition: The tree is sorted in in-order 
     *****************************************************/

    public TreeNode search( int target )
    {
	TreeNode temp = _root;

	while(temp != null && temp.getValue() != target){
	    if(target < temp.getValue()){
		temp = temp.getLeft();
	    }
	    else{ //target > temp.getValue()
		temp = temp.getRight();
	    }
	}

	return temp;

    }

    /*****************************************************
     * int height()
     * returns height of this tree (length of longest leaf-to-root path)
     * eg: a 1-node tree has height 0
     *****************************************************/
    public int height()
    {
	return heightRec(_root) - 1;
    }

    private int heightRec(TreeNode current){
        if(current == null){
	    return 0;
        }
        //adds 1 plus the larger height (either the left subtree or right subtree)
        else { //current != null
	    return 1 + Math.max(heightRec(current.getLeft()), heightRec(current.getRight()) ); 
        }

    }


    /*****************************************************
     * int numLeaves()
     * returns number of leaves in tree
     *****************************************************/
    public int numLeaves()
    {
	return numLeavesRec(_root);
    }

    private int numLeavesRec(TreeNode current){
	if(current == null){
	    return 0;
	}
	else if(current.getLeft() == null && current.getRight() == null){
	    return 1;
	}
	else{
	    return numLeavesRec(current.getLeft()) + numLeavesRec(current.getRight());
	}
    }


    private TreeNode maxFinder(TreeNode current){
    	while(current.getRight() != null){
    		current = current.getRight();
    	}

    	return current;
    }

    private boolean isLeaf(TreeNode node){
    	return (node.getRight() == null && node.getLeft() == null);
    }


//preconditions: you do not remove the _root of the whole tree

    public TreeNode remove(int target){
	TreeNode follow = _root;
	TreeNode temp = _root;

		if(target < temp.getValue()){
	    temp = temp.getLeft();
        }
        else if(target > temp.getValue()){
	    temp = temp.getRight();
        }

        //finds the target and also changes position of follower
	while(temp != null && temp.getValue() != target){
	    if(target < temp.getValue()){
		TreeNode t = temp;
		follow = t;
		temp = temp.getLeft();
	    }
	    else{ //target > temp.getValue()
		TreeNode t = temp;
		follow = t;
		temp = temp.getRight();
	    }
	}

	System.out.println(follow.getValue());
	System.out.println(temp.getValue());

	//if the target is a leaf
	if(isLeaf(temp)){
	    if(follow.getValue() < temp.getValue()){
		follow.setRight(null);
	    }
	    else{ //follow.getValue() > temp.getValue()
		follow.setLeft(null);
	    }
	}

	//if the target has both a left and right child
	else if(temp.getRight() != null && temp.getLeft() != null) {

		//if the target has two leaf childs
		if(  isLeaf(temp.getRight()) && isLeaf(temp.getLeft())  ){
		    if (follow.getValue() < temp.getValue()) {
			temp.getRight().setLeft(follow.getRight().getLeft()); // redirects the pointer of the promoted node to 
			follow.setRight(temp.getRight()); // this would promote the right subtree
		    }
		    else { // follow.getValue() > temp.getValue()
			temp.getLeft().setRight(follow.getLeft().getRight()); // redirects
			follow.setLeft(temp.getLeft()); // promotes the left subtree
		    }
		}

		else{
			if(follow.getValue() > temp.getValue()){
				follow.setLeft(maxFinder(temp.getLeft())); //finds the max value of the left of the target and replaces temp with it
				follow.getLeft().setLeft(temp.getLeft()); //rearranges the new node with the same stuff that temp pointed to
				follow.getLeft().setRight(temp.getRight());
				temp = follow.getLeft(); //this new node is now the new temp
			}
			else{ //follow.getValue() < temp.getValue()
				follow.setRight(maxFinder(temp.getLeft()));
				follow.getRight().setLeft(temp.getLeft());
				follow.getRight().setRight(temp.getRight());
				temp = follow.getRight();
			}
		}



	}//end if else
	
	//if the target has one right child
	else if(temp.getLeft() == null){
	    if(follow.getValue() < temp.getValue()){
		follow.setRight(temp.getRight());
	    }
	    else{ //follow.getValue > temp.getValue()
		follow.setLeft(temp.getRight());
	    }
	}

	//if the target has one left child
	else if(temp.getRight() == null){
	    if(follow.getValue() < temp.getValue()){
		follow.setRight(temp.getLeft());
	    }
	    else{ //follow.getValue > temp.getValue()
		follow.setLeft(temp.getLeft());
	    }
	}





	return temp;
    }




    //main method for testing
    public static void main( String[] args )
    {
	BST arbol = new BST(); 
	BST treee = new BST();

	//PROTIP: sketch state of tree after each insertion
	//        ...BEFORE executing these.
	arbol.insert( 4 );
	arbol.insert( 2 );
	arbol.insert( 5 );
	arbol.insert( 6 );
	arbol.insert( 1 );
	arbol.insert( 3 );

	treee.insert( 4 );
	treee.insert( 10 );
	treee.insert( 3 );
	treee.insert( 8 );
	treee.insert( 7 );
	treee.insert( 9 );
	treee.insert( 13 );
	treee.insert( 12 );
	treee.insert( 14 );


	System.out.println( "\n-----------------------------");
	System.out.println( "pre-order traversal:" );
	arbol.preOrderTrav();

	System.out.println( "\n-----------------------------");
	System.out.println( "in-order traversal:" );
	arbol.inOrderTrav();

	System.out.println( "\n-----------------------------");
	System.out.println( "post-order traversal:" );
	arbol.postOrderTrav();

	System.out.println( "\n-----------------------------");

	System.out.println("Number of Leaves: " + arbol.numLeaves());
	System.out.println( "in-order traversal:" );
	arbol.inOrderTrav();
	System.out.println( "\n-----------------------------");

	System.out.println("Checking Height: " + arbol.height());

	System.out.println("Searching for 3: " + arbol.search(3).getValue()); //expect 3
	System.out.println("Searching for 4: " + arbol.search(4).getValue()); //expect 3
	System.out.println("Searching for max value: " + arbol.maxFinder(arbol._root).getValue());



	System.out.println("Removing 3: ");
	System.out.println(arbol.remove(3).getValue());
	arbol.inOrderTrav();
	System.out.println();
	System.out.println("Removing 2: ");
	System.out.println(arbol.remove(2).getValue());
	arbol.inOrderTrav();
	System.out.println("Removing 5: ");
	System.out.println(arbol.remove(5).getValue());
	arbol.inOrderTrav();

	System.out.println("\n=================Testing with New Tree===============\n");
	treee.inOrderTrav();
	System.out.println();
	System.out.println(treee.remove(10).getValue()); 



    }

}//end class
