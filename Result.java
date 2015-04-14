import java.util.*;

public class Result {
   
   protected LinkedList<String> tokens; // unprocessed tokens
   protected boolean fail; // parser error
   protected ListIterator<String> unseen;
   
   //# unseen tokens
   public int pending() {
	   return tokens.size() - unseen.nextIndex(); 
	   }
   
   public Result(String s, String regEx) {
      fail = false;
      String[] a = s.split(regEx);
      tokens = new LinkedList<String>(); 
      for(int i = 0; i < a.length; i++) {
         tokens.add(a[i]);
      }
      unseen = tokens.listIterator();
   }
   public Result(String s) {
      this(s, "\\s+");
   }
   public Result() {
       tokens = new LinkedList<String>(); 
       unseen = tokens.listIterator();
       fail = false;
   }
   
   public String toString() {
	   
      return String.format("[fail = %s; unseen = %s]", fail, tokens.subList(unseen.nextIndex(), tokens.size()));
   }
}