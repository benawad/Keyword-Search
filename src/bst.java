class bst{
    
    Node root;

    private class Node{
    	
    	// These attributes of the Node class will not be sufficient for those attempting the AVL extra credit.
    	// You are free to add extra attributes as you see fit, but do not remove attributes given as it will mess with help code.
        String keyword;
        Record record;
        int size;
        Node l;
        Node r;

        private Node(String k){
        	// TODO Instantialize a new Node with keyword k.
        }

        private void update(Record r){
        	//TODO Adds the Record r to the linked list of records. You do not have to check if the record is already in the list.
        	//HINT: Add the Record r to the front of your linked list.

        }

       
    }

    public bst(){
        this.root = null;
    }
      
    public void insert(String keyword, FileData fd){
        Record recordToAdd = new Record(fd.id, fd.author, fd.title, null);
        //TODO Write a recursive insertion that adds recordToAdd to the list of records for the node associated
        //with keyword. If there is no node, this code should add the node.
    }
    
    public boolean contains(String keyword){
    	//TODO Write a recursive function which returns true if a particular keyword exists in the bst
    	return false;
    }

    public Record get_records(String keyword){
        //TODO Returns the first record for a particular keyword. This record will link to other records
    	//If the keyword is not in the bst, it should return null.
    	return null;
    }

    public void delete(String keyword){
    	//TODO Write a recursive function which removes the Node with keyword from the binary search tree.
    	//You may not use lazy deletion and if the keyword is not in the bst, the function should do nothing.
    }

    public void print(){
        print(root);
    }

    private void print(Node t){
        if (t!=null){
            print(t.l);
            System.out.println(t.keyword);
            Record r = t.record;
            while(r != null){
                System.out.printf("\t%s\n",r.title);
                r = r.next;
            }
            print(t.r);
        } 
    }
    

}
