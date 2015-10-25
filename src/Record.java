public class Record{
    int id;
    String title;
    String author;
    Record next;

    Record(int i, String t, String a, Record r){
        this.id = i;
        this.title = t;
        this.author = a;
        this.next = r;
    }
    
    public void print(){
    	System.out.println(this.title);
    	System.out.println(this.author);
    }
    

}

