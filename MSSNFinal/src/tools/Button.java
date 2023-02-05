package tools;

import dodger.State;
import processing.core.PApplet;
import processing.core.PFont;

public class Button{
	
	int x, y;
	PFont font;
	String text;
	int textSize;
	State nextState;
	float textWidth;
	int level;
	
	public Button(int x, int y, String text, PFont font, State nextState, PApplet p) {
		
		this.x = x;
		this.y = y;
		this.text = text;
		this.font = font;
		this.textSize = font.getSize();
		this.nextState = nextState;
		
	}
	
	public Button(int x, int y, String text, PFont font, State nextState, PApplet p, int level) {
		
		this.x = x;
		this.y = y;
		this.text = text;
		this.font = font;
		this.textSize = font.getSize();
		this.nextState = nextState;
		;
		
	}
	
	public void display(PApplet p) {
		
		// Desenho da box
		p.fill(255);
		p.noStroke();
		p.rect(x, y,textWidth + 30 , textSize + 30, 10);
		
		// Desenho do texto
		p.textFont(font);
		textWidth = p.textWidth(text);
		p.fill(0);
		p.text(text, x-1, y-4);
	}
	
	public State isInside(State s, PApplet p) {
		
		if (p.mouseX <= (x + textWidth/2)+15 && p.mouseX >= (x - textWidth/2)-15 && 
			      p.mouseY <= (y+textSize/2)+15 && p.mouseY >= (y-textSize/2)-15) {
			return nextState;
		}
		return s;
		
	}
	
	

}
