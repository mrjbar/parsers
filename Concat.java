public class Concat extends Result {
	
	protected Result first_kid;
	protected Result second_kid;

	
	public String toString() {
		return String.format("[%s ~ %s]", first_kid, second_kid);
	}

}
