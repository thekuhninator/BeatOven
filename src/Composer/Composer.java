package Composer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
//import javax.sound.*;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.ManagedPlayer;
import org.jfugue.player.Player;
import org.jfugue.player.SequencerManager;
import org.jfugue.realtime.RealtimePlayer;
import org.jfugue.rhythm.Rhythm;

public class Composer extends JPanel {

	private RealtimePlayer player;
	private ArrayList<String> patternSequences;
	private TreeMap<Integer,String> notes;
	//private PlayBar playBar;
	private Map<Character,String> rhythmKit;
	private Timer painter;
	//private PlayBar playbar;
	private boolean playing;


	public Composer()
	{
		makeTimer();
		//painter.start();
		//playbar = new PlayBar();
		try
		{
			player = new RealtimePlayer();
		}
		catch (MidiUnavailableException e)
		{
			e.printStackTrace();
		}
		patternSequences = new ArrayList<String>();
		rhythmKit = Rhythm.DEFAULT_RHYTHM_KIT; // makes a kit for instruments
	}
	private void makeTimer()
	{
		int delay = 100;
		ActionListener paintPerformer = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{ //  100 100 640 480
				repaint();
			}
		};
		painter = new Timer(delay, paintPerformer);
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0,0, 1280, 720);


		for(int p = 0; p <patternSequences.size(); p++)
		{
			String patStr = patternSequences.get(p);

			for(double c = 0; c < patStr.length(); )
			{
				char note = patStr.charAt((int)(c));
				getNote(g,patStr,note);
				if(note != 't')
				{
					g.fillRect((int)(c*35) + 5 + (((int)(c/4)) * 10),  30 + (p*40), 30, 30);

					if(c%4 == 0 && c >=4)
					{
						g.setColor(Color.BLACK);
						g.fillRect((int)(c*35) + 36, (int)(c)*100 + 50,  2,  50);
					}
				}
				else
				{

					g.fillRect((int)(c*35) + 5 + (((int)(c/4)) * 10),  30 + (p*40), 30/4, 30);
				}
				if(note != 't')
					c++;
				else
					c+=.25;
			}
		}
		//playbar.move();
		//playbar.draw(g);

	}
	private void getNote(Graphics g, String s, char c)
	{
		String instrument = rhythmKit.get(c);
		if(instrument.equals("Rs"))
			g.setColor(Color.DARK_GRAY);
		else if(instrument.contains("DRUM"))
		{
			g.setColor(Color.RED);
		}
		else if(instrument.contains("HAT"))
		{
			g.setColor(Color.GREEN);
		}
		else if(instrument.contains("SNARE"))
		{
			g.setColor(Color.BLUE);
		}
		else if(instrument.contains("CLAP"))
		{
			g.setColor(Color.ORANGE);
		}
	}
	public void compose()
	{
		//player.play("C D E F G A B");
		//p1.setIntrusment(Pattern)
		//Pattern p1 = new Pattern("V0 I[Piano] Eq Ch. | Eq Ch. | Dq Eq Dq Cq");
		//Pattern p2 = new Pattern("V1 I[Flute] Rw     | Rw     | GmajQQQ  CmajQ");
		//player.play(p1);
		//player.play(p2);
		Pattern drums = generateDrums();
		player.play(drums);
		painter.start();
		writeToFile();
	}
	private void writeToFile()
	{
		try
		{
			//BufferedWriter output = new BufferedWriter();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	private Pattern generateDrums()
	{
		Rhythm rhythm = new Rhythm(); // makes new Rhythm
		ArrayList<String>layers = new ArrayList<String>(); // different layers, (drums, hi hats, snares, etc.)
		ArrayList<ArrayList<String>> song = new ArrayList<ArrayList<String>>();
		String kick = generateKick();
		String snare = generateSnare();
		String clap = generateClap(kick,snare);
		String hats = generateHiHats();
		//String chords = generateChords();
		layers.add(generateKick());
		layers.add(generateSnare());
		layers.add(generateClap(layers.get(0),layers.get(1)));
		//layers.add("hhhtttt..hhhhhhhh..");
		layers.add(generateHiHats());


		for(int n = 0; n < layers.size(); n++)
		{
			patternSequences.add(layers.get(n));
		}


		makeRhythmKit();
		rhythm.setRhythmKit(rhythmKit); // sets the instruments

		// intro
		Pattern intro= new Pattern();
		intro.add(kick);
		// verse 1
		Pattern verse= new Pattern();
		verse.add(kick);
		verse.add(snare);
		verse.add(clap);
		// chorus
		Pattern chorus= new Pattern();
		chorus.add(kick);
		chorus.add(snare);
		chorus.add(clap);
		chorus.add(hats);
		// outro
		Pattern outro= new Pattern();
		outro.add(kick);
		outro.add(clap);

		Pattern beat = new Pattern();

		beat.add(intro);
		beat.add(verse);
		beat.add(chorus);
		beat.add(verse);
		beat.add(chorus);
		// beat.add(breakdown);
		beat.add(verse);
		beat.add(chorus);
		beat.add(outro);

		ArrayList<String> temperate  =new ArrayList<String>();
		String temp = "k.......k.........";
		temperate.add(temp);
		//temperate.add(temp2);
		beat.setTempo(120);
		//player.play(beat);
		//rhythm.setLayers(layers); // sets the beats
		//rhythm.setLayers(temperate);
		rhythm.setLayers(layers);

		Pattern drums = rhythm.getPattern();
		drums.setTempo(120);
		drums.repeat(4);
		addPaterns(rhythm);
		return drums;
	}
	private void makeRhythmKit()
	{
		rhythmKit.put('k', "[BASS_DRUM]s"); // sets instrument
		rhythmKit.put('c', "[HAND_CLAP]s"); // sets instrument
		rhythmKit.put('h', "[PEDAL_HI_HAT]s"); // sets instrument
		rhythmKit.put('t', "[PEDAL_HI_HAT]x");
		rhythmKit.put('s', "[ACOUSTIC_SNARE]s"); // sets instrument
		rhythmKit.put('`', "[CLOSED_HI_HAT]s"); // sets instrument
		rhythmKit.put('~', "[CLOSED_HI_HAT]s"); // sets instrument
		rhythmKit.put('^', "[CLOSED_HI_HAT]s"); // sets instrument
		rhythmKit.put('.', "Rs"); // sets instrument

		System.out.println(rhythmKit.toString());

	}
	private String generateKickPattern()
	{
		String kick = "k...............";
		for(int n = 2; n < kick.length(); n+=2)
		{
			if(Math.random() > .65)
			{
				kick = kick.substring(0, n) + 'k' + kick.substring(n+1,kick.length());
			}
		}
		System.out.println(kick);
		return kick;
	}
	private String generateKick()
	{
		String kickPattern;
		int kickCount;
		do
		{
			kickPattern = generateKickPattern();

			kickCount = 0;
			for(int i = 0; i < kickPattern.length(); i++)
				if(kickPattern.charAt(i) == 'k')
					kickCount++;
		}
		while(	kickPattern.substring(0,8).equals(kickPattern.substring(8,16)) ||
				kickPattern.substring(8,16).indexOf('k') == -1 ||
				kickCount > 5 ||
				kickCount < 2);

		return kickPattern;
	}
	private String generateSnare()
	{
		String snarePattern;
		snarePattern = "........s.......";

		return snarePattern;
	}
	private String generateClapPattern(String kickPattern, String snarePattern)
	{
		String clap = "................";
		for(int n = 2; n < clap.length(); n+=2)
		{
			if(	Math.random() > .65 &&
					kickPattern.charAt(n) != 'k' &&
					snarePattern.charAt(n) != 's')
			{
				clap = clap.substring(0, n) + 'c' + clap.substring(n+1,clap.length());
			}
		}
		System.out.println(clap);
		return clap;

	}
	private String generateClap(String kickPattern, String snarePattern)
	{
		String clapPattern;
		int clapCount;
		do
		{
			clapPattern = generateClapPattern(kickPattern, snarePattern);

			clapCount = 0;
			for(int i = 0; i < clapPattern.length(); i++)
				if(clapPattern.charAt(i) == 'c')
					clapCount++;
		}
		while(	//clapPattern.substring(8,16).indexOf('c') == -1 ||
				clapCount > 4 ||
						clapCount <= 1);

		return clapPattern;
	}
	private String generateHiHatPatterns()
	{
		String hiHatPattern = "hhhhhhhhhhhhhhhh";
		for(int n = 0; n < hiHatPattern.length(); n++)
		{
			if(hiHatPattern.charAt(n) != 't' &&	Math.random() > .80)
			{
				hiHatPattern = hiHatPattern.substring(0, n) + "tttt" + hiHatPattern.substring(n+1,hiHatPattern.length());
			}
			else if(hiHatPattern.charAt(n) != 't' && Math.random() < .30 && n+2 <= hiHatPattern.length() && n%2 ==0)
			{
				hiHatPattern = hiHatPattern.substring(0, n) + ".."+ hiHatPattern.substring(n+2,hiHatPattern.length());
			}
		}
		return hiHatPattern;
	}
	private String generateHiHats()
	{
		String hiHatPattern;
		int fullHatCount;
		int rollHatCount;
		do
		{
			hiHatPattern = generateHiHatPatterns();
			fullHatCount = 0;
			rollHatCount = 0;
			for(int i = 0; i < hiHatPattern.length(); i++)
			{
				if(hiHatPattern.charAt(i) == 'h')
					fullHatCount++;
				if(hiHatPattern.charAt(i) == 't')
					rollHatCount++;
			}
			rollHatCount/=4;
			System.out.println(hiHatPattern);
		}
		while(	hiHatPattern.indexOf("tttttttt") != -1 ||
				fullHatCount >= 14 ||
				rollHatCount >= 2);

		return hiHatPattern;
		// 16 s's so that is 1/16
	}
	public void generateChords()
	{
		Pattern pattern = new Pattern("C D E F");
	}
	private void addPaterns(Rhythm r)
	{
		patternSequences = (ArrayList<String>) r.getLayers();
	}
	public void getScale(String scale) {}
	public void draw(Graphics2D g)
	{
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0,0, 1280, 720);
		for(int p = 0; p < patternSequences.size(); p++)
		{
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0,p*100+200,500,100);
			String patStr = patternSequences.get(p);
			int pos = 0;
			g.drawString("BeatOven v1.0", 100,200);
			//g.drawString("");
			for(int c = 0; c< patStr.length();c++)
			{
				int temp = 0;
				char let = patStr.charAt(c);
				if(let == '^')
					temp= 16;
				else
					temp=48;
				g.setColor(Color.GREEN);
				g.drawRect(pos,p*100+250,temp,50);
				pos+=temp;
			}
		}
	}
	public void keyPressed(int k)
	{
		System.out.println("This is not working");
		switch(k)
		{
			case KeyEvent.VK_ENTER:
				compose();
				break;
			default:
				// need to add better player methods, stop, and what not ,and better rhythm kit.
				break;
		}
	}
	public void keyReleased(int k)
	{

	}
	public void update()
	{
	}
}
