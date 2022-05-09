package bilard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Table extends JPanel implements Runnable {
	
	Table table = this;
	
	List<Ball> balls = new ArrayList<Ball>();

	public Table() {
		setSize(912, 456);
		setBackground(new Color(0,80,0));
//		setMaximumSize(new Dimension(100,100));
		setPreferredSize(new Dimension(912, 456));
	}
	
	public Ball addBall(int x, int y){
		Ball ball = new Ball();
		ball.setxPos(x);
		ball.setyPos(y);

		balls.add(ball);		
		return ball;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (Ball ball : balls) {
			ball.paint(g);
		}

	}
	
	public void frictionForce() {
		for (Ball ball : balls) {
			if(Math.abs(ball.getxVel())>=1) {
				if(ball.getxVel()>0)
					ball.setxVel(ball.getxVel()-1);
				if(ball.getxVel()<0)
					ball.setxVel(ball.getxVel()+1);
			}
			if(Math.abs(ball.getyVel())>=1){
				if(ball.getyVel()>0)
					ball.setyVel(ball.getyVel()-1);
				if(ball.getyVel()<0)
					ball.setyVel(ball.getyVel()+1);
			}
		}
	}

	@Override
	public void run() {
		boolean b = true;
        int pause = 1;
        int distanceSquared;
        double theta;
        int tempxVel, tempyVel;
        int i = 1;
        while(b) {
        	for (Ball ball : balls) {
        		for (Ball otherBall : balls) {
        			if(ball!=otherBall) {
	        			distanceSquared = (ball.getxCenterPos()-otherBall.getxCenterPos())*(ball.getxCenterPos()-otherBall.getxCenterPos())+(ball.getyCenterPos()-otherBall.getyCenterPos())*(ball.getyCenterPos()-otherBall.getyCenterPos());
		        		
			        	if((ball.getxPos()+ball.getDiameter())==table.getWidth())
			        		ball.setxVel(-Math.abs(ball.getxVel()));
			        	if(ball.getxPos()==0)
			        		ball.setxVel(Math.abs(ball.getxVel()));
	        			
	        			if((ball.getyPos()+ball.getDiameter())==table.getHeight())
	            			ball.setyVel(-Math.abs(ball.getyVel()));
	        			if(ball.getyPos()==0)
			        		ball.setyVel(Math.abs(ball.getyVel()));
	        			
//	        			if(distanceSquared<=ball.getDiameter()*ball.getDiameter()) {
//	        				if((ball.getxCenterPos()==otherBall.getxCenterPos()))      					
//	        					theta = Math.PI/2;
//							else 
//								theta = Math.atan((ball.getyCenterPos()-otherBall.getyCenterPos())/(otherBall.getxCenterPos()-ball.getxCenterPos()));
//	        				
//	            			tempyVel = (int) (otherBall.getyVel()*Math.sin(theta)*Math.sin(theta)+ball.getyVel()*Math.cos(theta)*Math.cos(theta)+(ball.getxVel()-otherBall.getxVel())*Math.sin(theta)*Math.cos(theta));     		
//	            			otherBall.setyVel((int) (ball.getyVel()*Math.sin(theta)*Math.sin(theta)+otherBall.getyVel()*Math.cos(theta)*Math.cos(theta)+(otherBall.getxVel()-ball.getxVel())*Math.sin(theta)*Math.cos(theta)));
//	            			ball.setyVel(tempyVel);
//	    					
//	            			tempxVel = (int) (ball.getxVel()*Math.sin(theta)*Math.sin(theta)+otherBall.getxVel()*Math.cos(theta)*Math.cos(theta)+(ball.getyVel()-otherBall.getyVel())*Math.sin(theta)*Math.cos(theta));     		
//	            			otherBall.setxVel((int) (otherBall.getxVel()*Math.sin(theta)*Math.sin(theta)+ball.getxVel()*Math.cos(theta)*Math.cos(theta)+(otherBall.getyVel()-ball.getyVel())*Math.sin(theta)*Math.cos(theta)));
//	            			ball.setxVel(tempxVel);	
//	        				
	        					
//	        				tempxVel = ball.getxVel();
//	        				ball.setxVel(otherBall.getxVel());
//	        				otherBall.setxVel(tempxVel);
//	        				
//	        				tempyVel = ball.getyVel();
//	        				ball.setyVel(otherBall.getyVel());
//	        				otherBall.setyVel(tempyVel);
//	        			}
        			}
		        }
        		if(i%100==0) {
        			frictionForce();
        		}
        		
            	if(ball.getxVel()!=0) {
    	        	if(i%((int)(100/Math.abs(ball.getxVel())))==0) {
    	        		if(ball.getxVel()>0)
    	    				ball.setxPos(ball.getxPos()+1);
    	    			else
    	    				ball.setxPos(ball.getxPos()-1);
    	        	}
            	}
            	
            	if(ball.getyVel()!=0) {
            		if(i%((int)(100/Math.abs(ball.getyVel())))==0) {
    	        		if(ball.getyVel()>0)
    	    				ball.setyPos(ball.getyPos()+1);
    	    			else
    	    				ball.setyPos(ball.getyPos()-1);
    	        	}
            	}
	        }
        	
        	i++;
        	if(i%100==0) i=0;
        	repaint();
        	
        	try {
        		Thread.sleep(pause);
        	} catch (InterruptedException e) {
        		e.printStackTrace();
        	}
        }
	}
}        	
