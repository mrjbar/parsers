public class ExpParsers {
	
   public static Parser number = Combinators.regEx("^[0-9]+");
   public static Parser name = Combinators.regEx("[a-zA-Z][a-zA-Z0-9]*");
   public static Parser boole = Combinators.regEx("true|false");
   public static Parser operator = Combinators.regEx("&&|\\|\\||\\+|\\*|!");
   public static Parser term = Combinators.alt(Combinators.alt(name,number), boole);
   public static Parser list1 = Combinators.rep(term);
   public static Parser list2 = new Parser();
   static { list2.setParser(Combinators.seq(term, Combinators.opt(list2))); }
   public static Parser product = Combinators.seq(term, Combinators.rep(Combinators.seq(operator, term)));
   public static Parser sum = new Parser();
   static { sum.setParser(Combinators.alt(product, Combinators.seq(term, sum))); }
   
   public static Parser exp = new Parser();
   static { exp.setParser(Combinators.alt(Combinators.seq(number, Combinators.seq(operator, exp)), number)); }
   
   public static Parser seq = Combinators.seq(number,number);

   
   public static void test(Parser p, String s) {
	   System.out.println("s = " + s);
	   Result tree =  p.apply(new Result(s));
	   System.out.println("tree = " + tree);
	   System.out.println("pending = " + tree.pending() + "\n");   
	   //System.out.println("head of unseen = " + tree.unseen.previous() + "\n");   

	}
 
   public static void testExpParser() {
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
   
   public static void testNumberParser() {
	   System.out.println("NUMBER ::= [1-9]+\n");
	   String s = "42";
	   test(ExpParsers.number, s);
	   s = "42z";
	   test(ExpParsers.number, s);
	   s = "forty two";
	   test(ExpParsers.number, s);
   }
   
   public static void testNameParser() {
	   System.out.println("NAME ::= [a-zA-Z][a-zA-Z0-9]*\n");
	   String s = "John";
	   test(ExpParsers.name, s);
	   s = "Johnny5";
	   test(ExpParsers.name, s);
	   s = "5Johnny";
	   test(ExpParsers.name, s);
   }
   
   public static void testBooleParser() {
	   System.out.println("BOOLE ::= true | false\n");
	   String s = "true";
	   test(ExpParsers.boole, s);
	   s = "false";
	   test(ExpParsers.boole, s);
	   s = "boolean";
	   test(ExpParsers.boole, s);
   }
   
   public static void testOperatorParser() {
	   System.out.println("OPERATOR ::= && | \\|\\| | \\+ | \\* | !\n");
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
	   s = "% # $";
	   test(ExpParsers.operator, s);
   }
   
   public static void testTermParser() {
	   System.out.println("TERM ::= NAME | NUMBER | BOOLE\n");
	   String s = "Jabari";
	   test(ExpParsers.term, s);
	   s = "100";
	   test(ExpParsers.term, s);
	   s = "true";
	   test(ExpParsers.term, s);
	   s = "false";
	   test(ExpParsers.term, s);
	   s = "!@#$%^&*";
	   test(ExpParsers.term, s);
   }
   
   public static void testList1Parser() {
	   System.out.println("LIST1 ::= TERM+\n");
	   String s = "Apple Banana Pear Avocado";
	   test(ExpParsers.list1, s);
	   s = "1 10 100 1000 100000";
	   test(ExpParsers.list1, s);
	   s = "true false";
	   test(ExpParsers.list1, s);
	   s = "Apple 100 true";
	   test(ExpParsers.list1, s);
	   s = "Apple 100 !@#$%^";
	   test(ExpParsers.list1, s);
   }
   
   public static void testList2Parser() {
	   System.out.println("LIST2 ::= TERM~(LIST2)?\n");
	   String s = "Apple";
	   test(ExpParsers.list2, s);
	   s = "Apple 100";
	   test(ExpParsers.list2, s);
	   s = "Apple 100 true";
	   test(ExpParsers.list2, s);
	   s = "Apple 100 @# $#$ %#$%$";
	   test(ExpParsers.list2, s);
	   s = "5 &";
	   test(ExpParsers.seq, s);
   }
   
   public static void testProductParser() {
	   System.out.println("PRODUCT ::= TERM~(OPERATOR~TERM)+\n");   
	   String s = "Jack && Jill || Hill + 1 * true ! false";
	   test(ExpParsers.product, s);
	   s = "1 && 2 || 3 + 4 * 5 !";
	   test(ExpParsers.product, s);
	   s = "Jack &&";
	   test(ExpParsers.product, s);
   }
   
   public static void testSumParser() {
	   System.out.println("SUM ::= PRODUCT | TERM~SUM\n");
	   String s = "1 + 2 + 8";
	   test(ExpParsers.sum, s);
	   s = "Jack and Jill";
	   test(ExpParsers.sum, s);
	   s = "Term Term + TERM";
	   test(ExpParsers.sum, s);
   }
   
   public static void main(String[] args)
   {
	   testExpParser();
	   System.out.println("--------------------------------------------------\n");
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
	   testList1Parser();
	   System.out.println("--------------------------------------------------\n");
	   testList2Parser();
	   System.out.println("--------------------------------------------------\n");
	   testProductParser();
	   System.out.println("--------------------------------------------------\n");
	   testSumParser();
   }
}