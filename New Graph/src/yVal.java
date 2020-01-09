
public class yVal {
	
    public static double calculateY(double x, String f)
    {
    	
    	//first formats the function
    	f = Simplify.noSpace(f);
    	f = Simplify.balanceBrackets(f);
    	f = Simplify.multiply(f);
    	
    	double y = 0;
    	
    	//addition
    	if(operatorTest(f, "+"))
		{
			String f1 = f.substring(0, operatorLocation(f, "+"));
			String f2 = f.substring(operatorLocation(f, "+") + 1);			
				
			y += calculateY(x, f1) + calculateY(x, f2);
		}
    		
    	//negation (subtraction as first symbol)
   		else if(operatorTest(f, "-") && operatorLocation(f, "-") == 0)
		{
			y -= calculateY(x, f.substring(1));
		}
    			
    	//normal subtraction
    	else if(operatorTest(f, "-") && !f.substring(operatorLocation(f, "-")-1, operatorLocation(f, "-")+1).equals("^-")) 
    	{
   			String f1 = f.substring(0, operatorLocation(f, "-"));
			String f2 = f.substring(operatorLocation(f, "-") + 1);			
				
			y += calculateY(x, f1) - calculateY(x, f2);
    	}
    			
   		//multiplication
   		else if(operatorTest(f, "*"))
   		{
   			String f1 = f.substring(0, operatorLocation(f, "*"));
			String f2 = f.substring(operatorLocation(f, "*") + 1);			
					
			y += calculateY(x, f1) * calculateY(x, f2);
    	}
    			
   		//division
  		else if(operatorTest(f, "/"))
		{
			String f1 = f.substring(0, operatorLocation(f, "/"));
			String f2 = f.substring(operatorLocation(f, "/") + 1);
					
			y += calculateY(x, f1) / calculateY(x, f2);
		}
				
		//power
		else if(operatorTest(f, "^")) 
		{
			String f1 = f.substring(0, operatorLocation(f, "^"));
			String f2 = f.substring(operatorLocation(f, "^") + 1);
										
			if(calculateY(x, f1) < 0) 
			{
				if(powerCheck(f2) == 1)//rotational symmetry
				{
					y -= Math.pow(-1 * calculateY(x, f1), calculateY(x, f2));
				}
				else if(powerCheck(f2) == 2)//reflect across y axis
				{
					y += Math.pow(-1 * calculateY(x, f1), calculateY(x, f2));
				}
				else
				{
					y += Math.pow(calculateY(x, f1), calculateY(x, f2));
				}
			}
			else
			{
				y += Math.pow(calculateY(x, f1), calculateY(x, f2));
			}
					
		}
				
    	//trigonometric functions below
    			
    			
    	//arc sin
		else if(operatorTest(f, "arcsin")) 
		{
			int location = operatorLocation(f, "arcsin") + 6;
					
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			y += Math.asin(calculateY(x, inside));
		}
    	//arc cosine
		else if(operatorTest(f, "arccos")) 
		{
			int location = operatorLocation(f, "arccos") + 6;
					
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			y += Math.acos(calculateY(x, inside));
		}
    	//arc tan
		else if(operatorTest(f, "arctan")) 
		{
			int location = operatorLocation(f, "arctan") + 6;
					
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			y += Math.atan(calculateY(x, inside));
		}
    			
    			
		//sin
		else if(operatorTest(f, "sin")) 
		{
			int location = operatorLocation(f, "sin") + 3;
					
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			y += Math.sin(calculateY(x, inside));
		}
    	//cos
		else if(operatorTest(f, "cos")) 
		{
			int location = operatorLocation(f, "cos") + 3;
				
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			y += Math.cos(calculateY(x, inside));
		}
    	//tan
		else if(operatorTest(f, "tan")) 
		{
			int location = operatorLocation(f, "tan") + 3;
					
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			y += Math.tan(calculateY(x, inside));
		}
    			
    	//csc
		else if(operatorTest(f, "csc")) 
		{
			int location = operatorLocation(f, "csc") + 3;
					
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			y += 1 / Math.sin(calculateY(x, inside));
		}
    	//sec
		else if(operatorTest(f, "sec")) 
		{
			int location = operatorLocation(f, "sec") + 3;
					
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			y += 1 / Math.cos(calculateY(x, inside));
		}
    	//cot
		else if(operatorTest(f, "cot")) 
		{
			int location = operatorLocation(f, "cot") + 3;
					
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			y += 1 / Math.tan(calculateY(x, inside));
		}
    			
    			
    	//logarithm functions below
    			
		//natural log
		else if(operatorTest(f, "ln"))
		{
			int location = operatorLocation(f, "ln") + 2;
					
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			y += Math.log(calculateY(x, inside));
		}
				
		//log
		else if(operatorTest(f, "log")) 
		{
					
			int location = operatorLocation(f, "log") + 3;
										
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			y += Math.log10(calculateY(x, inside));
		}
				
		//just variable x
		else if(f.equals("x") || f.equals("t"))
		{
			y += x;
		}
				
		//constants
		else
   		{	
    		if(f.equals("E"))
    			y += Math.E;
    		
    		else if(f.equals("PI")) 
    			y += Math.PI;
    		
    		else 
    			y += Double.parseDouble(f);	
    	}
    	
    	return y;
    }
    
    
    //second calculate method
    
