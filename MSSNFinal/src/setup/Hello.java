package setup;

import processing.core.PApplet;

public class Hello implements IProcessingApp{
	
	private int c;
	
	public void setup(PApplet p){
		c = p.color(255,0,0);
	}
	
	public void draw(PApplet p, float dt){
		p.fill(c);
		p.circle(p.mouseX, p.mouseY, p.random(20, 70));
	}

	@Override
	public void mousePressed(PApplet p) {
		c = p.color(p.random(255),p.random(255),p.random(255));
		
	}

	@Override
	public void mouseMoved(PApplet p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(PApplet p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(PApplet p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(PApplet p) {
		// TODO Auto-generated method stub
		
	}
}
