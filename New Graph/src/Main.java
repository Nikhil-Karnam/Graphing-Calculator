import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.util.Scanner;

public class Main extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 2L;

    public static final int WIDTH = 1400, HEIGHT = 1050;
    private Thread thread;
    private boolean running = false;
    
    Scanner sc = new Scanner(System.in);
    public static boolean paintCheck = true;
    public static int graphSpeed;

    
    long lastTick = -1;
    double timeInterval = .1;
    static double runTime = 0;
    
    
    static MENU menu = MENU.menu;
    static STATE state = STATE.normal;
    static INVERSE inversestate = INVERSE.normal;
    
    
    public static String originalFunction = "";
    
    public static String originalPFunctionx = "";
    public static String originalPFunctiony = "";
    
    public static String xfunction3D = "";
    public static String yfunction3D = "";
    public static String zfunction3D = "";

    
    Plot plot = new Plot();
    Backgrounds backgrounds = new Backgrounds();
    
    Window window = new Window(WIDTH, HEIGHT, this);
    
    public Main()
    {
        this.addKeyListener(new KeyInput());
        
    	graphSpeed = 12;
    	
    	window.setTitle("Graphing Calculator");
    	window.menu();
    	    	
    	start();        
    }
    
    
    public synchronized void start()
    {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop()
    {
        try
        {
            thread.join();
            running = false;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void run()
    {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running)
        {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >=1)
            {
                tick();
                delta--;
            }
            if(running)
                render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000)
            {
                timer += 1000;
                //System.out.println("FPS: "+ frames);
                frames = 0;
            }
        }
        stop();
    }
    
    public void tick() 
    {
    	update();
    }
    
    public void render() 
    {
    	BufferStrategy bs = this.getBufferStrategy();
        if(bs == null)
        {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        
        //actual code starts here
        
        
        if(menu == MENU.menu) 
        {
        	if(paintCheck) 
            {
        		backgrounds.menu(g);
        		paintCheck = false;
            }
        }
        
        
        else if(menu == MENU.graph2D)
        {
        	if(paintCheck)
            {
        		backgrounds.graph2D(g);
        		paintCheck = false;
            }
        	
        	//graphs
        	
        	for(int i = 0; i < graphSpeed; i++)
        		plot.renderGraph2D(g);
        	 
        }
              
        
        else if(menu == MENU.graph3D) 
        {
        	if(paintCheck) 
        	{
        		backgrounds.graph3D(g);
            	paintCheck = false;
        	}
        	
        	for(int i = 0; i < graphSpeed; i++)
        		plot.renderGraph3D(g);
        }
        
        
        else if(menu == MENU.graph4D) 
        {
        	backgrounds.graph4D(g);	
        	plot.renderAnimatedGraph(g);
        }
        
        
        //ends code
        g.dispose();
        bs.show();
    }
    
    //updates timer
    public void update() 
    {
    	long now = System.currentTimeMillis();
     	long time = (long) (1000*timeInterval);
     	if(lastTick == -1) lastTick = now - time;
     	while((now - lastTick) >= time)
     	{
     		lastTick += time;
     		runTime += Plot.tracestep3D;
     	}
    }
    
    
    public static void main(String[] args) 
    {
    	new Main();
    }
}
