package bilard;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainClass extends JFrame{

	static RightPanel rightPanel;
	static JPanel midPanel;
	static Table table;
		

	public static final int WIDTH = 1400;
	public static final int HEIGHT = 600;
	
	public MainClass() {
		setSize(WIDTH,HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		setLayout(new BorderLayout());
		setResizable(false);
		setTitle("Billiard");
		
		JMenuBar menuBar = new JMenuBar();
		//UIManager.put("MenuBar.background", Color.DARK_GRAY);
		JMenu menuLine1 = new JMenu("Nowa gra");
		JMenu menuLine2 = new JMenu("Zapisz");
		JMenu menuLine3 = new JMenu("Otwórz");
		JMenu menuLine4 = new JMenu("Zmiana koloru");
		JMenu menuLine5 = new JMenu("Język");
		
		JMenuItem menuItem11 = new JMenuItem("Domyślna");
		menuItem11.setActionCommand("default");
//		menuItem11.addActionListener(this);
		menuLine1.add(menuItem11);
		menuLine1.addSeparator();
		
		
		JMenuItem menuItem12 = new JMenuItem("Niestandardowa");
		menuItem12.setActionCommand("custom");
		menuItem12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Removes shutter issue (check method header for details)
				table.CustomGameStart();

			}
		});
//		menuItem12.addActionListener(this);
		menuLine1.add(menuItem12);
		
		JMenuItem menuItem51 = new JMenuItem("EN");
		menuItem51.setActionCommand("en");
//		menuItem51.addActionListener(this);
		menuLine5.add(menuItem51);
		menuLine5.addSeparator();
		
		JMenuItem menuItem52 = new JMenuItem("PL");
		menuItem52.setActionCommand("pl");
//		menuItem52.addActionListener(this);
		menuLine5.add(menuItem52);
		
		
		menuBar.add(menuLine1);
		menuBar.add(menuLine2);
		menuBar.add(menuLine3);
		menuBar.add(menuLine4);
		menuBar.add(menuLine5);
		
		setJMenuBar(menuBar);	
		// TODO Auto-generated constructor stub
	}
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainClass frame = new MainClass();
				
				rightPanel = new RightPanel();
				frame.add(rightPanel, BorderLayout.LINE_END);
				
				midPanel = new JPanel();
					midPanel.setLayout(new GridBagLayout());
					midPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
				frame.add(midPanel, BorderLayout.CENTER);
				
				table = new Table();
				midPanel.add(table);

				ExecutorService exec = Executors.newFixedThreadPool(2);
				exec.execute(table);
				
				frame.setVisible(true);
			}
		});
	}
}
