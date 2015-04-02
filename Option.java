
public class Option extends Result {
	protected Result kid;
	
	public String toString()
	{
		if(kid.fail)
			return String.format("[? ]");
		return String.format("[? %s]", kid);
		
	}
}
