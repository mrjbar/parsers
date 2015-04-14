public class Choice extends Result {
	
	protected Result choice;
	
	   public String toString() {
		   return String.format("[| %s]", choice);
	   }
}
