package bilard;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainClass {

	static Frame frame;
	static RightPanel rightPanel;
	static JPanel midPanel;
	static Table table;

	
	
	public MainClass() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frame = new Frame();	
				
				rightPanel = new RightPanel();
				
				frame.rightPanel = new RightPanel();
				
				frame.add(rightPanel, BorderLayout.LINE_END);
				
				midPanel = new JPanel();
					midPanel.setLayout(new GridBagLayout());
					midPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
				frame.add(midPanel, BorderLayout.CENTER);
				
				table = new Table();
				Ball ball1 = table.addBall(300, 300);
				Ball ball2 = table.addBall(200, 100);
				Ball ball3 = table.addBall(40, 100);
				midPanel.add(table);

				ExecutorService exec = Executors.newFixedThreadPool(2);
				exec.execute(table);
//				exec.execute(pr);
				frame.setVisible(true);
			}
		});
	}

}