    public static double calculateY(double u, double v, String f)
    {    	    	
    	//first formats the function
    	f = Simplify.noSpace(f);
    	f = Simplify.balanceBrackets(f);
    	f = Simplify.multiply(f);
    	    	
    	double y = 0;
    	
    	//addition
    	if(operatorTest(f, "+"))
		{
			String f1 = f.substring(0, operatorLocation(f, "+"));
			String f2 = f.substring(operatorLocation(f, "+") + 1);			
				
			y += calculateY(u, v, f1) + calculateY(u, v, f2);
		}
    		
    	//negation (subtraction as first symbol)
   		else if(operatorTest(f, "-") && operatorLocation(f, "-") == 0)
		{
			y -= calculateY(u, v, f.substring(1));
		}
    			
    	//normal subtraction
    	else if(operatorTest(f, "-") && !f.substring(operatorLocation(f, "-")-1, operatorLocation(f, "-")+1).equals("^-")) 
    	{
   			String f1 = f.substring(0, operatorLocation(f, "-"));
			String f2 = f.substring(operatorLocation(f, "-") + 1);			
				
			y += calculateY(u, v, f1) - calculateY(u, v, f2);
    	}
    			
   		//multiplication
   		else if(operatorTest(f, "*"))
   		{
   			String f1 = f.substring(0, operatorLocation(f, "*"));
			String f2 = f.substring(operatorLocation(f, "*") + 1);			
					
			y += calculateY(u, v, f1) * calculateY(u, v, f2);
    	}
    			
   		//division
  		else if(operatorTest(f, "/"))
		{
			String f1 = f.substring(0, operatorLocation(f, "/"));
			String f2 = f.substring(operatorLocation(f, "/") + 1);
					
			y += calculateY(u, v, f1) / calculateY(u, v, f2);
		}
				
		//power
		else if(operatorTest(f, "^")) 
		{
			String f1 = f.substring(0, operatorLocation(f, "^"));
			String f2 = f.substring(operatorLocation(f, "^") + 1);
										
			if(calculateY(u, v, f1) < 0) 
			{
				if(powerCheck(f2) == 1)//rotational symmetry
				{
					y -= Math.pow(-1 * calculateY(u, v, f1), calculateY(u, v, f2));
				}
				else if(powerCheck(f2) == 2)//reflect across y axis
				{
					y += Math.pow(-1 * calculateY(u, v, f1), calculateY(u, v, f2));
				}
				else
				{
					y += Math.pow(calculateY(u, v, f1), calculateY(u, v, f2));
				}
			}
			else
			{
				y += Math.pow(calculateY(u, v, f1), calculateY(u, v, f2));
			}
					
		}
				
    	//trigonometric functions below
    			
    			
    	//arc sin
		else if(operatorTest(f, "arcsin")) 
		{
			int location = operatorLocation(f, "arcsin") + 6;
					
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			y += Math.asin(calculateY(u, v, inside));
		}
    	//arc cosine
		else if(operatorTest(f, "arccos")) 
		{
			int location = operatorLocation(f, "arccos") + 6;
					
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			y += Math.acos(calculateY(u, v, inside));
		}
    	//arc tan
		else if(operatorTest(f, "arctan")) 
		{
			int location = operatorLocation(f, "arctan") + 6;
					
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			y += Math.atan(calculateY(u, v, inside));
		}
    			
    			
		//sin
		else if(operatorTest(f, "sin")) 
		{
			int location = operatorLocation(f, "sin") + 3;
					
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			y += Math.sin(calculateY(u, v, inside));
		}
    	//cos
		else if(operatorTest(f, "cos")) 
		{
			int location = operatorLocation(f, "cos") + 3;
				
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			y += Math.cos(calculateY(u, v, inside));
		}
    	//tan
		else if(operatorTest(f, "tan")) 
		{
			int location = operatorLocation(f, "tan") + 3;
					
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			y += Math.tan(calculateY(u, v, inside));
		}
    			
    	//csc
		else if(operatorTest(f, "csc")) 
		{
			int location = operatorLocation(f, "csc") + 3;
					
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			y += 1 / Math.sin(calculateY(u, v, inside));
		}
    	//sec
		else if(operatorTest(f, "sec")) 
		{
			int location = operatorLocation(f, "sec") + 3;
					
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			y += 1 / Math.cos(calculateY(u, v, inside));
		}
    	//cot
		else if(operatorTest(f, "cot")) 
		{
			int location = operatorLocation(f, "cot") + 3;
					
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			y += 1 / Math.tan(calculateY(u, v, inside));
		}
    			
    			
    	//logarithm functions below
    			
		//natural log
		else if(operatorTest(f, "ln"))
		{
			int location = operatorLocation(f, "ln") + 2;
					
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			y += Math.log(calculateY(u, v, inside));
		}
				
		//log
		else if(operatorTest(f, "log"))
		{
					
			int location = operatorLocation(f, "log") + 3;
										
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			y += Math.log10(calculateY(u, v, inside));
		}
				
		//just variables x and w
		else if(f.equals("u"))
		{
			y += u;
		}
    	
		else if(f.equals("v")) 
		{
			y += v;
		}
				
		//constants
		else
   		{	
    		if(f.equals("E"))
    			y += Math.E;
    		
    		else if(f.equals("PI")) 
    			y += Math.PI;
    		
    		else 
    			y += Double.parseDouble(f);	
    	}
    	
    	return y;
    }
    
    
    //third calculate method
    
