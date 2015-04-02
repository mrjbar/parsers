import java.util.function.UnaryOperator;

public class Parser implements UnaryOperator<Result> {
	
	private UnaryOperator<Result> parser;
	
	public Result apply(Result r) {
		return parser.apply(r);
	}
	
	public void setParser(UnaryOperator<Result> r) {
		parser = r;
	}
}
