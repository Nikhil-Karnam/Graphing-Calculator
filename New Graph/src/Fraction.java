import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

//fraction class that works using Strings
public class Fraction {
	
	public static void main(String[] args) 
	{
		System.out.println(multiply("2", "-3/5"));
	}
	
	//adds integer to fraction. returns as string
	public static String add(String a, String b)
    {
		a = Simplify.balanceBrackets(simplify(a));
		b = Simplify.balanceBrackets(simplify(b));
		
    	if(!a.contains("/"))
    		a = a + "/1";
    	if(!b.contains("/"))
    		b = b + "/1";
    	    		
    	int an = Integer.parseInt(a.split("/")[0]);
       	int ad = Integer.parseInt(a.split("/")[1]);
        	
        int bn = Integer.parseInt(b.split("/")[0]);
        int bd = Integer.parseInt(b.split("/")[1]);
        	
        int d = lcm(ad, bd);
        int n = d*an/ad + d*bn/bd;
        
        return simplify(n+"/"+d);
    }
    
	//subtracts 2 fractions
    public static String subtract(String a, String b) 
    {
    	a = Simplify.balanceBrackets(simplify(a));
		b = Simplify.balanceBrackets(simplify(b));
		
    	if(!a.contains("/"))
    		a = a + "/1";
    	if(!b.contains("/"))
    		b = b + "/1";
    	    		
    	int an = Integer.parseInt(a.split("/")[0]);
       	int ad = Integer.parseInt(a.split("/")[1]);
        	
        int bn = Integer.parseInt(b.split("/")[0]);
        int bd = Integer.parseInt(b.split("/")[1]);
        	
        int d = lcm(ad, bd);
        int n = d*an/ad - d*bn/bd;
        
        return simplify(n+"/"+d);
    }
    
    //multiplies 2 fractions, returns as string
    public static String multiply(String a, String b) 
    {	
    	a = Simplify.balanceBrackets(simplify(a));
		b = Simplify.balanceBrackets(simplify(b));
		
    	if(!a.contains("/"))
    		a = a + "/1";
    	if(!b.contains("/"))
    		b = b + "/1";
    	
    	int an = Integer.parseInt(a.split("/")[0]);
    	int ad = Integer.parseInt(a.split("/")[1]);
    	int bn = Integer.parseInt(b.split("/")[0]);
    	int bd = Integer.parseInt(b.split("/")[1]);
    	
    	an = an*bn;
    	ad = ad*bd;
    	
    	return simplify(an + "/" + ad);
    }
    
    //divides 2 decimals and outputs a simplified fraction as string
    public static String divide(String a, String b) 
    {
    	a = Simplify.balanceBrackets(simplify(a));
		b = Simplify.balanceBrackets(simplify(b));
    	
    	if(!a.contains("/"))
    		a = a + "/1";
    	if(!b.contains("/"))
    		b = b + "/1";
    	
    	int an = Integer.parseInt(a.split("/")[0]);
    	int ad = Integer.parseInt(a.split("/")[1]);
    	int bn = Integer.parseInt(b.split("/")[0]);
    	int bd = Integer.parseInt(b.split("/")[1]);
    	
    	an = an*bd;
    	ad = ad*bn;
    	
    	return simplify(an + "/" + ad);
    }
    
    //returns power of fractions a and b
    public static String pow(String a, String b)
    {
    	a = Simplify.balanceBrackets(simplify(a));
		b = Simplify.balanceBrackets(simplify(b));
    	
    	//calculates upper half of the power
    	if(!isFraction(b))
    		b = b + "/1";
    	
    	int n = Integer.parseInt(b.split("/")[0]);
    	int d = Integer.parseInt(b.split("/")[1]);
    	
    	String product = "1";
		for(int i = 0; i < n; i++)
			product = multiply(product,a);
		
		a = product;
		n = 1;

		if(!isFraction(b))
			return simplify(a);
		
		
		//calculates bottom half of power
		if(!isFraction(a))
			a = simplify(a)+"/1";
		
		for(int i = 0; i <= Integer.parseInt(a.split("/")[0]); i++)
			for(int j = 0; j <= Integer.parseInt(a.split("/")[1]); j++)
				if(pow(i+"/"+j, Integer.toString(d)).equals(simplify(a)))
				{
					a = i+"/"+j;
					d = 1;
					break;
				}
				
		//return statements
		a = simplify(a);
		b = simplify(b);
		
		if(d==1)
			return a;
		
		if(d%2 == 1 && isLesser(a,"0"))
			return "-" + pow(a.substring(1), b);
		
		if(d%2 == 0 && isLesser(a,"0"))//for complex numbers
		{
			String f = pow(a.substring(1), b);
			
			if(f.equals("1"))
				return "i";
			if(isFraction(f))
				return envelop(f) + "i";
			return f + "i";
		}
		
		return a + "^" + envelop(b);
    }
    
    
    //returns simplified fraction f
    public static String simplify(String f)
    {
    	f = Simplify.balanceBrackets(f.replaceAll("\\-\\(", "\\(\\-"));
    	
    	if(!f.contains("/"))
    		f = f + "/1";
    	
    	if(f.contains("."))
    	f = divide(decimalToFraction(Double.parseDouble(f.split("/")[0])), decimalToFraction(Double.parseDouble(f.split("/")[1])));
    	
    	if(!f.contains("/"))
    		f = f + "/1";
    	
    	int a = Integer.parseInt(f.split("/")[0]);
    	int b = Integer.parseInt(f.split("/")[1]);
    	
    	while(hasCommonFactor(a, b))
    	{
    		int gcf = gcf(a,b);
    		a = a/gcf;
    		b = b/gcf;
    	}
    	
    	if(b<0) 
    	{
    		b = Math.abs(b);
    		a *= -1;
    	}	
    	
    	if(a == 0)
    		return "0";
    	
    	if(b!=1)
    		return a + "/" + b;
    	
    	return Integer.toString(a);
    }
    
    
    //returns lowest common multiple of a and b
    public static int lcm(int a, int b) 
    {
    	int count = 1;
    	if(a>b) 
    	{	
    		while(true) 
    		{
    			if(a*count % b == 0) 
    			{
    				return a*count;
    			}
    			count++;
    		}
    	}
    	else if(b>a) 
    	{
    		while(true) 
    		{
    			if(b*count % a == 0) 
    			{
    				return b*count;
    			}
    			count++;
    		}
    	}
    	
    	return a;
    }
    
