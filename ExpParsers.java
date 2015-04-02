public class ExpParsers {
   public static Parser number = Combinators.regEx("^[0-9]+");
   public static Parser name = Combinators.regEx("[a-zA-Z][a-zA-Z0-9]*");
   public static Parser boole = Combinators.regEx("true|false");
   public static Parser operator = Combinators.regEx("&&|\\|\\||\\+|\\*|!");
   public static Parser term = Combinators.alt(Combinators.alt(name,number), boole);
   public static Parser rep = Combinators.rep(term);
   public static Parser product = Combinators.seq(term, Combinators.rep(Combinators.seq(operator, term)));
   public static Parser sum = new Parser();
   static { sum.setParser(Combinators.alt(product, Combinators.seq(term, sum))); }

   public static Parser list2 = new Parser();
   static { list2.setParser(Combinators.seq(term, Combinators.opt(list2))); }
   
   public static Parser opt = Combinators.opt(number);
   
   public static void test(Parser p, String s) {
	   System.out.println("s = " + s);
	   Result tree =  p.apply(new Result(s));
	   System.out.println("tree = " + tree);
	   System.out.println("pending = " + tree.pending() + "\n");   
	   //System.out.println("head of unseen = " + tree.unseen.previous() + "\n");   

	}
   
   public static void testNumberParser() {
	   System.out.println("Test Number Parser");
	   String s = "42";
	   test(ExpParsers.number, s);
	   s = "29234523423423";
	   test(ExpParsers.number, s);
	   s = "00000000000";
	   test(ExpParsers.number, s);
	   s = "-2342342342";
	   test(ExpParsers.number, s);
	   s = "29z";
	   test(ExpParsers.number, s);
	   s = "error";
	   test(ExpParsers.number, s);
   }
   
   public static void testNameParser() {
	   System.out.println("Test Name Parser");
	   String s = "John";
	   test(ExpParsers.name, s);
	   s = "Johnny5";
	   test(ExpParsers.name, s);
	   s = "5Johnny";
	   test(ExpParsers.name, s);
	   s = "55555";
	   test(ExpParsers.name, s);
   }
   
   public static void testBooleParser() {
	   System.out.println("Test Boole Parser");
	   String s = "true";
	   test(ExpParsers.name, s);
	   s = "false";
	   test(ExpParsers.name, s);
	   s = "1";
	   test(ExpParsers.name, s);
	   s = "0";
	   test(ExpParsers.name, s);
   }
   
   public static void testOperatorParser() {
	   System.out.println("Test Operator Parser");
	   String s = "&&";
	   test(ExpParsers.operator, s);
	   s = "||";
	   test(ExpParsers.operator, s);
	   s = "+";
	   test(ExpParsers.operator, s);
	   s = "*";
	   test(ExpParsers.operator, s);
	   s = "!";
	   test(ExpParsers.operator, s);
   }
   
   public static void testTermParser() {
	   System.out.println("Test Term Parser");
	   String s = "Jabari";
	   test(ExpParsers.term, s);
	   s = "100";
	   test(ExpParsers.term, s);
	   s = "true";
	   test(ExpParsers.term, s);
	   s = "false";
	   test(ExpParsers.term, s);
   }
   
   public static void testList2Parser() {
	   System.out.println("Test List 2 Parser");
	   String s = "Jabari true";
	   test(ExpParsers.list2, s);
	   s = "Jabari false";
	   test(ExpParsers.list2, s);
	   s = "Jabari";
	   test(ExpParsers.list2, s);
   }
   
   public static void tesOptParser() {
	   System.out.println("Test List 2 Parser");
	   String s = "5";
	   test(ExpParsers.opt, s);
	   s = "F F F";
	   test(ExpParsers.opt, s);
   }
   
   /*public static void tesRepParser() {
	   System.out.println("Test Repetition Parser");
	   String s = "Jabari Lofton";
	   test(ExpParsers.rep, s);
	   s = "Jabari 5 true";
	   test(ExpParsers.rep, s);
	   s = "5 A true )";
	   test(ExpParsers.rep, s);
	   s = "5 * &";
	   test(ExpParsers.rep, s);
	   s = "+ * &";
	   test(ExpParsers.rep, s);
   }
   
   public static void testProductParser() {
	   // PRODUCT ::= TERM~(OPERATOR~TERM)+
	   System.out.println("Test Product Parser");
	   String s = "5 && 5";
	   test(ExpParsers.product, s);
   }*/
   
   public static void testSumParser() {
	   // SUM ::= PRODUCT | TERM~SUM
	   System.out.println("Test Product Parser");
	   String s = "1 + 2 + 8";
	   //test(ExpParsers.sum, s);
	   s = "1 + 1 + 2";
	   test(ExpParsers.sum, s);
   }
   
   public static void main(String[] args)
   {
	   //testNumberParser();
	   //testNameParser();
	   //testBooleParser();
	   //testOperatorParser();
	   //testTermParser();
	   //testList2Parser();
	   //tesRepParser();
	   //testProductParser();
	   testSumParser();
	   //tesOptParser();
   }
}