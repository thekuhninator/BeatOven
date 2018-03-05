package Main;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JToolBar;

import Composer.Composer;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;

import javax.swing.Box;
import javax.swing.JMenuItem;
import net.miginfocom.swing.MigLayout;
import javax.swing.JEditorPane;
import javax.swing.JPanel;

public class BeatOven {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame  frame = new JFrame();
					
					MainPanel mainPanel = new MainPanel(frame);
					frame.setContentPane(mainPanel);
					mainPanel.setLayout(null);					
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
			
			
			//run();

		}
	}

	/**
	 * Create the application.
	 */

	/**
	 * Initialize the contents of the frame.
	 */

