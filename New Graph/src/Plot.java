import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;
import java.awt.Font;

public class Plot{
	
	public static double tracestep;
	public static double tracestep3D;
	
	public static double i = -10;
	
	public static double window;
	
	public static double uMin;
	public static double uMax;
	public static double vMin;
	public static double vMax;
	public static double u;
	public static double v;
						
	public static int longitude = 0;
	public static int latitude = 0;
	public boolean gridLine = true;

	public static boolean done = true;
	
	Random r = new Random();

	
	public static void reset() 
	{
		i = -10;
	}
	
	public static void reset3D()
	{
		u = uMin;
		v = vMin;
		
		longitude = 0;
		latitude = 0;
	}
	
	public Color makeColor(double x, double y) 
	{
		int val = (int)(x / 4);
		int val2 = (int)(y / 4);
				
		return new Color(val, 175, val2);
	}
	
	public Color makeColor(double x, double y, double z) 
	{
		int val = Math.abs((int)(x / 4))+40;
		int val2 = Math.abs((int)(y / 4)+40);
		int val3 = Math.abs((int)(z / 4)+40);
				
		while(val > 255)
			val = 500-val;
		
		while(val2 > 255)
			val2 = 500-val2;
		
		while(val3 > 255)
			val3 = 500-val3;

		return new Color(val3, val2, val);
	}
	
	
	public void renderGraph2D(Graphics g) 
	{
		
		if(Main.state == STATE.normal && Main.originalFunction.equals(""))
				return;
		
		if(Main.state == STATE.parametrics && (Main.originalPFunctionx.equals("") || Main.originalPFunctiony.equals("")))
			return;
		
		
		if(i<10)
		{	
			double x = 0;
			double y = 0;
			
			if(Main.state == STATE.normal)
			{
				x = i;
		    	y = yVal.calculateY(i, Main.originalFunction);
			}
			
			else if(Main.state == STATE.parametrics)
			{
				x = yVal.calculateY(i, Main.originalPFunctionx);
	        	y = yVal.calculateY(i, Main.originalPFunctiony);
			}
	    	
	    	
	    	//formats coordinates of point to match the canvas
	    	if(Main.inversestate == INVERSE.normal) 
	    	{
	    		x = (x*80+800)/2+50;
			    y = (-y*80+800)/2+100;
	    	}
	    	
	    	else if(Main.inversestate == INVERSE.inverse) 
			{
				x = (-x*80+800)/2+100;
		       	y = (y*80+800)/2+50;
			}
			
			    
		    //plots point
	    	
	    	if(Main.inversestate == INVERSE.normal) 
	    	{
	    		if(!Double.isNaN(y) && y <= 900 && y >=100 && !Double.isNaN(x) && x <= 850 && x >= 50)
			    {
			    	//sets color
		    		g.setColor(makeColor(x, y));
		    		
		    		//actually draws
		    		g.fillRect((int)x,(int)y,2,2);
		       	}
	    	}
		    
	    	else if(Main.inversestate == INVERSE.inverse) 
	    	{
	    		if(!Double.isNaN(y) && y <= 850 && y >=50 && !Double.isNaN(x) && x <= 900 && x >= 100)
			    {
			    	//sets color
		    		g.setColor(makeColor(x, y));
		    		
		    		//actually draws
		    		g.fillRect((int)y,(int)x,2,2);
		       	}
	    	}
	    	
	    	i+= tracestep;
		}
	}
	
	
	//3D graph
	
	public void renderGraph3D(Graphics g) 
	{			
		if(Main.xfunction3D.equals("") || Main.yfunction3D.equals("") || Main.zfunction3D.equals(""))
			return;
		
		if(u <= uMax) 
		{
			 if(longitude % 10 == 0 || latitude % 10 == 0)
				gridLine = true;
			 else
				gridLine = false;
			 

			double x = yVal.calculateY(u, v, Main.xfunction3D);
			double y = yVal.calculateY(u, v, Main.yfunction3D);
			double z = yVal.calculateY(u, v, Main.zfunction3D);
			
			
			x += y/2;
			z += y/2;
			
			
			x = x * 400/window + 400 + 50;
			z = z * -400/window + 400 + 100;

			
			if(!Double.isNaN(z) && z <= 900 && z >= 100 && !Double.isNaN(x) && x <= 850 && x>=50)
			{	
				if(gridLine)
					g.setColor(Color.white);
				else
					g.setColor(makeColor(x,z,10*y));
				
				g.fillRect((int)x,(int)z,2,2);
		    }
			
			v += tracestep3D;
			longitude++;
			
			
			if(v > vMax) 
			{
				u += tracestep3D;
				v = vMin;
				latitude++;
			}
				
		}
		
	}
	
	
	//Animated Graph
	
	public void renderAnimatedGraph(Graphics g) 
	{
		if(Main.xfunction3D.equals("") || Main.yfunction3D.equals("") || Main.zfunction3D.equals(""))
			return;
		
		done = false;

		
		for(u = uMin; u < uMax; u+=tracestep3D)
		{	
			for(v = vMin; v < vMax; v+=tracestep3D)
			{
				
				if(longitude % 10 == 0 || latitude % 10 == 0)
					gridLine = true;
				 else
					gridLine = false;
				
				
				double x = yVal.calculateY(u, v, Main.runTime, Main.xfunction3D);
				double y = yVal.calculateY(u, v, Main.runTime, Main.yfunction3D);
				double z = yVal.calculateY(u, v, Main.runTime, Main.zfunction3D);
				
				//System.out.println(x + "  " + y + "  " + z);
				
				x += y/2;
				z += y/2;
				
				x = x * 400/window + 400 + 50;
				z = z * -400/window + 400 + 100;

				
				if(!Double.isNaN(z) && z <= 900 && z >= 100 && !Double.isNaN(x) && x <= 850 && x>=50)
				{
					if(gridLine)
						g.setColor(Color.white);
					else
						g.setColor(makeColor(x,z,10*y));
										
					g.fillRect((int)x,(int)z,2,2);
			    }	
				
				
				longitude++;
			}
			
			latitude++;
		}
		
		latitude = 0;
		longitude = 0;		
		
		done = true;
	}
	
	
	//calculates slope at point i given 2 y values
	public double slope(double y1, double y2)
	{	
			return (y2-y1)/tracestep;		
	}
	
	//calculates points for nth derivative of a function
	public double derivative(int n)
	{
		int points = 2;
		
		for(int i = 1; i < n; i++)
		{
			points = 2*points-1;
		}
				
		//System.out.println(points);
		
		ArrayList<Double> a1 = new ArrayList<Double>();
		ArrayList<Double> a2 = new ArrayList<Double>();
		
		//first traversal
		for(int j = 0; j < points-1; j++)
		{
			a1.add(slope(yVal.calculateY(i+j*tracestep, Main.originalFunction), yVal.calculateY(i+(j+1)*tracestep, Main.originalFunction)));
		}
				
		//for more than 1 derivative
		if(n>1) 
		{			
			while(a2.size() != 1 && a1.size() != 1) 
			{				
				if(a1.isEmpty()) 
				{					
					for(int i = 0; i < a2.size()-1; i++) 
					{
						a1.add(slope(a2.get(i), a2.get(i+1)));
					}
					a2.clear();
				}
				else if(a2.isEmpty()) 
				{
					for(int i = 0; i < a1.size()-1; i++) 
					{
						a2.add(slope(a1.get(i), a1.get(i+1)));
					}
					a1.clear();
				}
			}
		}
		
		
		//return statements
		if(a1.isEmpty()) 
		{
			return a2.get(0);
		}
		return a1.get(0);
		
	}
	

    
}