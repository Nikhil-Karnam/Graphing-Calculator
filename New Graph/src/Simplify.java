import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeSet;

public class Simplify {
	
	public static void main(String[] args)
	{
		
	}
	
	public static String simplify(String f)
	{
		return null;
	}
	
	
	//input function, adds terms with like powers, returns simplified function
	public static String addTerms(String f)
	{
		String[] terms = splitf(f);
		Map<String, ArrayList<String>> map = new HashMap<>();
		
		for(String a : terms)
		{
			if(!map.containsKey(specialPower(a)))
				map.put(specialPower(a), new ArrayList<String>());
			
			map.get(specialPower(a)).add(coefficient(a, 1));
		}
		
		String result = "";
				
		for(String a : map.keySet())
		{
			String sum = "0";
			
			for(String b : map.get(a)) 
				sum = Fraction.add(sum, b);
			
			if(Fraction.isFraction(a))
				a = Fraction.envelop(a);
						
			if(!sum.equals("0"))
			{
				if(a.equals("0"))
					result += sum + "+";
				
				else
					result += formatCoefficient(sum) + "x^" + a + "+";
			}
		}
				
		result = result.replaceAll("\\^1", "");
		result = result.replaceAll("\\(-", "\\-\\(");
		result = result.replaceAll("\\+-", "\\-");
		
		if(result.equals(""))
			return "0";
		
		return rearrange(result.substring(0,result.length()-1));
	}
	
	
	//cross multiplies 2 terms
	public static String multiplyTerms(String a, String b) 
	{
		if(!yVal.operatorTest(a, "/"))
			a += "/1";
		if(!yVal.operatorTest(b, "/"))
			b += "/1";
		
		String an = a.substring(0, yVal.operatorLocation(a, "/"));
		String ad = a.substring(yVal.operatorLocation(a, "/") + 1);
		String bn = b.substring(0, yVal.operatorLocation(b, "/"));
		String bd = b.substring(yVal.operatorLocation(b, "/") + 1);
		
		if(multiplyBasic(ad, bd).equals("1"))
			return multiplyBasic(an, bn);
		
		try{return divideBasic(multiplyBasic(an, bn), multiplyBasic(ad, bd));} catch(Exception e) {return multiplyBasic(an, bn) + "/" + multiplyBasic(ad, bd);}
	}
	
	
	//multiplies 2 terms
	public static String multiplyBasic(String a, String b) 
	{
		//defines powers
		String p1 = specialPower(a);
		String p2 = specialPower(b);
		String power = "";
		try{power = Fraction.add(p1, p2);} catch(Exception e){power = addTerms(p1 + "+" + p2);}
		
		
		//defines coefficients
		String c1 = coefficient(a, 0);
		String c2 = coefficient(b, 0);
		String c = formatCoefficient(Fraction.multiply(c1, c2));
	
		
		//actually multiplies case by case
		
		//for constants
		if(isConstant(a) && !isConstant(b)) 
		{
			if(isSpecialConstant(a)) 
				return c + Fraction.envelop(removeCoefficient(a, 0)) + removeCoefficient(b, 0);
			
			return formatCoefficient(Fraction.multiply(a, c2)) + removeCoefficient(b, 0);
		}
		
		else if(!isConstant(a) && isConstant(b)) 
		{
			if(isSpecialConstant(b))
				return c + Fraction.envelop(removeCoefficient(b, 0)) + removeCoefficient(a, 0);
			
			return formatCoefficient(Fraction.multiply(b, c1)) + removeCoefficient(a, 0);
		}
		
		if(isConstant(a) && isConstant(b)) 
		{
			if(isSpecialConstant(a) && !isSpecialConstant(b))
				return Fraction.multiply(b, c1) + removeCoefficient(a, 0);
			
			if(!isSpecialConstant(a) && isSpecialConstant(b)) 
				return Fraction.multiply(a, c2) + removeCoefficient(b, 0);
			
			if(isSpecialConstant(a) && isSpecialConstant(b))
				return c + Fraction.envelop(removeCoefficient(a, 0)) + Fraction.envelop(removeCoefficient(b, 0));
								
			return Fraction.multiply(a, b);
		}
		
		
		String result = "";
		a = removeCoefficient(removePower(a), 0);
		b = removeCoefficient(removePower(b), 0);
		
		//for like terms
		if(a.equals(b)) 
		{
			if(splitf(power).length > 1)
				power = Fraction.envelop(power);
			
			if(power.equals("0"))
				result = Fraction.multiply(c1, c2);
			else
				result = c + a + "^" + power;
			
			return balanceBrackets(result.replaceAll("\\^1", ""));
		}
		
		//everything else
		return c + Fraction.envelop(a) + Fraction.envelop(b);
	}
	
	
	//divides 2 terms
	public static String divideBasic(String a, String b) 
	{
		//defines powers
		String p1 = specialPower(a);
		String p2 = specialPower(b);
		String power = "";
		try{power = Fraction.subtract(p1, p2);} catch(Exception e){power = addTerms(p1 + "-" + p2);}
		
		
		//defines coefficients
		String c1 = coefficient(a, 1);
		String c2 = coefficient(b, 1);
		
		String c = Fraction.divide(c1, c2);
		
		if(Fraction.isFraction(c)) 
		{
			c1 =  c.split("\\/")[0];
			c2 = c.split("\\/")[1];
		}
		else 
		{
			c1 = c;
			c2 = "1";
		}
		
		//System.out.println(c1 + "  " + c2);
		
		c = formatCoefficient(c);
	
		
		//actually divides case by case
		
		//for constants
		if(isConstant(a) && !isConstant(b)) 
		{
			if(isSpecialConstant(a)) 
				return formatCoefficient(c1) + removeCoefficient(a, 1) + "/" + formatCoefficient(c2) + removeCoefficient(b, 1);
			
			return c1 + "/" + formatCoefficient(c2) + removeCoefficient(b, 1);
		}
		
		else if(!isConstant(a) && isConstant(b)) 
		{
			if(isSpecialConstant(b)) 
				return formatCoefficient(c1) + removeCoefficient(a, 1) + "/" + formatCoefficient(c2) + removeCoefficient(b, 1);
						
			if(c2.equals("1"))
				return formatCoefficient(c1) + removeCoefficient(a, 1);
			
			return formatCoefficient(c1) + removeCoefficient(a, 1) + "/" + c2;
		}
		
		if(isConstant(a) && isConstant(b)) 
		{
			if(isSpecialConstant(a) && !isSpecialConstant(b)) 
			{
				if(c2.equals("1"))
					return formatCoefficient(c1) + removeCoefficient(a, 1);
				
				return 	formatCoefficient(c1) + removeCoefficient(a, 1) + "/" + c2;
			}
			
			if(!isSpecialConstant(a) && isSpecialConstant(b)) 
				return c1 + "/" + formatCoefficient(c2) + removeCoefficient(b, 1);
			
			if(isSpecialConstant(a) && isSpecialConstant(b))
				return formatCoefficient(c1) + removeCoefficient(a, 1) + "/" + formatCoefficient(c2) + removeCoefficient(b, 1);
								
			return Fraction.divide(a, b);
		}
		
		
		String result = "";
		a = removeCoefficient(removePower(a), 1);
		b = removeCoefficient(removePower(b), 1);
		
		//System.out.println(a + "  " + b);
		
		//for like terms
		if(a.equals(b)) 
		{
			if(splitf(power).length > 1)
				power = Fraction.envelop(power);
			
			if(power.equals("0"))
				result = Fraction.divide(c1, c2);
			else
				result = c + a + "^" + power;
			
			return balanceBrackets(result.replaceAll("\\^1", ""));
		}
		
		//everything else
		return formatCoefficient(c1) + envelopMultipleTerms(a) + "/" + formatCoefficient(c2) + envelopMultipleTerms(b);
	}
	
	
	//foils or multiplies out a factored equation
	public static String foil(String f) 
	{	
		f = balanceBrackets(f);
		if(!f.contains(")("))
				return f;
		
		f = f.substring(1, f.length()-1);

		
		if(f.split("\\)\\(").length > 2)
		{
			String[] terms = f.split("\\)\\(");
			String f1 = Fraction.envelop(terms[0]) + Fraction.envelop(terms[1]);
			
			f = "";
			for(int i = 2; i < terms.length; i++)
				f += terms[i];

			return foil(Fraction.envelop(foil(f1)) + Fraction.envelop(f));
		}
		else 
		{
			String[] a = splitf(f.split("\\)\\(")[0]);
			String[] b = splitf(f.split("\\)\\(")[1]);
			
			String[] result = new String[a.length * b.length];
			int count = 0;
						
			for(String i : a)
				for(String j : b)
				{
					result[count] = multiplyTerms(i, j);
					count++;
				}

			return rearrange(addTerms(unsplit(result)));
		}
	}
			
	
	//factors an equation
	public static String factor(String f) 
	{
		f = foil(f);
		
		ArrayList<String> factors = new ArrayList<String>();
		
		f = rearrange(addTerms(f));
						
		if(!isConstant(splitf(f)[splitf(f).length-1]))
			f += "+0";
				
		String[] terms = (splitf(f));
		
		//factors out a common coefficient
		
		ArrayList<String> coefficients = new ArrayList<>();
		for(String a : terms) 
			coefficients.add(coefficient(a, 1));
				
		ArrayList<Integer> n = new ArrayList<>();
		ArrayList<Integer> d = new ArrayList<>();
		for(String a : coefficients)
		{
			a = a.replaceAll("\\-\\(", "\\(\\-");
			a = balanceBrackets(a);
			
			if(!Fraction.isFraction(a))
				a+="/1";
			
			n.add(Integer.parseInt(a.split("/")[0]));
			d.add(Integer.parseInt(a.split("/")[1]));
		}
		
		String multiplier = Fraction.divide(Fraction.bigGcf(n)+"", Fraction.bigLcm(d)+"");
		
		
		for(int i = 0; i < terms.length; i++)
			terms[i] = multiplyTerms(terms[i], Fraction.divide("1", multiplier));
						
		
		//generates first queue of coefficients
		LinkedList<String> function = new LinkedList<String>();
							
		for(int i = 0; i < terms.length - 1; i++)
		{
			function.add(coefficient(terms[i], 1));
							
			for(int j = 1; j < Integer.parseInt(Fraction.subtract(specialPower(terms[i]) , specialPower(terms[i+1]))); j++)
				function.add("0");
		}
		function.add(coefficient(terms[terms.length-1], 1));
				
		
		//actually factors
		while(function.size() != 1) 
		{
			Boolean canFactor = false;
			
			//generates TreeSet of starting values
			
			ArrayList<Integer> q = Fraction.getFactorsWithNegative(Integer.parseInt(function.peekLast()));
			ArrayList<Integer> p = Fraction.getFactorsWithNegative(Integer.parseInt(function.peekFirst()));
			TreeSet<String> values = new TreeSet<String>();

			for(int i : p)
				for(int j : q) 
					values.add(Fraction.divide(Integer.toString(j), Integer.toString(i)));
			

			//synthetic division
			for(String startingValue: values) 
			{
				LinkedList<String> test = syntheticDivision(startingValue, function);
				String remainder = test.pollLast();

				if(remainder.equals("0"))
				{
					canFactor = true;
					function = (LinkedList<String>) test.clone();		
					factors.add(startingValue);
					break;
				}
			}
			
			if(!canFactor)
				break;
		}
		
		
		//puts together function from result of synthetic division
		String result = "";
		
		for(String a : factors)
		{
			String c = "";
			
			a = Fraction.multiply("-1", a);
			
			if(Fraction.isFraction(a)) 
			{
				c = a.split("/")[1];
				a = a.split("/")[0];
			}
						
			if(!a.startsWith("-"))
				a = "+" + a;
						
			if(!a.equals("+0"))
				result += Fraction.envelop(c + "x" + a);
			else
				result += "(x)";
		}
		
		if(function.size() > 1) 
		{
			for(int i = 0; i < function.size()-2; i++) 
				if(!function.get(i).equals("0"))
					function.set(i, function.get(i) + "x^" + (function.size()-i-1));
									
			if(!function.get(function.size()-2).equals("0"))
				function.set(function.size()-2, function.get(function.size()-2) + "x");
			
			while(function.contains("0"))
				function.remove("0");
			
			
			String[] bigFactor = new String[function.size()];
			
			for(int i = 0; i < function.size(); i++)
				bigFactor[i] = function.get(i);
			
			result += Fraction.envelop(rearrange(addTerms(unsplit(bigFactor))));
		}		
		
		if(!multiplier.equals("1"))
			result = Fraction.envelop(multiplier) + Fraction.envelop(result);
		
		
		return balanceBrackets(result);
	}
	
	
	//returns the synthetic division of starting value and queue
	public static LinkedList<String> syntheticDivision(String startingValue, LinkedList<String> input) 
	{		
		Queue<String> queue = new LinkedList<String>();
		
		for(String i : input)
			queue.add(i);
		
		Stack<String> result = new Stack<String>();
		result.add(queue.poll());
				
		while(!queue.isEmpty())
			result.add(Fraction.add(Fraction.multiply(result.peek(), startingValue), queue.poll()));
		
		//converts result stack to queue
		LinkedList<String> last = new LinkedList<String>();
		for(String a : result)
			last.add(a);
		
		return last;
	}
	
	
	//cancels like factors in an equation
	//TODO
	public static String cancel(String f) 
	{		
		String a = balanceBrackets(f.substring(0, yVal.operatorLocation(f, "/")));
		String b = balanceBrackets(f.substring(yVal.operatorLocation(f, "/")+1));
		
		a = factor(a);
		b = factor(b);
		
		if(a.startsWith("(") && a.endsWith(")"))
			a = a.substring(1, a.length()-1);
		if(b.startsWith("(") && b.endsWith(")"))
			b = b.substring(1, b.length()-1);
		
		String[] n = a.split("\\)\\(");
		String[] d = b.split("\\)\\(");
		
		for(int i = 0; i < n.length; i++)
		{
			for(int j = 0; j < d.length; j++) 
			{
				if(n[i].equals(d[j])) 
				{
					n[i] = "1";
					d[j] = "1";
					break;
				}
			}
		}
		
		String numerator = "";
		for(String i : n)
				numerator += Fraction.envelop(i);
		
		String denomenator = "";
		for(String i : d)
				denomenator += Fraction.envelop(i);
		
		numerator = foil(numerator);
		denomenator = foil(denomenator);

		if(numerator.contains("+") || (numerator.substring(1).contains("-")))
			numerator = Fraction.envelop(numerator);
		
		if(denomenator.contains("+") || (denomenator.substring(1).contains("-")))
			denomenator = Fraction.envelop(denomenator);
		
		return numerator + "/" + denomenator;
	}
	
	
	//rearranges terms in a equation based on powers
	
