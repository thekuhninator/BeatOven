package Main;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;

import Composer.Composer;

public class MainPanel extends Container implements KeyListener, ActionListener {
	
	// dimensions
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final int SCALE = 1;
		
	// game thread
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;
	
	// image
	private BufferedImage image;
	private Graphics2D g;
	private JFrame frame;
	private Composer beethoven;
	
	public MainPanel(JFrame frame)
	{
		this.frame = frame;
		init();
	}

	private void init() {
		
		image = new BufferedImage(
					WIDTH, HEIGHT,
					BufferedImage.TYPE_INT_RGB
				);
		g = (Graphics2D) image.getGraphics();
		
		running = true;
		
		beethoven = new Composer();
		
		//JButton btnBake = new JButton("Bake");
		//btnBake.addActionListener(new ActionListener(){
		//public void actionPerformed(ActionEvent e){
		//	beethoven.compose();
		//}
		frame.setBounds(100, 100, 640, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JToolBar toolBar = new JToolBar();
		
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);
	
		JMenuItem mntmEraser = new JMenuItem("Eraser");
		toolBar.add(mntmEraser);
		
		JMenuItem mntmPaintBrush = new JMenuItem("Paint Brush");
		toolBar.add(mntmPaintBrush);
									
		Box horizontalBox = Box.createHorizontalBox();
		frame.getContentPane().add(horizontalBox, BorderLayout.WEST);
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		frame.getContentPane().add(horizontalBox_1, BorderLayout.EAST);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenuItem mntmFile = new JMenuItem("File");
		menuBar.add(mntmFile);
		
		JMenuItem mntmEdit = new JMenuItem("Edit");
		menuBar.add(mntmEdit);
		
		JMenuItem mntmOptions = new JMenuItem("Options");
		menuBar.add(mntmOptions);
		frame.setVisible(true);
		JButton btnBake = new JButton("Bake");
		btnBake.setBounds(frame.getWidth()/2-50, frame.getHeight()-100, 100, 25);
		
		beethoven.setBounds(10,20,600,350);
		this.add(beethoven);

		
		btnBake.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0)
			{
				beethoven.compose();
			}
		});
		add(btnBake);
		
		
		//});
		//frame.add(btnBake, BorderLayout.SOUTH);

		//run();
	}
	private void run()
	{			
		long start;
		long elapsed;
		long wait;
			
		// game loop
		while(running) {
				
			start = System.nanoTime();
				
			update();
			draw();
			drawToScreen();
				
			elapsed = System.nanoTime() - start;
				
			wait = targetTime - elapsed / 1000000;
			if(wait < 0) wait = 5;
			try {
				Thread.sleep(wait);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
				
		}
	}
	
	private void drawToScreen() 
	{
		System.out.println(frame.getContentPane() == null);
		
		Graphics g2 = frame.getContentPane().getGraphics();
		
		System.out.println(image == null);
		g2.drawImage(image, 0, 0,
				WIDTH * SCALE, HEIGHT * SCALE,
				null);
		g2.dispose();
	}
	public void draw()
	{
		beethoven.draw(g);
	}
	public void update()
	{
		beethoven.update();
	}
	public void keyPressed(KeyEvent key) 
	{
		beethoven.keyPressed(key.getKeyCode());
	}

	public void keyReleased(KeyEvent key) 
	{
		beethoven.keyReleased(key.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
