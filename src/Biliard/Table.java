package Biliard;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

public class Table extends JPanel implements Runnable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Table table = this;
	String typeOfGame;
//	RightPanel rightPanel = new RightPanel();
	MainClass frame;
	int ballNumber = 16;
	private Ball[] balls = new Ball[ballNumber];
	private Pocket[] pockets = new Pocket[6];
	int ballCount = 0;
	Date data;
	long start;
	private Vector2D strike = new Vector2D();
	private Vector2D direction, gap, stickVec;

	public Sound ballHit = new Sound();

	long previousTime;
	
	Ball cueBall;
	
	int stickWidth = 5;
	Color stickColor = Color.black;
	int stickX1, stickX2, stickY1, stickY2;

	public Table(final MainClass frame) {
		setSize(1000, 500);
		setBackground(new Color(60,30,5));
		setPreferredSize(new Dimension(1000, 500));

		pockets[0] = new Pocket(30, 30);
		pockets[1] = new Pocket(getWidth()/2-25, 30);
		pockets[2] = new Pocket(getWidth()-50-30, 30);
		pockets[3] = new Pocket(30, getHeight()-50-30);
		pockets[4] = new Pocket(getWidth()/2-25, getHeight()-50-30);
		pockets[5] = new Pocket(getWidth()-50-30, getHeight()-50-30);
		
		
		MyMouseListener mouseListener = new MyMouseListener();
		addMouseListener(mouseListener);
		addMouseMotionListener(mouseListener);
	}
	       
	
	
	public void CustomGameStart() { //temporary

		ballCount = 0;
		MainClass.rightPanel.score1.setText("0");
		MainClass.rightPanel.score2.setText("0");
		balls = new Ball[ballNumber];
		cueBall = addBall(100, getHeight()/2 , 0);
		randomAddAllBalls(ballNumber);
		
		synchronized(table) {
			table.notify();
		}
		repaint();
		
	}
	
	public void StandardGameStart() {
		ballCount = 0;
		MainClass.rightPanel.score1.setText("0");
		MainClass.rightPanel.score2.setText("0");
		balls = new Ball[16];
		cueBall = addBall(getWidth()/4, getHeight()/2, 0);
		start = 0;
		addBall(getWidth()*3/5, getHeight()/2, 1);
		
		addBall(getWidth()*3/5+26, getHeight()/2+15, -1);
		addBall(getWidth()*3/5+26, getHeight()/2-15, 1);
		
		addBall(getWidth()*3/5+52, getHeight()/2, 2);
		addBall(getWidth()*3/5+52, getHeight()/2-30, -1);
		addBall(getWidth()*3/5+52, getHeight()/2+30, 1);
		
		addBall(getWidth()*3/5+78, getHeight()/2-15, 1);
		addBall(getWidth()*3/5+78, getHeight()/2+15, -1);
		addBall(getWidth()*3/5+78, getHeight()/2+45, 1);
		addBall(getWidth()*3/5+78, getHeight()/2-45, -1);
		
		addBall(getWidth()*3/5+104, getHeight()/2, -1);
		addBall(getWidth()*3/5+104, getHeight()/2-30, -1);
		addBall(getWidth()*3/5+104, getHeight()/2+30, 1);
		addBall(getWidth()*3/5+104, getHeight()/2-60, 1);
		addBall(getWidth()*3/5+104, getHeight()/2+60, -1);
		
		
		synchronized(table) {
			table.notify();
		}
		repaint();
	}
	
	public Ball addBall(int x, int y, int ballType)
	{
		Ball ball = new Ball(x, y, ballType);
	
		balls[ballCount] = ball;
		ballCount++;

		return ball;
	}
	
	public void randomAddAllBalls(int ballNumber) {
		Random rand = new Random();
		int x;
		int y;
		int ballType;
		for(int i = 0; i<ballNumber-2; i++) {
			x = rand.nextInt(getWidth()-100-Ball.getDiameter())+50;
			y = rand.nextInt(getHeight()-100-Ball.getDiameter())+50;
			if(i%2==0)
				ballType = 1;
			else
				ballType = -1;
			addBall(x, y, ballType);
		}
		x = rand.nextInt(getWidth()-100-Ball.getDiameter())+50;
		y = rand.nextInt(getHeight()-100-Ball.getDiameter())+50;
		addBall(x, y, 2);
	}
	
	public void deleteAllBalls() {
		balls = null;
		cueBall = null;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(new Color(0, 80, 0));
		g2.fillRect(50, 50, getWidth()-50*2, getHeight()-50*2);
		
		for(Pocket pocket : pockets) {
			pocket.paint(g2);
		}

		for (int i = 0; i < ballCount; i++) {
			balls[i].paint(g2);
		}
		
		if(!areMoving()) {
			g2.setColor(stickColor);
			g2.setStroke(new BasicStroke(stickWidth));
			g2.drawLine(stickX1, stickY1, stickX2, stickY2);
		}
		
		if(areMoving()) {
			g2.setColor(stickColor);
			g2.setStroke(new BasicStroke(stickWidth));
			g2.drawLine(stickX1, stickY1, stickX2, stickY2);
			stickFading();
		}
		
		
	}
	public void stickFading() {
	
		if(System.currentTimeMillis() - start <= 1000) {
		}
		else{
			stickX1 = -50;
			stickX2 = -50;
			stickY1 = -50;
			stickY2 = -50;			
		}

	}
	
	@Override
	public void run()
	{
		previousTime = System.currentTimeMillis();
		long currentTime = previousTime;
		long elapsedTime;
		
		while(true)
		{


			if(!areMoving()) {
				try {

					synchronized(table) {
						table.wait();
					}
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else {
			currentTime = System.currentTimeMillis();
			elapsedTime = (currentTime - previousTime);

			tableUpdate(elapsedTime / 1000f);
			repaint();

			
				try
				{
					Thread.sleep(5);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				previousTime = currentTime;
			}
		}
	}
	
	public void tableUpdate(float time)
	{
		
		for (int i = 0; i < ballCount; i++){
				balls[i].frictionForce();
				balls[i].position.setX(balls[i].position.getX() + (balls[i].velocity.getX() * time));
				balls[i].position.setY(balls[i].position.getY() + (balls[i].velocity.getY() * time));
		}
		collisions();
		
	}
	
	public boolean areMoving() {
		int nMovingBallsNumber = 0;
		for (int i = 0; i < ballCount; i++){
			if(balls[i].velocity.getX() == 0 && balls[i].velocity.getY() == 0)
				nMovingBallsNumber++;
		}
			if(nMovingBallsNumber == ballCount) {
				if(cueBall!= null && cueBall.position.getX()<-10)
					cueBall.setPosition(new Vector2D(table.getWidth()/4,table.getHeight()/2));
				repaint();
				return false;
			}
			else
				return true;
				
	}

	public void sorting(Comparable[] a)
	{
		 for( int p = 1; p < ballCount; p++ ){
	         Comparable tmp = a[ p ];
	         int j = p;

	         for( ; j>0 && tmp.compareTo(a[j-1]) < 0; j--)
	             a[j] = a[j-1];

	         a[j] = tmp;
		 }
	}


	public void collisions()
	{
		sorting(balls);

		for (int i = 0; i < ballCount; i++)
		{
			//with bands
			float overlap;
			
			if((balls[i].position.getY()+7.5) > (50+25) && (balls[i].position.getY()+7.5) < (getWidth()-50-25)) {
				

				
				if (balls[i].position.getX()< 50){
					
					try {
						ballHit.loadSound("./audio/BandHit.wav");
						ballHit.playSound();
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					balls[i].position.setX(-(balls[i].position.getX()-50)+50);
					
					balls[i].velocity.setX(-(balls[i].velocity.getX()));
					balls[i].velocity.setY(balls[i].velocity.getY());
				}
				else if (balls[i].position.getX() + Ball.getDiameter() > (getWidth() - 50)){
					
					try {
						ballHit.loadSound("./audio/BandHit.wav");
						ballHit.playSound();
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					overlap = balls[i].position.getX() + Ball.getDiameter() - (getWidth() - 50);
					balls[i].position.setX(getWidth() - 50 - Ball.getDiameter() - overlap);
					
					balls[i].velocity.setX(-(balls[i].velocity.getX())); 
					balls[i].velocity.setY((balls[i].velocity.getY()));
				}
			}
			if((balls[i].position.getX()+7.5) > (50+25) && ((balls[i].position.getX()+7.5) < (getWidth()/2-25) || (balls[i].position.getX()+7.5) > (getWidth()/2-25)) && (balls[i].position.getX()+7.5) < (getWidth()-50-25)) {
	

				
				if (balls[i].position.getY() < 50){
					
					try {
						ballHit.loadSound("./audio/BandHit.wav");
						ballHit.playSound();
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
					
					balls[i].position.setY(-(balls[i].position.getY()-50)+50);
					
					balls[i].velocity.setY(-(balls[i].velocity.getY())); 
					balls[i].velocity.setX((balls[i].velocity.getX()));
				}
				else if (balls[i].position.getY() + Ball.getDiameter() > getHeight()-50){
					
					try {
						ballHit.loadSound("./audio/BandHit.wav");
						ballHit.playSound();
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					overlap = balls[i].position.getY() + Ball.getDiameter() - (getHeight() - 50);
					balls[i].position.setY(getHeight() - 50 - Ball.getDiameter() - overlap);
					
					balls[i].velocity.setY(-(balls[i].velocity.getY()));   
					balls[i].velocity.setX((balls[i].velocity.getX()));
				}
			}
			
			//pockets
			for(Pocket pocket : pockets){
				ballToPocket(pocket);
			}

			//ball to ball
			for(int j = i + 1; j < ballCount; j++){
				balls[i].ballToBallCollision(balls[j]);
			}
		}
	}
	
	
	public void ballToPocket(Pocket pocket) {
		
		for(int j = 0; j < ballCount; j++){
			float distanceSq = (balls[j].position.getX()+15-pocket.position.getX()-25)*(balls[j].position.getX()+15-pocket.position.getX()-25)+(balls[j].position.getY()+15-pocket.position.getY()-25)*(balls[j].position.getY()+15-pocket.position.getY()-25);
			if(distanceSq<25*25){
			
				balls[j].setPosition(new Vector2D(-50,-50));
				balls[j].setVelocity(new Vector2D());
				
				try {
					ballHit.loadSound("./audio/pocket.wav");
					ballHit.playSound();
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(balls[j].ballType == -1)
					MainClass.rightPanel.score1.setText(Integer.toString(Integer.parseInt(MainClass.rightPanel.score1.getText())+1));
				else if(balls[j].ballType == 1)
					MainClass.rightPanel.score2.setText(Integer.toString(Integer.parseInt(MainClass.rightPanel.score2.getText())+1));
				else if(balls[j].ballType == 2) {
					JOptionPane.showMessageDialog(MainClass.table, MainClass.blackball_pocketed, "", JOptionPane.INFORMATION_MESSAGE);
					ballCount = 0;
					deleteAllBalls();
				}
			}
		}
	}
	
	class MyMouseListener extends MouseInputAdapter {
		public void mouseMoved(MouseEvent e) {
			if(!areMoving()) {
				if(cueBall==null) return;

		        strike.setX((float) (e.getX()-(cueBall.position.getX()+Ball.getRadius())));
	        	strike.setY((float) (e.getY()-(cueBall.position.getY()+Ball.getRadius())));
	        	strike.normalize();
	        	
	        	direction = strike;
	        	gap = direction.multiply(-15-MainClass.rightPanel.getSliderValue());
	        	stickVec = direction.multiply(-250-MainClass.rightPanel.getSliderValue());
	        	
	        	stickX1=(int) (cueBall.position.getX()+15+gap.getX());
		        stickY1=(int) (cueBall.position.getY()+15+gap.getY());

		        stickX2=(int) (cueBall.position.getX()+15+stickVec.getX());
		        stickY2=(int) (cueBall.position.getY()+15+stickVec.getY());
			}
			
        }
		   
        public void mousePressed(MouseEvent e) {
        	if(!areMoving()) {	       		
	        	if(cueBall==null) return;
	        	
	        	start = System.currentTimeMillis();
	        	
	        	gap = direction.multiply(-15);
	        	stickVec = direction.multiply(-250);
	        	
	        	stickX2=(int) (cueBall.position.getX()+15+stickVec.getX());
			    stickY2=(int) (cueBall.position.getY()+15+stickVec.getY());
			        
	        	stickX1=(int) (cueBall.position.getX()+15+gap.getX());
		        stickY1=(int) (cueBall.position.getY()+15+gap.getY());
		        
        		cueBall.setVelocity(strike.multiply(MainClass.rightPanel.powerSlider.getValue()*25));
        		
        		
    			try {
    				ballHit.loadSound("./audio/BallHit.wav");
    				ballHit.playSound();
    			} catch (Throwable ex) {
    				// TODO Auto-generated catch block
    				ex.printStackTrace();
    			}
    		
        		
	        	previousTime = System.currentTimeMillis();
	        	
        		synchronized(table) {
	    			table.notify();
	    		}
        	}
        }
		 

	}
}
	