	public static String rearrange(String f) 
	{
		String[] original = splitf(f);
		ArrayList<String> terms = new ArrayList<>();
		ArrayList<String> list = new ArrayList<>();

		for(String a : original) 
			terms.add(a);
		
		
		while(!terms.isEmpty()) 
		{
			String max = terms.get(0);
			
			for(String a : terms)
				if(Fraction.isGreater(specialPower(a), specialPower(max)))
					max = a;
			
			list.add(max);
			terms.remove(max);
		}
		
		String result = "";
		for(String a : list)
			result += a + "+";
		
		result = result.replaceAll("\\+-", "\\-");
		
		return result.substring(0, result.length()-1);
	}
	
	
	//formats a number so it can be concatenated to an equation as a coefficient
	public static String formatCoefficient(String c) 
	{
		if(Fraction.isFraction(c))
			c = Fraction.envelop(c);
		if(c.equals("1"))
			c = "";
		if(c.equals("-1"))
			c = "-";
		
		return c;
	}
	
	//if a String contains multiple terms, surround it with brackets
	public static String envelopMultipleTerms(String a) 
	{
		if(splitf(a).length > 1)
			return Fraction.envelop(a);
		
		return a;
	}
	
	//returns true if you can factor the equation
	public boolean canFactor(String f)
	{
		if(!f.contains("("))
			return true;
		
		return false;
	}

