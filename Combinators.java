import java.util.function.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Combinators {
   
   // returns p1 | p2
   public static Parser alt(Parser p1, Parser p2) {
      Parser parser = new Parser();
      parser.setParser(
         (result) -> {
            if (result.fail) 
            	return result; 
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
   
   public static Parser regEx(String regex) {
	   Parser parser = new Parser();
	      parser.setParser(
	         (result) -> {
        	 	 Literal answer = new Literal();
        		 String token = result.unseen.get(0);
        		 if(!token.matches(regex))
        		 {
        			 result.fail = true;
        			 return result;
        			 
        		 }
        		 answer.token = token;
        		 result.unseen.remove(0);
    			 answer.unseen = result.unseen;	            
            return answer;
	      });
	      return parser;
   }

   // returns p1 ~ p2
   public static Parser seq(Parser p1, Parser p2) {
	   Parser parser = new Parser();
	      parser.setParser(
	         (result) -> {
	            if (result.fail) 
	            	return result; 
	            
	            Concat answer = new Concat();
	            answer.kids[0] = p1.apply(result);
	            if (answer.kids[0].fail)  
	            {
	            	result.fail = true;
		            return result;	
	            }   
	            
	            answer.unseen = answer.kids[0].unseen;
	            answer.kids[1] = p2.apply(answer.kids[0]);
	            	
	            if (answer.kids[1].fail)  
	            {
	            	result.fail = true;
		            return result;
		        }
	            
	            answer.unseen = answer.kids[1].unseen;
	            return answer; 
	      });
	      return parser;
   }
   
   // returns p? 
   public static Parser opt(Parser p) {
	   Parser parser = new Parser();
	      parser.setParser(
	         (result) -> {
	            if (result.fail) 
	            	return result; 
	            
	            Option answer = new Option();
	            if(result.unseen.isEmpty())
	            {
	            	Result l = new Result();
	            	l.fail = true;
	            	answer.kid = l;
	            	return answer;
	            }
	            answer.kid = p.apply(result);
	            if(answer.kid.fail)
	            	answer.kid.unseen.remove(0);
	            answer.unseen = answer.kid.unseen;
	            return answer; 
	      });
	      return parser;
   }
   
   // returns p+ 
   public static Parser rep(Parser p) {
	   Parser parser = new Parser();
	   parser.setParser(
		         (result) -> {
		            if (result.fail) 
		            	return result; 
		            
		            Iteration answer = new Iteration();
		            Result r;
		            r = p.apply(result);
            		answer.fail = r.fail;
		            answer.unseen = r.unseen;

            		if(answer.fail) return result;          			
            		answer.kids.add(r);

            		while(!answer.unseen.isEmpty()) {	
		            	r = p.apply(r);
		            	if(r.fail) return answer;
			            answer.kids.add(r);
			            answer.fail = r.fail;
			            answer.unseen = r.unseen;
		            }
		            return answer;
		      });
		      return parser;
   }
}