    //returns the lcm of more than 2 numbers
    public static int bigLcm(ArrayList<Integer> a) 
    {
    	while(a.size() > 1) 
    	{
    		a.set(0, lcm(a.get(0), a.get(1)));
    		a.remove(1);
    	}
    	
    	return a.get(0);
    }
    
    //returns the gcf of more than 2 numbers
    public static int bigGcf(ArrayList<Integer> a) 
    {
    	while(a.size() > 1) 
    	{
    		a.set(0, gcf(a.get(0), a.get(1)));
    		a.remove(1);
    	}
    	
    	return a.get(0);
    }
    
  //converts a decimal to a fraction
    public static String decimalToFraction(double a)
    {
    	String decimal = Double.toString(a);
    	
    	if(decimal.contains("."))
    		if(!cycleFinder(decimal).equals("no cycle"))
        		return recurringDecimal(Double.toString(a));
    	
    	
    	int b = 1;
    	
    	while(a-(int)a != 0.0)
    	{
    		a = 10 * a;
    		b = b * 10;
    	}
    	
    	while(a % 2 == 0 && b%2 == 0)
    	{
    		a = a/2;
    		b=b/2;
    	}
    	while(a % 5 == 0 && b%2 == 0)
    	{
    		a = a/5;
    		b=b/5;
    	}
    	
    	return (int)a+"/"+b;
    }
    
    
    //input a fraction as String, and outputs a double
    public static double fractionToDecimal(String a)
    {
    	double result;
    	
		a = Simplify.balanceBrackets(a);
		a = Simplify.distributeBrackets(a);
		
    	if(a.contains("e")) 
    	{
    		a = a.replaceAll("e", Double.toString(Math.E));
    	}
    	
    	if(a.contains("/"))
    	{    	    		    		
    		String first = a.split("/")[0];
    		String second = a.split("/")[1];
	    	result = Double.parseDouble(first) / Double.parseDouble(second);
    	}
    	else
    	{	
    		result = Double.parseDouble(a);
    	}
    	
		return result;
    }
    
    //returns greatest common factor of and b
    public static int gcf(int a, int b) 
    {
    	a = Math.abs(a);
    	b = Math.abs(b);
    	
    	for(int i = a; i > 1; i--)
    	{
    		if(a%i == 0 && b%i == 0)
    		{
    			return i;
    		}
    	}
    	
    	return 1;
    }
    
  
    
    //returns true if there is a common factor
    public static boolean hasCommonFactor(int a, int b)
    {		
    	a = Math.abs(a);
    	b = Math.abs(b);
    	
    	for(int i = 2; i <= a; i++) 
    	{
    		if(a%i == 0 && b%i == 0)
    		{
    			return true;
    		}
    	}
    	return false;
    }
    
    
    //returns true if the 2 numbers can be divided without recurring decimal
    public boolean divisibilityTest(int a, int b)
    {
    	if(a%b == 0)
    		return true;
    	
    	return false;
    }
    
  //returns true if the number is prime
    public boolean primeTester(int a)
    {
    	if(a == 0 || a == 1)
    		return false;
    	
    	for(int i = 2; i < a; i++) 
    	{
    		if(a % i == 0) 
    			return false;
    	}
    	
    	return true;
    }
    
    //returns true if f is a fraction, false if it is an integer
    public static boolean isFraction(String f) 
    {
    	try 
    	{
    		f = simplify(f);
    	}
    	catch(Exception e) {}
    	
    	if(f.contains("/"))
    		return true;
    	
    	return false;
    }
    