	//returns true if the constant is a special term like log or trig
	public static boolean isSpecialConstant(String a)
	{
		boolean check1 = false;
		boolean check2 = true;
		
		for(int i = 0; i < a.length(); i++) 
		{
			if(Character.isAlphabetic(a.charAt(i)))
				check1 = true;
			if(a.substring(i, i+1).equals("x"))
				check2 = false;
		}
			
		if(check1 && check2)
			return true;
		
		return false;
	}	
	
	
	/*returns coefficient of a term. a is a switch variable for different coefficient methods.
	 * a = 0 returns 1 for constants
	 * a = 1 returns the constant for constants
	*/
	public static String coefficient(String f, int a) 
	{	
		f = balanceBrackets(f);

		if(splitf(f).length > 1)
			return "1";
		
		f = multiply(f);
		
		if(!f.contains("*")) 
		{
			if(f.startsWith("-") && Character.isAlphabetic(f.substring(1).charAt(0)))
				return "-1";
			
			if(f.startsWith("x") || isSpecialConstant(f))
				return "1";
			
			if(a == 0)
				return "1";
			
			return f;
		}
		
		return balanceBrackets(f.substring(0, f.indexOf("*")));
	}
	
	
	/*returns term without coefficient. a is a switch variable for different remove coefficient methods.
	 * a = 0 returns the constant for constants
	 * a = 1 removes constant and returns 1 for constants
	*/
	public static String removeCoefficient(String f, int a) 
	{
		f = balanceBrackets(f);
		
		if(splitf(f).length > 1)
			return f;
		
		f = multiply(f);
		
		if(!f.contains("*"))
		{
			if(f.startsWith("-"))
				return f.substring(1);
			
			if(f.startsWith("x") || isSpecialConstant(f))
				return f;
			
			if(a == 0)
				return f;
			
			return "1";
		}
		
		return balanceBrackets(f.substring(f.indexOf("*")+1));
	}
	
	
	//returns a term without its power
	public static String removePower(String f) 
	{		
		if(!f.contains("^"))
			return f;
		
		return balanceBrackets(f.substring(0, f.indexOf("^")));
	}
	
