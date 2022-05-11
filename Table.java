package bilard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JPanel;

public class Table extends JPanel implements Runnable {
	
	Table table = this;
	int ballNumber = 10;
	private Ball[] balls = new Ball[ballNumber];
	int ballCount = 0;
	
	private Vector2D strike = new Vector2D();
	private double X = 0;
	private double Y = 0;
	
	Ball cueBall;

	public Table() {
		setSize(912, 456);
		setBackground(new Color(0,80,0));
//		setPreferredSize(new Dimension(912, 456));
		setPreferredSize(new Dimension(600, 400));

		
		addMouseListener(new MouseAdapter() {
	        public void mousePressed(MouseEvent e) {
	        	
	        	if(cueBall==null) return;
	        	
	        	X = e.getX();
	        	Y = e.getY();  
	        	strike.setX((float) (X-(cueBall.position.getX()+Ball.getRadius())));
	        	strike.setY((float) (Y-(cueBall.position.getY()+Ball.getRadius())));
	        	strike.normalize();

	        	cueBall.setVelocity(strike.multiply(1000));
	        }
	    });
	}
	
	
	public void CustomGameStart() { //temporary
		cueBall = addBall(100, getHeight()/2 , 0);
		randomAddAllBalls(ballNumber);
	}
	
	public void StandardGameStart() {
		//not-yet
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
		for(int i = 0; i<ballNumber-1; i++) {
			x = rand.nextInt(getWidth()-Ball.getDiameter());
			y = rand.nextInt(getHeight()-Ball.getDiameter());
			if(i%2==0)
				ballType = 1;
			else
				ballType = -1;
			addBall(x, y, ballType);
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (int i = 0; i < ballCount; i++) {
			balls[i].paint(g);
		}
	}
	
	@Override
	public void run()
	{
		long previousTime = System.currentTimeMillis();
		long currentTime = previousTime;
		long elapsedTime;

		while(true)
		{
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
	
	public void tableUpdate(float time)
	{

		for (int i = 0; i < ballCount; i++){
			balls[i].frictionForce();
			balls[i].position.setX(balls[i].position.getX() + (balls[i].velocity.getX() * time));
			balls[i].position.setY(balls[i].position.getY() + (balls[i].velocity.getY() * time));
		}
		collisions();
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
			if (balls[i].position.getX()< 0){
				balls[i].position.setX(0);
				balls[i].velocity.setX(-(balls[i].velocity.getX()));
				balls[i].velocity.setY(balls[i].velocity.getY());
			}
			else if (balls[i].position.getX() + Ball.getDiameter() > getWidth()){
				balls[i].position.setX(getWidth() - Ball.getDiameter());		
				balls[i].velocity.setX(-(balls[i].velocity.getX())); 
				balls[i].velocity.setY((balls[i].velocity.getY()));
			}

			if (balls[i].position.getY() < 0){
				balls[i].position.setY(0);				
				balls[i].velocity.setY(-(balls[i].velocity.getY())); 
				balls[i].velocity.setX((balls[i].velocity.getX()));
			}
			else if (balls[i].position.getY() + Ball.getDiameter() > getHeight()){
				balls[i].position.setY(getHeight() - Ball.getDiameter());		
				balls[i].velocity.setY(-(balls[i].velocity.getY()));   
				balls[i].velocity.setX((balls[i].velocity.getX()));
			}

			//ball to ball
			for(int j = i + 1; j < ballCount; j++){
				balls[i].ballToBallCollision(balls[j]);
			}
		}
	}
	
}