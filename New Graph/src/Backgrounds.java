import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Backgrounds {

	public void menu(Graphics g) 
	{
		g.setColor(Color.white);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
				
		g.setColor(Color.red);
		g.setFont(new Font("Calibri", Font.PLAIN, 50));
		g.drawString("Graphing Calculator", 350, 500);
	}
	
	
	public void graph2D(Graphics g) 
	{
		//backgrounds
		
		g.setColor(Color.white);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);

		g.setColor(Color.black);
		g.fillRect(50, 100, 800, 800);
		
		g.drawRect(50, 20, 800, 70);
		
		//text
		g.setColor(Color.red);
		g.setFont(new Font("Calibri", Font.PLAIN, 50));
		g.drawString("2D Graphing", 350, 70);
		
		
		//draw axes
		
		g.setColor(Color.white);
		
		for(int i = 50; i < 850; i+=20)
	    {
			g.fillRect(i,500,10,2);
			g.fillRect(450,i+50,2,10);
	    }
	}
	
	
	public void graph3D(Graphics g) 
	{
		//backgrounds
		
		g.setColor(Color.white);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		
		g.setColor(Color.black);
		g.fillRect(50, 100, 800, 800);
		
		g.drawRect(50, 20, 800, 70);
		
		//text
		g.setColor(Color.red);
		g.setFont(new Font("Calibri", Font.PLAIN, 50));
		g.drawString("3D Graphing", 350, 70);
		
		
		//draw axes
		
		g.setColor(Color.white);

   	 	for(int i = 50; i <= 850; i++)
   	 	{
   	 		g.fillRect(i,500,2,2);
   	 		g.fillRect(450,i+50,2,2);
   	 		
   	 		if(i%50 == 0) 
   	 		{   	 			
   	 			g.drawOval(i-5, 500-5, 10, 10);
   	 			g.drawOval(450-5,i-5+50,10,10);
   	 		}
   	 	}
   	 	
   	 	int count = 0;
   	 	
   	 	for(int i = 0; i <= 800; i++)
   	 	{
   	 		g.fillRect(i+50, 800-i+100, 2, 2);
   	 	
   	 		if(count%25 == 0) 
	 			g.drawOval(i-5+50, (800-i)-5+100, 10, 10);
   	 		
   	 		count++;
   	 	}
   	 	
	}
	
	
	public void graph4D(Graphics g) 
	{
		//backgrounds
		
		g.setColor(Color.white);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		
		g.setColor(Color.black);
		g.fillRect(50, 100, 800, 800);
		
		g.drawRect(50, 20, 800, 70);
		
		//text
		g.setColor(Color.red);
		g.setFont(new Font("Calibri", Font.PLAIN, 50));
		g.drawString("Animated Surfaces", 280, 70);
		
		
		//draw axes
		
		g.setColor(Color.white);

   	 	for(int i = 50; i <= 850; i++)
   	 	{
   	 		g.fillRect(i,500,2,2);
   	 		g.fillRect(450,i+50,2,2);
   	 		
   	 		if(i%50 == 0)
   	 		{   	 			
   	 			g.drawOval(i-5, 500-5, 10, 10);
   	 			g.drawOval(450-5,i-5+50,10,10);
   	 		}
   	 	}
   	 	
   	 	int count = 0;
   	 	
   	 	for(int i = 0; i <= 800; i++)
   	 	{
   	 		g.fillRect(i+50, 800-i+100, 2, 2);
   	 	
   	 		if(count%25 == 0) 
	 			g.drawOval(i-5+50, (800-i)-5+100, 10, 10);
   	 		
   	 		count++;
   	 	}
   	 	
	}
	
	
}