	//returns the power for a term f in an equation
	public static String power(String f) 
	{
		if(!f.contains("^")) 
			return "1";

		return balanceBrackets(f.substring(yVal.operatorLocation(f, "^") + 1));
	}
	
	//returns power, but if its a constant, returns 0
	public static String specialPower(String f) 
	{	
		if(isConstant(f))
			return "0";

		return power(f);
	}
	
	//returns the highest power in an equation
	public static String highestPower(String f)
	{
		String max = "0";
		
		for(int i = 0; i < splitf(f).length; i++) 
		{
			if(Fraction.isGreater(power(splitf(f)[i]), max))
				max = power(splitf(f)[i]);
		}
		
		return max;
	}
	
	//returns the base for a term f in an equation
	public static String base(String f) 
	{
		if(!f.contains("^"))
			return f;
		
		return f.substring(0, yVal.operatorLocation(f, "^"));
	}
	
	
	//simplifies the constants in a String
	public static String simplifyConstants(String f) 
	{
		String[] a = splitf(f);
		
		for(int i = 0; i < a.length; i++)
		{
			if(isConstant(a[i]))
				a[i] = Fraction.pow(base(a[i]), power(a[i]));
		}
		
		return unsplit(a);
	}
	
	
    //distributes negative sign
    public static String distribute(String str)
    {
    	String second = str.substring(matchingBracket(str, 1) + 1);
    	String f = str.substring(0, matchingBracket(str, 1) + 1);
    	
    	while(canDistribute(f)) 
    	{			
    			
    			f = f.substring(2, f.length()-1);
    			f = balanceBrackets(f);
        		f = "+" + f;
        		
        		if(f.substring(0,2).equals("+-"))
        		{
        			f = f.substring(1);
        		}
        			    			    			    			
    			for(int i = 0; i < f.length()-1; i++) 
    			{
    				if(outsideBrackets(f, i)) 
    				{
    					//checks for places to distribute negative within the brackets itself (recurs)
    					if(f.substring(i, i+1).equals("-"))
    					{
    						
    						//I have to use 2 for loops because i+2 is out of the bounds for the for loop
    						if(f.substring(i+1, i+2).equals("(")) 
    						{        						
    							int location = 0;
        						int count = 1;
        						
        						for(int j = i+2; j < f.length(); j++) 
        						{
        							if(f.substring(j, j+1).equals("(")) 
        							{
        								count++;
        							}
        							else if(f.substring(j, j+1).equals(")")) 
        							{
        								count--;
        							}
        							
        							if(count == 0)
        							{
        								location = j;
        								break;
        							}
        						}
        						
        						f = f.substring(0,i) + distribute(f.substring(i, location+1)) + f.substring(location+1);
    						}
    					}
    						
    					if(f.substring(i, i+1).equals("+")) 
    					{
    						f = f.substring(0, i) + "-" + f.substring(i+1);
    					}
    					else if(f.substring(i, i+1).equals("-")) 
    					{
    						f = f.substring(0, i) + "+" + f.substring(i+1);
    					}
    				}
    			}
        	}
    	
    	return f + second;
    }
	
	
	//random supporting methods below
	
