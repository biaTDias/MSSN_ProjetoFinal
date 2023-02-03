//This class is to group all game settings so they can be easily altered 


//TODO usar Java annotations para ter uma breve explicação in-IDE do que cada variável é


package dodger;

import processing.core.PApplet;
import processing.core.PFont;


public class GameSettings extends PApplet{
	//width e weight da janela usados em ProcessingSetup
	public final static int width = 1200;
	public final static int height = 700;
	
	//Font Standard
	public final static String fontStdFamily = "Verdana";
	public final static int fontStdSize = 22;
}
