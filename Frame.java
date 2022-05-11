package bilard;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Frame extends JFrame{
	
	static String title = "Bilard";
	
	RightPanel rightPanel;
	JPanel table;

	public Frame() {
		setSize(1400,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		setLayout(new BorderLayout());
//		setResizable(false);
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
	}
	
	
}
