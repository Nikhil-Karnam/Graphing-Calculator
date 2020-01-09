import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JFrame;


public class Window extends Canvas
{
    private static final long serialVersionUID = 1L;
    
    private JFrame frame = new JFrame();
    Box mainBox = Box.createVerticalBox();
    Box equation = Box.createHorizontalBox();
    
    public Window(int width, int height, Main main)
    { 
        //set up JFrame
    	
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        
        frame.add(main);
        
        frame.pack();
        frame.setVisible(true);
        
        //other stuff
        
        mainBox.setMaximumSize(new Dimension(100,Main.HEIGHT));
    }
    
    
    public void resetStates(STATE state) 
    {
    	Main.originalFunction = "";
    	
    	Main.originalPFunctionx = "";
    	Main.originalPFunctiony = "";
    	
    	Main.xfunction3D = "";
    	Main.yfunction3D = "";
    	Main.zfunction3D = "";
    	
		Main.state = state;
		Main.inversestate = INVERSE.normal;
		
		Plot.reset();
		Plot.reset3D();
		Main.paintCheck = true;
    }    
    
    
    public void menu() 
    {
    	//features
    	
    	Button button = new Button("Graph 2D Equations");
    	button.setFont(new Font("Calibri", Font.PLAIN, 30));
    	
    	Button button2 = new Button("Graph 3D Equations");
    	button2.setFont(new Font("Calibri", Font.PLAIN, 30));
    	
    	Button button3 = new Button("Menu");
    	button3.setFont(new Font("Calibri", Font.PLAIN, 30));
    	
    	Button button4 = new Button("Graph 4D Equations");
    	button4.setFont(new Font("Calibri", Font.PLAIN, 30));

    	//functionality
    	
    	//2D graphing
    	
    	button.addActionListener(new ActionListener() 
    	{
			public void actionPerformed(ActionEvent e) 
			{				
				if(Main.menu != MENU.graph2D)
				{
					Main.menu = MENU.graph2D;
					graph2D();	
					
					resetStates(STATE.normal);
				}		
			}
    	});
    	
    	//3D graphing
    	
    	button2.addActionListener(new ActionListener() 
    	{
    		public void actionPerformed(ActionEvent e) 
    		{	
    			if(Main.menu != MENU.graph3D)
				{
    				
					Main.menu = MENU.graph3D;
					graph3D();

					resetStates(STATE.normal);
				}
    		}
    	});
    	
    	//4D graphing
    	
    	button4.addActionListener(new ActionListener() 
    	{
    		public void actionPerformed(ActionEvent e) 
    		{	
    			if(Main.menu != MENU.graph4D)
				{
					Main.menu = MENU.graph4D;
					graph4D();
					
					resetStates(STATE.normal);
				}	
    		}
    	});
	
    	//main menu
    	
    	button3.addActionListener(new ActionListener() 
    	{
			public void actionPerformed(ActionEvent e) 
			{
				
				frame.remove(equation);
				frame.remove(mainBox);
				frame.pack();
								
				Main.menu = MENU.menu;
				
				resetStates(STATE.normal);		
			}
    		
    	});
    	
    	//adding features
    	
    	Box box = Box.createVerticalBox();
    	box.add(button);
    	box.add(button2);
    	box.add(button4);
    	box.add(button3);
    	
    	//adding to frame
    	frame.getContentPane().add(box, BorderLayout.WEST);
    	frame.pack();
    }
    
    
    public void graph2D()
    {
    	frame.remove(equation);
		frame.remove(mainBox);
		frame.pack();
    	
    	//features
        
    	Font font = new Font("Calibri", Font.PLAIN, 30);
    	Font font2 = new Font("Calibri", Font.PLAIN, 23);
    	
        JComboBox<String> box = new JComboBox<>();
        box.setFont(font);
        
        for(String b : "Normal Parametric".split(" "))
        	box.addItem(b);
        
                
        Button button = new Button("Invert");
        button.setFont(font);
        
        Button diff = new Button("Differentiate");
        diff.setFont(font);
        
        Button graph = new Button("Graph");
        graph.setFont(font);
        
        TextField text = new TextField("y = ");
        text.setFont(font);
        
        TextField x1 = new TextField("x = ");
        x1.setFont(font);
        
        TextField y1 = new TextField("y = ");
        y1.setFont(font);
        
        TextField tracestep = new TextField("tracestep: 0.001");
        tracestep.setFont(font2);
        
        //functionality
        
        
        //change graphing mode
        
        box.addActionListener(new ActionListener() 
    	{
			public void actionPerformed(ActionEvent e)
			{								
				if(box.getSelectedItem().equals("Normal"))
				{
					equation.removeAll();
					text.setText("y = ");
					equation.add(text);
					frame.pack();
					
					resetStates(STATE.normal);
				}

				else if(box.getSelectedItem().equals("Parametric")) 
				{
					equation.removeAll();
					x1.setText("x = ");
					y1.setText("y = ");
					equation.add(x1);
					equation.add(y1);
					frame.pack();
					
					resetStates(STATE.parametrics);
				}
			}
    	});
        
        //graph button
        
        graph.addActionListener(new ActionListener() 
    	{
			public void actionPerformed(ActionEvent e)
			{				
				//updates function
				
				if(Main.state == STATE.normal)
					Main.originalFunction = text.getText().substring(4);
								
				else if(Main.state == STATE.parametrics) 
				{
					Main.originalPFunctionx = x1.getText().substring(4);
					Main.originalPFunctiony = y1.getText().substring(4);
				}

				
				Plot.tracestep = Double.parseDouble(tracestep.getText().substring(10));
				
				Main.paintCheck = true;
				Plot.reset();
			}
    	});
        
        //inverses
        
        button.addActionListener(new ActionListener() 
    	{
			public void actionPerformed(ActionEvent e)
			{
				if(Main.inversestate == INVERSE.normal)
					Main.inversestate = INVERSE.inverse;
				
				else if(Main.inversestate == INVERSE.inverse)
					Main.inversestate = INVERSE.normal;

				
				Plot.reset();
				Main.paintCheck = true;
			}
    	});
        
        //derivatives
        
        diff.addActionListener(new ActionListener()
    	{
			public void actionPerformed(ActionEvent e)
			{
				if(Main.state == STATE.normal)
				{
					Main.originalFunction = Derivative.derivative(text.getText().substring(4));
					text.setText("y = " + Main.originalFunction);
					frame.pack();

					Plot.reset();
					Main.paintCheck = true;
					
				}	
			}
    	});
        
        
        //adding the features
        
        mainBox.removeAll();
        equation.removeAll();
        
        mainBox.add(button);
        mainBox.add(box);
        mainBox.add(diff);
        mainBox.add(graph);
        mainBox.add(tracestep);
        
        equation.add(text);
        
        
        //adding to frame  

        frame.getContentPane().add(mainBox, BorderLayout.EAST);
        frame.getContentPane().add(equation, BorderLayout.SOUTH);
        
        frame.pack();
    }
    
    
    public void graph3D() 
    {    	
    	frame.remove(equation);
		frame.remove(mainBox);
		frame.pack();
		
		//features
		
		Font font = new Font("Calibri", Font.PLAIN, 30);
		Font font2 = new Font("Calibri", Font.PLAIN, 25);
		
		Button graph = new Button("Graph");
        graph.setFont(font);
        
        TextField x = new TextField("x = ");
        x.setFont(font);
        
        TextField y = new TextField("y = ");
        y.setFont(font);
        
        TextField z = new TextField("z = ");
        z.setFont(font);
        
        TextField tracestep = new TextField("tracestep: 0.02");
        tracestep.setFont(font2);
        
        TextField uMin = new TextField("u min: 0");
        uMin.setFont(font2);
        
        TextField uMax = new TextField("u max: 3.14");
        uMax.setFont(font2);
        
        TextField vMin = new TextField("v min: 0");
        vMin.setFont(font2);
        
        TextField vMax = new TextField("v max: 3.14");
        vMax.setFont(font2);
        
        TextField window = new TextField("window: 2");
        window.setFont(font2);
        
        //functionality
        
        graph.addActionListener(new ActionListener()
    	{
			public void actionPerformed(ActionEvent e)
			{
				Main.xfunction3D = x.getText().substring(4);
				Main.yfunction3D = y.getText().substring(4);
				Main.zfunction3D = z.getText().substring(4);
				
				
				Plot.tracestep3D = Double.parseDouble(tracestep.getText().substring(10));
				
				Plot.uMin = yVal.calculateY(1, uMin.getText().substring(7));
				Plot.uMax = yVal.calculateY(1, uMax.getText().substring(7));
				
				Plot.vMin = yVal.calculateY(1, vMin.getText().substring(7));
				Plot.vMax = yVal.calculateY(1, vMax.getText().substring(7));

				Plot.window = Double.parseDouble(window.getText().substring(8));
				
				
				Main.paintCheck = true;
				Plot.reset3D();
			}
    	});
        
        
        //adding features
        
        equation.removeAll();
        mainBox.removeAll();
        
        equation.add(x);
        equation.add(y);
        equation.add(z);
        
        mainBox.add(graph);
        
        mainBox.add(tracestep);
        
        mainBox.add(uMin);
        mainBox.add(uMax);
        mainBox.add(vMin);
        mainBox.add(vMax);
        mainBox.add(window);
    
        
        //adding to frame
        
        frame.getContentPane().add(mainBox, BorderLayout.EAST);
        frame.getContentPane().add(equation, BorderLayout.SOUTH);
        
        frame.pack();
    }
    
    
    public void graph4D() 
    {
    	frame.remove(equation);
		frame.remove(mainBox);
		frame.pack();
		
		//features
		
		Font font = new Font("Calibri", Font.PLAIN, 30);
		Font font2 = new Font("Calibri", Font.PLAIN, 25);
		
		Button graph = new Button("Animate");
        graph.setFont(font);
        
        TextField x = new TextField("x = ");
        x.setFont(font);
        
        TextField y = new TextField("y = ");
        y.setFont(font);
        
        TextField z = new TextField("z = ");
        z.setFont(font);
        
        TextField tracestep = new TextField("tracestep: 0.08");
        tracestep.setFont(font2);
        
        TextField uMin = new TextField("u min: 0");
        uMin.setFont(font2);
        
        TextField uMax = new TextField("u max: 3.14");
        uMax.setFont(font2);
        
        TextField vMin = new TextField("v min: 0");
        vMin.setFont(font2);
        
        TextField vMax = new TextField("v max: 3.14");
        vMax.setFont(font2);
        
        TextField window = new TextField("window: 2");
        window.setFont(font2);
        
        //functionality
        
        graph.addActionListener(new ActionListener()
    	{
			public void actionPerformed(ActionEvent e)
			{
				Main.xfunction3D = x.getText().substring(4);
				Main.yfunction3D = y.getText().substring(4);
				Main.zfunction3D = z.getText().substring(4);
				
				
				Plot.tracestep3D = Double.parseDouble(tracestep.getText().substring(10));
				
				Plot.uMin = yVal.calculateY(1, uMin.getText().substring(7));
				Plot.uMax = yVal.calculateY(1, uMax.getText().substring(7));
				
				Plot.vMin = yVal.calculateY(1, vMin.getText().substring(7));
				Plot.vMax = yVal.calculateY(1, vMax.getText().substring(7));

				Plot.window = Double.parseDouble(window.getText().substring(8));
				
				
				Main.runTime = 0;
				Plot.reset3D();
			}
    	});
        
        
        //adding features
        
        equation.removeAll();
        mainBox.removeAll();
        
        equation.add(x);
        equation.add(y);
        equation.add(z);
        
        mainBox.add(graph);
        
        mainBox.add(tracestep);
        
        mainBox.add(uMin);
        mainBox.add(uMax);
        mainBox.add(vMin);
        mainBox.add(vMax);
        mainBox.add(window);
    
        
        //adding to frame
        
        frame.getContentPane().add(mainBox, BorderLayout.EAST);
        frame.getContentPane().add(equation, BorderLayout.SOUTH);
        
        frame.pack();
    }
    
    
	public void setTitle(String title) {
		frame.setTitle(title);
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
