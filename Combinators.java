import java.util.function.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Combinators {
   
   // returns p1 | p2
   public static Parser alt(Parser p1, Parser p2) {
      Parser parser = new Parser();
      parser.setParser(
         result->{
            if (result.fail)return result; 
            Choice answer = new Choice();
            answer.choice = p1.apply(result);
            if (answer.choice.fail)  {
               answer.choice = p2.apply(result);
            }
            if (answer.choice.fail) return answer.choice;
            answer.unseen = answer.choice.unseen;
            return answer;
      });
      return parser;
   }

   // returns p1 ~ p2
   //public static Parser seq(Parser p1, Parser p2) {...}
   
   // returns p+ 
   //public static Parser rep(Parser p) {...}
   
   // returns p? 
   //public static Parser opt(Parser p) {...}
   
   // returns p = regExp
   public static Parser regEx(String regex) {
	   Pattern pattern = Pattern.compile(regex);
	   Parser parser = new Parser();
	      parser.setParser(
	         (result) -> {
	        	 for(int i=0; i<result.unseen.size(); i++)
	        	 {
	        		 String token = result.unseen.get(i);
	        		 Matcher matcher = pattern.matcher(token);
	        		 if(matcher.find())
	        			 result.unseen.remove(i);
	        		 else
	        		 {
	        			 result.fail = true;
	        			 return result;
	        		 }
	        	 }
	            
	            return result;
	      });
	      return parser;
   }

   // etc.
}