    //removes unnecessary decimals at the end of the String
    public static String fixDecimal(String f) 
    {
    	if(f.contains(".")) 
    	{
    		while(!f.endsWith("0"))
    			f = f.substring(0,f.length()-1);
    		
    		if(Integer.parseInt(f.split("\\.")[1]) == 0)
    			return f.substring(0, f.indexOf("."));
    	}
    		
    			
    	return f;
    }
    
    //envelops fraction with brackets
    public static String envelop(String f) 
    {
    	if(f.startsWith("(") && f.endsWith(")"))
    		return f;
    	
    	return "(" + f + ")";
    }
  	
    //returns true if a > b
    public static boolean isGreater(String a, String b) 
    {
    	if(fractionToDecimal(a) > fractionToDecimal(b))
    		return true;
    	
    	return false;
    }
    
    
    //returns true if a < b
    public static boolean isLesser(String a, String b) 
    {
    	if(fractionToDecimal(a) < fractionToDecimal(b))
    		return true;
    	
    	return false;
    }
    
    
    //sorts an array of fractions in ascending order
	public static String[] sortAscending(String[] array)
	{
	    boolean swapped = true;
	    int j = 0;
	    String tmp;
	    while (swapped) {
	        swapped = false;
	        j++;
	        for (int i = 0; i < array.length - j; i++) {
	            if (Fraction.isGreater(array[i], array[i+1])) {
	                tmp = array[i];
	                array[i] = array[i + 1];
	                array[i + 1] = tmp;
	                swapped = true;
	            }
	        }
	    }
	    
	    return array;
	}
	
    //sorts an array of fractions in descending order
	public static String[] sortDescending(String[] array)
	{
	    boolean swapped = true;
	    int j = 0;
	    String tmp;
	    while (swapped) {
	        swapped = false;
	        j++;
	        for (int i = 0; i < array.length - j; i++) {
	            if (Fraction.isLesser(array[i], array[i+1])) {
	                tmp = array[i];
	                array[i] = array[i + 1];
	                array[i + 1] = tmp;
	                swapped = true;
	            }
	        }
	    }
	    
	    return array;
	}
	
	//returns the recurring decimal in fraction form
	public static String recurringDecimal(String a) 
	{
		String cycle = cycleFinder(a);
		
		String fullCycle = "";
		int repeat = 0;
		
		if(cycle.length() == 1)
			repeat = 6;
		else
			repeat = (int)(12/cycle.length());
		
		for(int i = 0; i < repeat; i++)
			fullCycle += cycle;
		
		int rSpace = a.substring(a.indexOf(".")+1, a.indexOf(fullCycle)).length();
		rSpace = (int)Math.pow(10, rSpace);
		
		String nonRecurring = a.substring(0, a.indexOf(fullCycle));
		nonRecurring = simplify(nonRecurring);
		
		String d = "";
		for(int i = 0; i < cycle.length(); i++)
			d += "9";
		
		return add(nonRecurring, divide(divide(cycle, rSpace+""),d));

	}
	
	
	//finds a repeating cycle in a string
	public static String cycleFinder(String a) 
	{
		int cycles = 0;
		int maxcycles = 0;
		String cycleblock = "";
		HashSet<String> set = new HashSet<String>();
		
		for(int size = 1; size < a.length()/2; size++) 
		{			
			for(int i = 0; i < a.length()-size; i++) 
				set.add(a.substring(i, i+size));
			
			
			for(String b : set)
			{
				for(int j = 0; j < a.length()-size+1; j++) 
					if(a.substring(j, j + size).equals(b))
						cycles++;
				
				//checks whether its a cycle
				int repeat = 0;
				if(b.length() == 1)
					repeat = 6;
				else
					repeat = (int)(12/b.length());

				
				String fullcycle = "";
				for(int k = 0; k < repeat; k++)
					fullcycle += b;
				
				if(cycles > maxcycles && a.contains(fullcycle)) 
				{
					maxcycles = cycles;
					cycleblock = b;
				}
				
				cycles = 0;
			}
		}
		
		if(cycleblock.equals(""))
			return "no cycle";
		
		return cycleblock;
	}
	
	
	//returns a list of factors of a
	public static ArrayList<Integer> getFactors(int a)
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		if(a == 0)
			list.add(0);
		
		for(int i = 1; i <= a; i++) 
			if(a%i == 0)
				list.add(i);
		
		return list;
	}
		
	//returns a list of factors, but includes negative factors
	public static ArrayList<Integer> getFactorsWithNegative(int a)
	{
		a = Math.abs(a);
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		if(a == 0)
			list.add(0);
		
		for(int i = 1; i <= a; i++)
			if(a%i == 0) 
			{
				list.add(i);
				list.add(-i);
			}
		
		Collections.sort(list);
		
		return list;
	}
}
