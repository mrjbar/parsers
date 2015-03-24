public class ExpParsers {
   public static Parser number = Combinators.regEx("[0-9]+");
   public static Parser operator = Combinators.regEx("\\+|\\*");
   public static Parser exp = new Parser();
   static {
      exp.setParser(Combinators.alt(Combinators.seq(number, Combinators.seq(operator, exp)), number));
   }
   
   public static void test(Parser p, String s) {
	   System.out.println("s = " + s);
	   Result tree =  p.apply(new Result(s));
	   System.out.println("tree = " + tree);
	   System.out.println("pending = " + tree.pending());
	}
   
   public static void testExpParsers() {
	   String s = "42";
	   test(ExpParsers.number, s);
	   s = "29z";
	   test(ExpParsers.number, s);
	   s = "*";
	   test(ExpParsers.operator, s);
	   s = "-";
	   test(ExpParsers.operator, s);
	   s = "42 + 91 * 13 + 2";
	   test(ExpParsers.exp, s);
	   s = "123";
	   test(ExpParsers.exp, s);
	   s = "15 * 6 - 10";
	   test(ExpParsers.exp, s);
	}
}