class bst {

	Node root;

	private class Node {

		// These attributes of the Node class will not be sufficient for those
		// attempting the AVL extra credit.
		// You are free to add extra attributes as you see fit, but do not
		// remove attributes given as it will mess with help code.
		String keyword;
		Record record;
		int size;
		Node l;
		Node r;

		private Node(String k) {
			// TODO Instantialize a new Node with keyword k.
			keyword = k;
		}

		private void update(Record r) {
			// TODO Adds the Record r to the linked list of records. You do not
			// have to check if the record is already in the list.
			// HINT: Add the Record r to the front of your linked list.
			if (record == null) {
				record = r;
			} else {
				r.next = record;
				record = r;
			}
		}

	}

	public bst() {
		this.root = null;
	}

	public void insert(String keyword, FileData fd) {
		Record recordToAdd = new Record(fd.id, fd.author, fd.title, null);
		// TODO Write a recursive insertion that adds recordToAdd to the list of
		// records for the node associated
		// with keyword. If there is no node, this code should add the node.
		// remove leading and trailing whitespace
		keyword = keyword.trim();
		insert(keyword, recordToAdd, root);
	}

	private void insert(String keyword, Record recordToAdd, Node root) {
		// the root of the tree is null 
		if (root == null) {
			// create new node and make it the root
			Node node = new Node(keyword);
			node.update(recordToAdd);
			this.root = node;
		} else if (keyword.compareTo(root.keyword) < 0) {
			// make sure there is more nodes to go to
			if (root.l != null) {
				insert(keyword, recordToAdd, root.l);
			} else {
				// otherwise create a new node;
				Node node = new Node(keyword);
				node.update(recordToAdd);
				root.l = node;
			}
		} else if (keyword.compareTo(root.keyword) > 0) {
			if (root.r != null) {
				insert(keyword, recordToAdd, root.r);
			} else {
				Node node = new Node(keyword);
				node.update(recordToAdd);
				root.r = node;
			}
		} else {
			// found the keyword node
			root.update(recordToAdd);
		}
	}

	public boolean contains(String keyword) {
		// TODO Write a recursive function which returns true if a particular
		// keyword exists in the bst
		return contains(keyword, root);
	}
	
	private boolean contains(String keyword, Node root){
		// base case
		if(root == null) return false;
		if(keyword.compareTo(root.keyword) < 0){
			// move down the left side of the tree
			return contains(keyword, root.l);
		} else if (keyword.compareTo(root.keyword) > 0){
			// move down the right side of the tree
			 return contains(keyword, root.r);
		} else {
			// the keyword exists
			return true;
		}
	}

	public Record get_records(String keyword) {
		// TODO Returns the first record for a particular keyword. This record
		// will link to other records
		// If the keyword is not in the bst, it should return null.
		Node current = root;
		while(current != null){
			if(keyword.compareTo(current.keyword) < 0){
				current = current.l;
			} else if (keyword.compareTo(current.keyword) > 0){
				current = current.r;
			} else {
				// found keyword, now return first record
				return current.record;
			}
		}
		return null;
	}

	public void delete(String keyword) {
		// TODO Write a recursive function which removes the Node with keyword
		// from the binary search tree.
		// You may not use lazy deletion and if the keyword is not in the bst,
		// the function should do nothing.
		delete(keyword, root);
	}
	
	private Node delete(String keyword, Node current){
		if (current == null){
			// the keyword does not exist
		} else if(keyword.compareTo(current.keyword) < 0){
			current.l = delete(keyword, current.l);
		} else if(keyword.compareTo(current.keyword) > 0) {
			current.r = delete(keyword, current.r);
		} else {
			if(current.r == null){
				current = current.l;
			} else {
				// the node to delete has a left and right side
				// find the smallest value on the right subtree
				Node replacement = smallest(current.r);
				// move values into node 
				current.keyword = replacement.keyword;
				current.record = replacement.record;
				current.size = replacement.size;
				// delete the smallest node
				current.r = delete(replacement.keyword, current.r);
			}
		}
		return current;
	}
	
	private Node smallest(Node root){
		if(root == null){
			return null;
		}
		if(root.l == null){
			return root;
		}
		return smallest(root.l);
	}

	public void print() {
		print(root);
	}

	private void print(Node t) {
		if (t != null) {
			print(t.l);
			System.out.println(t.keyword);
			Record r = t.record;
			while (r != null) {
				System.out.printf("\t%s\n", r.title);
				r = r.next;
			}
			print(t.r);
		}
	}

}
