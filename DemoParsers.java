public class DemoParsers {
	
   public static Parser number = Combinators.regEx("^[0-9]+");
   public static Parser name = Combinators.regEx("[a-zA-Z][a-zA-Z0-9]*");
   public static Parser boole = Combinators.regEx("true|false");
   public static Parser operator = Combinators.regEx("&&|\\|\\||\\+|\\*|!");
   public static Parser term = Combinators.alt(Combinators.alt(name,number), boole);
   public static Parser product = Combinators.seq(term, Combinators.rep(Combinators.seq(operator, term)));

   public static void test(Parser p, String s) {
	   System.out.println("s = " + s);
	   Result tree =  p.apply(new Result(s));
	   System.out.println("tree = " + tree);
	   System.out.println("pending = " + tree.pending() + "\n");   
	}
   
   public static void testNumberParser() {
	   System.out.println("NUMBER ::= [0-9]+\n");
	   String s = "42";
	   test(DemoParsers.number, s);
	   s = "42z";
	   test(DemoParsers.number, s);
	   s = "forty two";
	   test(DemoParsers.number, s);
   }
   
   public static void testNameParser() {
	   System.out.println("NAME ::= [a-zA-Z][a-zA-Z0-9]*\n");
	   String s = "John";
	   test(DemoParsers.name, s);
	   s = "Johnny5";
	   test(DemoParsers.name, s);
	   s = "5Johnny";
	   test(DemoParsers.name, s);
   }
   
   public static void testBooleParser() {
	   System.out.println("BOOLE ::= true | false\n");
	   String s = "true";
	   test(DemoParsers.boole, s);
	   s = "false";
	   test(DemoParsers.boole, s);
	   s = "boolean";
	   test(DemoParsers.boole, s);
   }
   
   public static void testOperatorParser() {
	   System.out.println("OPERATOR ::= && | \\|\\| | \\+ | \\* | !\n");
	   String s = "&&";
	   test(DemoParsers.operator, s);
	   s = "||";
	   test(DemoParsers.operator, s);
	   s = "+";
	   test(DemoParsers.operator, s);
	   s = "*";
	   test(DemoParsers.operator, s);
	   s = "!";
	   test(DemoParsers.operator, s);
	   s = "% # $";
	   test(DemoParsers.operator, s);
   }
   
   public static void testTermParser() {
	   System.out.println("TERM ::= NAME | NUMBER | BOOLE\n");
	   String s = "Jabari";
	   test(DemoParsers.term, s);
	   s = "100";
	   test(DemoParsers.term, s);
	   s = "true";
	   test(DemoParsers.term, s);
	   s = "false";
	   test(DemoParsers.term, s);
	   s = "!@#$%^&*";
	   test(DemoParsers.term, s);
   }
   
   public static void testProductParser() {
	   System.out.println("PRODUCT ::= TERM~(OPERATOR~TERM)+\n");   
	   String s = "Jack && Jill || Hill + 1 * true ! false";
	   test(DemoParsers.product, s);
	   s = "1 && 2 || 3 + 4 * 5 !";
	   test(DemoParsers.product, s);
	   s = "&& 2 || 3 + 4 * 5 !";
	   test(DemoParsers.product, s);
   }
   
   public static void main(String[] args)
   {
	   testNumberParser();
	   System.out.println("--------------------------------------------------\n");
	   testNameParser();
	   System.out.println("--------------------------------------------------\n");
	   testBooleParser();
	   System.out.println("--------------------------------------------------\n");
	   testOperatorParser();
	   System.out.println("--------------------------------------------------\n");
	   testTermParser();
	   System.out.println("--------------------------------------------------\n");
	   testProductParser();
   }
}