    //Returns true if string f is a constant
    public static boolean isConstant(String f) 
	{
		if(!f.contains("x"))
			return true;
		
		return false;
	}
	
	
  //returns true if a String can be foiled
  	public static boolean canFoil(String f) 
  	{
  		if(f.startsWith("(") && f.endsWith(")") && !canBalanceBrackets(f))
  		{
  			return true;
  		}
  		
  		return false;
  	}
  	
  //returns the function with all spaces removed
  	public static String noSpace(String f) 
  	{
  		String[] a = f.split(" ");
  		
  		f = "";
  		for(int i = 0; i < a.length; i++) 
  		{
  			f = f + a[i];
  		}
  		
  		return f;
  	}
    
  	
    //returns true if the operator at a point in the equation exists outside brackets
    public static boolean outsideBrackets(String f, int sub) 
    {
    	int count = 0;
    	String[] line = f.split("");
    	
    	for(int i = 0; i < sub; i++)
    	{
    		if(line[i].equals("(")) 
    			count++;
    		
    		if(line[i].equals(")")) 
    			count--;
    	}
    	
    	if(count == 0) 
    		return true;

    	return false;
    }
    
    
    //takes a string, splits it into terms and returns it.
    public static String[] splitf(String f) 
    {    	    	
    	f = balanceBrackets(f);
    	f = f.replaceAll("-", "\\+-");
    	
    	for(int i = 1; i < f.length()-1; i++) 
    	{
    		if(f.substring(i, i+1).equals("+") && outsideBrackets(f, i) && !f.substring(i-1, i).equals("^"))
    		{
    			f = f.substring(0, i) + "plus" + f.substring(i+1);
    		}
    	}
    	
    	f = f.replaceAll("\\+-", "-");

		return f.split("plus");
		
    }
    
