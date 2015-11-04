import java.util.ArrayList;

class avl {

	Node root;

	private class Node {

		// These attributes of the Node class will not be sufficient for those
		// attempting the AVL extra credit.
		// You are free to add extra attributes as you see fit, but do not
		// remove attributes given as it will mess with help code.
		String keyword;
		Record record;
		int size;
		int height = 0;
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

	public avl() {
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
		balancePath(keyword);
	}

	private void insert(String keyword, Record recordToAdd, Node root) {
		// the root of the tree is null
		if (root == null) {
			// create new node and make it the root
			Node node = new Node(keyword);
			node.update(recordToAdd);
			this.root = node;
			balancePath(keyword);
		} else if (keyword.compareTo(root.keyword) < 0) {
			// make sure there is more nodes to go to
			if (root.l != null) {
				insert(keyword, recordToAdd, root.l);
			} else {
				// otherwise create a new node;
				Node node = new Node(keyword);
				node.update(recordToAdd);
				root.l = node;
				balancePath(keyword);
			}
		} else if (keyword.compareTo(root.keyword) > 0) {
			if (root.r != null) {
				insert(keyword, recordToAdd, root.r);
			} else {
				// create new node
				Node node = new Node(keyword);
				node.update(recordToAdd);
				root.r = node;
				balancePath(keyword);
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

	private boolean contains(String keyword, Node root) {
		// base case
		if (root == null)
			return false;
		if (keyword.compareTo(root.keyword) < 0) {
			// move down the l side of the tree
			return contains(keyword, root.l);
		} else if (keyword.compareTo(root.keyword) > 0) {
			// move down the r side of the tree
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
		while (current != null) {
			if (keyword.compareTo(current.keyword) < 0) {
				current = current.l;
			} else if (keyword.compareTo(current.keyword) > 0) {
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

	private Node delete(String keyword, Node current) {
		if (current == null) {
			// the keyword does not exist
		} else if (keyword.compareTo(current.keyword) < 0) {
			current.l = delete(keyword, current.l);
		} else if (keyword.compareTo(current.keyword) > 0) {
			current.r = delete(keyword, current.r);
		} else {
			if (current.r == null) {
				current = current.l;
				balancePath(keyword);
			} else {
				// the node to delete has a l and r side
				// find the smallest value on the r subtree
				Node replacement = smallest(current.r);
				// move values into node
				current.keyword = replacement.keyword;
				current.record = replacement.record;
				current.size = replacement.size;
				// delete the smallest node
				current.r = delete(replacement.keyword, current.r);
				balancePath(keyword);
			}
		}
		return current;
	}

	private Node smallest(Node root) {
		if (root == null) {
			return null;
		}
		if (root.l == null) {
			return root;
		}
		return smallest(root.l);
	}

	public void print() {
		print(root);
	}

	private void updateHeight(Node node) {
		if (node.l == null && node.r == null) {
			node.height = 0;
		} else if (node.l == null) {
			node.height = 1 + node.r.height;
		} else if (node.r == null) {
			node.height = 1 + node.l.height;
		} else {
			node.height = 1 + Math.max(node.r.height, node.l.height);
		}
	}

	private void balancePath(String e) {
		ArrayList<Node> path = path(e);
		for (int i = path.size() - 1; i >= 0; i--) {
			Node A = path.get(i);
			updateHeight(A);
			Node parentOfA = (A == root) ? null : path.get(i - 1);

			switch (balanceFactor(A)) {
			case -2:
				if (balanceFactor(A.l) <= 0) {
					balanceLL(A, parentOfA); // Perform LL rotation
				} else {
					balanceLR(A, parentOfA); // Perform LR rotation
				}
				break;
			case +2:
				if (balanceFactor(A.r) >= 0) {
					balanceRR(A, parentOfA); // Perform RR rotation
				} else {
					balanceRL(A, parentOfA); // Perform RL rotation
				}
			}
		}
	}

	private int balanceFactor(Node node) {
		if (node.r == null) {
			return -node.height;
		} else if (node.l == null) {
			return +node.height;
		} else {
			return node.r.height - node.l.height;
		}
	}

	private void balanceLL(Node A, Node parentOfA) {
		Node B = A.l;

		if (A == root) {
			root = B;
		} else {
			if (parentOfA.l == A) {
				parentOfA.l = B;
			} else {
				parentOfA.r = B;
			}
		}

		A.l = B.r;
		B.r = A;
		updateHeight(A);
		updateHeight(B);
	}

	private void balanceLR(Node A, Node parentOfA) {
		Node B = A.l; // A is l-heavy
		Node C = B.r; // B is r-heavy

		if (A == root) {
			root = C;
		} else {
			if (parentOfA.l == A) {
				parentOfA.l = C;
			} else {
				parentOfA.r = C;
			}
		}

		A.l = C.r; // Make T3 the l subtree of A
		B.r = C.l; // Make T2 the r subtree of B
		C.l = B;
		C.r = A;

		// Adjust heights
		updateHeight(A);
		updateHeight(B);
		updateHeight(C);
	}

	private void balanceRR(Node A, Node parentOfA) {
		Node B = A.r; // A is r-heavy and B is r-heavy

		if (A == root) {
			root = B;
		} else {
			if (parentOfA.l == A) {
				parentOfA.l = B;
			} else {
				parentOfA.r = B;
			}
		}

		A.r = B.l; // Make T2 the r subtree of A
		B.l = A;
		updateHeight(A);
		updateHeight(B);
	}

	private void balanceRL(Node A, Node parentOfA) {
		Node B = A.r; // A is r-heavy
		Node C = B.l; // B is l-heavy

		if (A == root) {
			root = C;
		} else {
			if (parentOfA.l == A) {
				parentOfA.l = C;
			} else {
				parentOfA.r = C;
			}
		}

		A.r = C.l; // Make T2 the r subtree of A
		B.l = C.r; // Make T3 the l subtree of B
		C.l = A;
		C.r = B;

		// Adjust heights
		updateHeight(A);
		updateHeight(B);
		updateHeight(C);
	}

	public ArrayList<Node> path(String keyword) {
		ArrayList<Node> list = new ArrayList<>();
		Node current = root;

		while (current != null) {
			list.add(current);
			if (keyword.compareTo(current.keyword) < 0) {
				current = current.l;
			} else if (keyword.compareTo(current.keyword) > 0) {
				current = current.r;
			} else {
				break;
			}
		}
		return list;
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
