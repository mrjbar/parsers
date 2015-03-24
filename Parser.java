import java.util.function.UnaryOperator;

public class Parser implements UnaryOperator {
	
	private UnaryOperator<Result> parser;
	
	public Result apply(Result r)
	{
		return parser.apply(r);
	}
	
	public void setParser(UnaryOperator<Result> r)
	{
		parser = r;
	}

	@Override
	public Object apply(Object t) {
		// TODO Auto-generated method stub
		return null;
	}
}
