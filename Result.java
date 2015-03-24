import java.util.*;

public class Result {
   
   protected List<String> unseen; // unprocessed tokens
   protected boolean fail; // parser error
   
   //# unseen tokens
   public int pending() { return unseen.size(); }
   
   public Result(String s, String regEx) {
      fail = false;
      String[] a = s.split(regEx);
      unseen = new ArrayList<String>(); 
      for(int i = 0; i < a.length; i++) {
         unseen.add(a[i]);
      }
   }
   public Result(String s) {
      this(s, "\\s+");
   }
   public Result() {
       unseen = new ArrayList<String>(); 
       fail = false;
   }
   
   public String toString() {
      return "[fail = " + fail + "; unseen = " + unseen + "]";
   }
}