    //combines an array of terms into an equation
    public static String unsplit(String[] f) 
    {
    	String a = "";
    	
    	for(int i = 0; i < f.length; i++) 
    		a += f[i] + "+";
    		
    	a = a.substring(0, a.length()-1);
    	a = a.replaceAll("\\+-", "\\-");
    	
    	return a;
    }
    
    //input a string, deletes unnecessary brackets, returns string
    public static String fixBrackets(String f)
    {    	
    	String[] terms = splitf(f);
    	
    	for(int i = 0; i < terms.length; i++) 
    	{
    		//here is where you actually delete the extra brackets on the outside and distribute 1 layer
    		//terms[i] = BalanceDistribute(terms[i]);
    		terms[i] = balanceBrackets(terms[i]);
    		
    		if(splitf(terms[i]).length > 1) 
    		{
    			terms[i] = fixBrackets(terms[i]);
    		}
    	}
    	
    	f = "";
    	for(int i = 0; i < terms.length-1; i++) 
    	{
    		f = f + terms[i] + "+";
    	}
    	f = f + terms[terms.length-1];
    	f = f.replaceAll("\\+-", "-");

    	return f;
    }
    
    
    //returns true if there are unnecessary brackets that can be removed on the outside
    public static boolean canBalanceBrackets(String f) 
    {    	
    	if(!f.startsWith("(") || !f.endsWith(")")) 
    	{
    		return false;
    	}
    	
    	f = f.substring(1, f.length()-1);
    	
    	int count = 0;
    	String[] line = f.split("");

    	for(int i = 0; i < f.length(); i++)
    	{
    		if(line[i].equals("(")) 
    		{
    			count++;
    		}
    		if(line[i].equals(")")) 
    		{
    			count--;
    		}
    		
    		if(count < 0) 
    		{
    			return false;
    		}
    	}
    	
    	if(count == 0) 
    	{
    		return true;
    	}
    	
    	return false;
    }
	
    
    //returns true if the brackets can be distributed
    public static boolean canDistribute(String f) 
    {
    	if(f.length() > 1) 
    	{
    		if(f.substring(0, 2).equals("-(") && f.substring(f.length()-1).equals(")")) 
        	{
        		return true;
        	}
    	}
    	
    	return false;
    }
    
   
    
