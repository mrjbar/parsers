
public class Concat extends Result {
	
	protected Result[] kids = new Result[2];
	
	public String toString()
	{
		return String.format("[%s ~ %s]", kids[0], kids[1]);
	}

}
