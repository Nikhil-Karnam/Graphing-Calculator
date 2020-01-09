
public class Derivative {

	//takes in array of terms and returns the derivative array

	public static void main(String[] args) 
	{
		System.out.println(derivative("x^2"));
	}
	
	public static String derivative(String f) 
    {
		if(f.equals(""))
			return f;
		
		
    	String[] terms = Simplify.splitf(Simplify.multiply(f));
    	
    	for(int i = 0; i < terms.length; i++) 
    	{
    		String first = terms[i];
    		
    		if(yVal.operatorTest(first, "*")) 
    		{
				String first1 = first.substring(0, yVal.operatorLocation(first, "*"));
				String first2 = first.substring(yVal.operatorLocation(first, "*") + 1);		
    			
    			first = Simplify.addTerms(Simplify.multiplyTerms(derivative(first1), first2) + "+" + Simplify.multiplyTerms(first1, derivative(first2)));
    		}
    		
    		else if(yVal.operatorTest(first, "^")) 
    		{
    			String first1 = first.substring(0, yVal.operatorLocation(first, "^"));
				String first2 = first.substring(yVal.operatorLocation(first, "^") + 1);
    			
				first = Simplify.multiplyTerms(Simplify.multiplyTerms(first1, first2) + "^" + Fraction.subtract(first2, "1"), derivative(first1));
    		}
    		
    		else if(first.equals("x"))
    		{
    			first = "1";
    		}
    		
    		else if(Simplify.isConstant(first))
    		{
    			first = "0";
    		}
    		
    		terms[i] = first;
    	}
    	
    	f = "";
    	
    	for(int i = 0; i < terms.length-1; i++) 
    	{
    		f = f + terms[i] + "+";
    	}
    	f = f + terms[terms.length-1];
    	
    	
    	return Simplify.addTerms(f);
    }
	
	
}