	//gets rid of extra plus sign from the distribute method
    public static String distributeBrackets(String f) 
    {
    	f = distribute(f);
    	
		if(f.substring(0,1).equals("+"))
		{
			f = f.substring(1);
		}
		
		return f;
    }
    
    //balances brackets
    public static String balanceBrackets(String f) 
    {
    	while(canBalanceBrackets(f))
    	{
        	f = f.substring(1, f.length()-1);
    	}
    	
    	return f;
    }
    
    
    //adds in multiplication signs into the equation wherever necessary
    public static String multiply(String f) 
    {
    	for(int i = 0; i < f.length()-1; i++)
    	{	
    		if(checkMultiply(f, i))
    		{
    			f = f.substring(0, i+1) + "*" + f.substring(i+1);
    		}
    	}
    	
    	return f;
    }
    
    //returns true if you can insert a multiply sign
    public static boolean checkMultiply(String f, int i) 
    {
    	if(Character.isDigit(f.charAt(i)) && (Character.isAlphabetic(f.charAt(i+1)) || f.substring(i+1, i+2).equals("("))) 
			return true;
			
    	else if(f.substring(i, i+1).equals(")") && !isOperator(f.substring(i+1, i+2))) 
    		return true;
    	
    	else if((f.substring(i, i+1).equals("I") || f.substring(i, i+1).equals("E")) && !isOperator(f.substring(i+1, i+2)))
    		return true;
    	
    	else if((f.substring(i, i+1).equals("u") && Character.isAlphabetic(f.charAt(i+1))) || (f.substring(i, i+1).equals("v") && Character.isAlphabetic(f.charAt(i+1))) || (f.substring(i, i+1).equals("w") && Character.isAlphabetic(f.charAt(i+1))))
    		return true;
    	
    	return false;
    }
    
    //contains list of operators
    //returns true if the input string is an operator
    public static boolean isOperator(String f) 
    {
    	ArrayList<String> list = new ArrayList<String>();
    	
    	list.add("+");
    	list.add("-");
    	list.add("*");
    	list.add("/");
    	list.add("^");
    	list.add(")");
    	list.add(".");
    	
    	for(int i = 0; i < list.size(); i++) 
    	{
    		if(list.get(i).equals(f)) 
    		{
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    //returns index position of the close bracket matching the respective open bracket at index position n
    public static int matchingBracket(String str, int n) 
    {
    	int count = 1;
    	String f = str.substring(n);
    	    	
    	for(int i = 1; i < f.length()-1; i++) 
    	{
    		if(f.substring(i, i+1).equals("("))
    		{
    			count++;
    		}
    		if(f.substring(i, i+1).equals(")")) 
    		{
    			count--;
    		}
    		
    		if(count == 0) 
    		{
    			return i + str.length() - f.length();
    		}
    	}
    	
    	return f.length()-1 + str.length() - f.length();
    }
}