    public static double calculateY(double u, double v, double w, String f)
    {    	    	
    	if(f.equals(""))
    		return 0;
    	
    	//first formats the function
    	f = Simplify.noSpace(f);
    	f = Simplify.balanceBrackets(f);
    	f = Simplify.multiply(f);
    	
    	double result = 0;
    	
    	//addition
    	if(operatorTest(f, "+"))
		{
			String f1 = f.substring(0, operatorLocation(f, "+"));
			String f2 = f.substring(operatorLocation(f, "+") + 1);			
				
			result += calculateY(u, v, w, f1) + calculateY(u, v, w, f2);
		}
    		
    	//negation (subtraction as first symbol)
   		else if(operatorTest(f, "-") && operatorLocation(f, "-") == 0)
		{
			result -= calculateY(u, v, w, f.substring(1));
		}
    			
    	//normal subtraction
    	else if(operatorTest(f, "-") && !f.substring(operatorLocation(f, "-")-1, operatorLocation(f, "-")+1).equals("^-")) 
    	{
   			String f1 = f.substring(0, operatorLocation(f, "-"));
			String f2 = f.substring(operatorLocation(f, "-") + 1);			
				
			result += calculateY(u, v, w, f1) - calculateY(u, v, w, f2);
    	}
    			
   		//multiplication
   		else if(operatorTest(f, "*"))
   		{
   			String f1 = f.substring(0, operatorLocation(f, "*"));
			String f2 = f.substring(operatorLocation(f, "*") + 1);			
					
			result += calculateY(u, v, w, f1) * calculateY(u, v, w, f2);
    	}
    			
   		//division
  		else if(operatorTest(f, "/"))
		{
			String f1 = f.substring(0, operatorLocation(f, "/"));
			String f2 = f.substring(operatorLocation(f, "/") + 1);
					
			result += calculateY(u, v, w, f1) / calculateY(u, v, w, f2);
		}
				
		//power
		else if(operatorTest(f, "^")) 
		{
			String f1 = f.substring(0, operatorLocation(f, "^"));
			String f2 = f.substring(operatorLocation(f, "^") + 1);
										
			if(calculateY(u, v, w, f1) < 0) 
			{
				if(powerCheck(f2) == 1)//rotational symmetry
				{
					result -= Math.pow(-1 * calculateY(u, v, w, f1), calculateY(u, v, w, f2));
				}
				else if(powerCheck(f2) == 2)//reflect across y axis
				{
					result += Math.pow(-1 * calculateY(u, v, w, f1), calculateY(u, v, w, f2));
				}
				else
				{
					result += Math.pow(calculateY(u, v, w, f1), calculateY(u, v, w, f2));
				}
			}
			else
			{
				result += Math.pow(calculateY(u, v, w, f1), calculateY(u, v, w, f2));
			}
					
		}
				
    	//trigonometric functions below
    			
    			
    	//arc sin
		else if(operatorTest(f, "arcsin")) 
		{
			int location = operatorLocation(f, "arcsin") + 6;
					
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			result += Math.asin(calculateY(u, v, w, inside));
		}
    	//arc cosine
		else if(operatorTest(f, "arccos")) 
		{
			int location = operatorLocation(f, "arccos") + 6;
					
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			result += Math.acos(calculateY(u, v, w, inside));
		}
    	//arc tan
		else if(operatorTest(f, "arctan")) 
		{
			int location = operatorLocation(f, "arctan") + 6;
					
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			result += Math.atan(calculateY(u, v, w, inside));
		}
    			
    			
		//sin
		else if(operatorTest(f, "sin")) 
		{
			int location = operatorLocation(f, "sin") + 3;
					
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			result += Math.sin(calculateY(u, v, w, inside));
		}
    	//cos
		else if(operatorTest(f, "cos")) 
		{
			int location = operatorLocation(f, "cos") + 3;
				
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			result += Math.cos(calculateY(u, v, w, inside));
		}
    	//tan
		else if(operatorTest(f, "tan")) 
		{
			int location = operatorLocation(f, "tan") + 3;
					
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			result += Math.tan(calculateY(u, v, w, inside));
		}
    			
    	//csc
		else if(operatorTest(f, "csc")) 
		{
			int location = operatorLocation(f, "csc") + 3;
					
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			result += 1 / Math.sin(calculateY(u, v, w, inside));
		}
    	//sec
		else if(operatorTest(f, "sec")) 
		{
			int location = operatorLocation(f, "sec") + 3;
					
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			result += 1 / Math.cos(calculateY(u, v, w, inside));
		}
    	//cot
		else if(operatorTest(f, "cot")) 
		{
			int location = operatorLocation(f, "cot") + 3;
					
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			result += 1 / Math.tan(calculateY(u, v, w, inside));
		}
    			
    			
    	//logarithm functions below
    			
		//natural log
		else if(operatorTest(f, "ln"))
		{
			int location = operatorLocation(f, "ln") + 2;
					
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			result += Math.log(calculateY(u, v, w, inside));
		}
				
		//log
		else if(operatorTest(f, "log"))
		{
					
			int location = operatorLocation(f, "log") + 3;
										
			String inside = f.substring(location, Simplify.matchingBracket(f, location)+1);
			result += Math.log10(calculateY(u, v, w, inside));
		}
				
		//just variables x and w
		else if(f.equals("u"))
			result += u;
    	
		else if(f.equals("v"))
			result += v;
    	
		else if(f.equals("w"))
			result += w;
				
		//constants
		else
   		{	
    		if(f.equals("E"))
    			result += Math.E;
    		
    		else if(f.equals("PI")) 
    			result += Math.PI;
    		
    		else 
    			result += Double.parseDouble(f);	
    	}
    	
    	return result;
    }
    
    
    //checks for plotting negative x values with fraction powers
    public static int powerCheck(String f) 
    {
    	
    	if(f.contains("/") && !f.contains("x") && !f.contains("^")) 
    	{
    		f = Simplify.distribute(f);
    		f = Simplify.balanceBrackets(f);
    		
    		String firstString = f.split("\\/")[0];
        	String secondString = f.split("\\/")[1];
        	
        	int first = Math.abs(Integer.parseInt(firstString));
        	int second = Math.abs(Integer.parseInt(secondString));
        	        	
        	if(second % 2 == 1 && first % 2 == 1) 
        	{
        		return 1;//rotational symmetry
        	}
        	else if(second % 2 == 1 && first % 2 == 0)
    		{
    			return 2;//reflect over y axis
    		}
    	}
    	
    	//if you don't need to check the power
    	return 0;
    }
    
    
    //takes in an operator and function, returns true if there are any operator signs outside of brackets
    
    public static boolean operatorTest(String first, String operator) 
    {
    	int size = operator.length();
    	
    	for(int k = 0; k < first.length()-size; k++) 
		{
			if(first.substring(k, k+size).equals(operator) && Simplify.outsideBrackets(first, k)) 
			{
				return true;
			}
		}
		
		return false;
    }
    
    //takes in an operator and a function
    //returns the first index position of that operator type which is outside the brackets
    public static int operatorLocation(String f, String operator)
    {
    	int size = operator.length();
    	int location = 0;
		
		for(int k = 0; k < f.length()-size; k++) 
		{
			if(f.substring(k, k+size).equals(operator) && Simplify.outsideBrackets(f, k)) 
			{
				return k;
			}
		}
		
		return location;
    }
    
    
}

   