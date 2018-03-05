package Composer;


import java.util.TreeMap;

public class Scales {
	
	private static TreeMap <Integer,String>scale = new TreeMap<Integer,String>();
	private static char[] cMajor = {'C','D','E','F','G','A','B'};
	

	public static TreeMap cMajor(int octave)
	{
		for(int n = 0; n < cMajor.length; n++)
		{
			//if(cMajor[0]> cMajor[n])
				scale.put(n,cMajor[n] + "" + (octave+1));
			//else
				scale.put(n,cMajor[n] + "" + (octave));
		}
		return scale;
	}
}
