package bilard;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Ball{
	
	Random rand = new Random();
	
	Ball ball = this;
	
	//polozenie lewego gornego "rogu"
	private int xPos;
	private int yPos;
	
	private int diameter = 70;
	private int xVel = 50;
    private int yVel = 100;
    private Color color = Color.WHITE;
	
    public Ball() {
    	xVel = rand.nextInt(110)-100;
    	yVel = rand.nextInt(110)-100;
    	color = new Color(rand.nextInt(100)+156, rand.nextInt(100)+156, rand.nextInt(100)+156);
	}

	public void paint(Graphics g){
		g.setColor(getColor());
//		g.drawImage(ballImg ,xPos, yPos, getWidth(), getHeight(), null );
		g.fillOval(xPos,yPos,diameter,diameter);
    }

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
	
	
	public int getxCenterPos() {
		return xPos + getRadius();
	}

//	public void setxCenterPos(int xPos) {
//		this.xPos = xPos - getRadius();
//	}

	public int getyCenterPos() {
		return yPos + getRadius();
	}

//	public void setyCenterPos(int yPos) {
//		this.yPos = yPos - getRadius();
//	}
	
	
	

	public int getDiameter() {
		return diameter;
	}
	
	public int getRadius() {
		return (diameter/2);
	}

	public int getxVel() {
		return xVel;
	}

	public void setxVel(int xVel) {
		this.xVel = xVel;
	}

	public int getyVel() {
		return yVel;
	}

	public void setyVel(int yVel) {
		this.yVel = yVel;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
