package Biliard;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Ball implements Comparable<Ball>{
	
	Random rand = new Random();
	Table table = new Table(null);
	
	Ball ball = this;
	

	public Vector2D velocity;
	public Vector2D position;
	public Sound ballHit = new Sound();
	
	private static int diameter = 30;
    
    int ballType = -1; //-1 - solid; 0 - white; 1 - striped; 2 - black
    
    Vector2D t;
    
    
	
    public Ball(int x, int y, int ballType) {
    	this.velocity = new Vector2D(0, 0);
		this.position = new Vector2D(x, y);
		this.ballType = ballType;
	}
    
//    public Ball(int x, int y, boolean ballType, ballNumber) {
//
//		this.ballNumber = ballNumber;
//	}
    
	public void paint(Graphics g){
		if(ballType == -1)
			g.setColor(Color.blue);
		else if(ballType == 0)
			g.setColor(Color.white);
		else if(ballType == 2)
			g.setColor(Color.black);
		else
			g.setColor(Color.red);
		g.fillOval((int) position.getX(),(int) position.getY(),diameter,diameter);
    }
	
	public void ballToBallCollision(Ball ball)
	{
		Vector2D distVec = (position.subtract(ball.position));
		float distanceSq = distVec.dot(distVec);

		if (distanceSq > getDiameter()*getDiameter()) return;
		
		float distance = distVec.getLength();

		Vector2D mtd;
		if (distance != 0.0f)
		{
			mtd = distVec.multiply((getDiameter()-distance)/distance);
		}
		else
		{
			distance = getDiameter() - 1;
			distVec = new Vector2D(getDiameter(), 0);

			mtd = distVec.multiply((getDiameter()-distance)/distance);
		}


		position = position.add(mtd.multiply(1/2));
		ball.position = ball.position.subtract(mtd.multiply(1/2));

		Vector2D v = (this.velocity.subtract(ball.velocity));
		float vn = v.dot(mtd.normalize());

		if (vn > 0.0f) return;
//

		Vector2D impulse = mtd.multiply(-vn);
//
		this.velocity = this.velocity.add(impulse);
		ball.velocity = ball.velocity.subtract(impulse);
		
		if(ball.getVelocity().getX() != 0 && ball.getVelocity().getY() != 0) {
			try {
				ballHit.loadSound("./audio/BallHit.wav");
				ballHit.playSound();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public void frictionForce(){
		if(ball.velocity.getLength()!=0) {
			if(Math.abs(ball.velocity.getX())>=1) {
				if(ball.velocity.getX()>0)
					ball.velocity.setX((float) (ball.velocity.getX()-Math.abs(ball.velocity.getX())/Math.sqrt(ball.velocity.getX()*ball.velocity.getX() + ball.velocity.getY()*ball.velocity.getY())));
				else if(ball.velocity.getX()<0)
					ball.velocity.setX((float) (ball.velocity.getX()+Math.abs(ball.velocity.getX())/Math.sqrt(ball.velocity.getX()*ball.velocity.getX() + ball.velocity.getY()*ball.velocity.getY())));
			}
			if(Math.abs(ball.velocity.getY())>=1) {
				if(ball.velocity.getY()>0)
					ball.velocity.setY((float) (ball.velocity.getY()-Math.abs(ball.velocity.getY())/Math.sqrt(ball.velocity.getX()*ball.velocity.getX() + ball.velocity.getY()*ball.velocity.getY())));
				else if(ball.velocity.getY()<0)
					ball.velocity.setY((float) (ball.velocity.getY()+Math.abs(ball.velocity.getY())/Math.sqrt(ball.velocity.getX()*ball.velocity.getX() + ball.velocity.getY()*ball.velocity.getY())));
			}
			if(ball.velocity.getLength()<=1.5) {
				ball.velocity.setX(0);
				ball.velocity.setY(0);
			}
		}
	}
	
	
	public int compareTo(Ball ball) {
		if (this.position.getX() > ball.position.getX())
		{
			return 1;
		}
		else if (this.position.getX() < ball.position.getX())
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}

	public Vector2D getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2D velocity) {
		this.velocity = velocity;
	}

	public Vector2D getPosition() {
		return position;
	}

	public void setPosition(Vector2D position) {
		this.position = position;
	}

	public static int getDiameter() {
		return diameter;
	}
	
	public static int getRadius() {
		return diameter/2;
	}

	public static void setDiameter(int diameter) {
		Ball.diameter = diameter;
	}
}