public class Combinators {
   
	   public static Parser regEx(String regex) {
		   Parser parser = new Parser();
		      parser.setParser(
		         (result) -> {
	        	 	 Literal answer = new Literal();
	        	 	 String token = "";
	        	 	 if(!result.unseen.hasNext())
	        	 	 {
	        	 		 result.fail = true;
	        	 		 return result;
	        	 	 }
	        	 	 token = result.unseen.next();
	        		 if(!token.matches(regex))
	        		 {
	        			 result.fail = true;
	        			 if(result.unseen.hasPrevious())
	        				 result.unseen.previous();
	        			 return result;
	        		 }
	        		 answer.tokens = result.tokens;
	        		 answer.fail = false;
	        		 answer.token = token;
	    			 answer.unseen = result.unseen;	            
	    			 return answer;
		      });
		      return parser;
	   }
	   
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
            	result.fail = false;
               answer.choice = p2.apply(result);
            }
            if (answer.choice.fail) return answer.choice;
            answer.tokens = answer.choice.tokens;
            answer.fail = answer.choice.fail;
            answer.unseen = answer.choice.unseen;
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
	            answer.first_kid = p1.apply(result);
	            if (answer.first_kid.fail)  
	            {
	            	result.fail = true;
		            return result;	
	            }   
	            answer.tokens = answer.first_kid.tokens;
	            answer.unseen = answer.first_kid.unseen;
	            answer.second_kid = p2.apply(answer.first_kid);
	            	
	            if (answer.second_kid.fail)  
	            {
	            	if(result.unseen.hasPrevious())
	            		result.unseen.previous();
	            	result.fail = true;
		            return result;
		        }
	            answer.tokens = answer.second_kid.tokens;
	            answer.unseen = answer.second_kid.unseen;
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
	            
	            // If another token exist then parse it
	            if(result.unseen.hasNext())
	            {
	            	answer.kid = p.apply(result);
	        		answer.fail = false;
	        		answer.tokens = answer.kid.tokens;
		            answer.unseen = answer.kid.unseen;
		            return answer;
	            }
	            
            	Result blank = new Result();
            	blank.fail = true;
            	answer.kid = blank;
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
		            Result r = p.apply(result);
		            answer.tokens = r.tokens;
            		answer.fail = r.fail;
		            answer.unseen = r.unseen;

            		if(answer.fail) return result;          			
            		answer.kids.add(r);

            		while(answer.unseen.hasNext()) {	
		            	r = p.apply(r);
		            	if(r.fail) return answer;
			            answer.kids.add(r);
			            answer.tokens = r.tokens;
			            answer.fail = r.fail;
			            answer.unseen = r.unseen;
		            }
		            return answer;
		      });
		      return parser;
   }
}