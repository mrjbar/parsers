import java.util.ArrayList;

public class Iteration extends Result {
	
	ArrayList<Result> kids = new ArrayList<Result>();
	
	public String toString() {
		String result = "";
		for(Result r : kids)
			result = result + String.format("[+ %s]", r);
		return result;
	}
}
