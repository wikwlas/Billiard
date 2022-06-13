package Billiard3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainClass extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static RightPanel rightPanel;
	static JPanel midPanel;
	static Table table;
	
	public JMenuBar menuBar;
	public JMenu menuLine1;
	public JMenu menuLine2;
	public JMenu menuLine3;
	public JMenu menuLine4;
	public JMenu menuLine5;
	public JMenuItem menuItem11;
	public JMenuItem menuItem12;
	public JMenuItem menuItem41;
	public JMenuItem menuItem42;
	public JMenuItem menuItem51;
	public JMenuItem menuItem52;
	
	public String new_game;	
	public String save;	
	public String open;	
	public String change_color;
	public String language;
	public String default_game;
	public String custom_game;
	public String ttable;
	public String stick;
	public int flag = 0;
	public String choose_color = "Wybierz kolor";
	public static final int WIDTH = 1400;
	public static final int HEIGHT = 600;
	PlayersWindow playersWindow;
	public MainClass() {
		new_game = "Nowa gra";	
		save = "Zapisz";	
		open = "Otwórz";	
		change_color = "Zmiana koloru";
		language = "Język/Language";
		default_game = "Domyślna";
		custom_game = "Niestandardowa";
		ttable = "Stół";
		stick = "Kij";
		
		setSize(WIDTH,HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		setLayout(new BorderLayout());
		setTitle("Billiard");
		
		menuBar = new JMenuBar();
		menuLine1 = new JMenu(new_game);
		menuLine2 = new JMenu(save);
		menuLine3 = new JMenu(open);
		menuLine4 = new JMenu(change_color);
		menuLine5 = new JMenu(language);
		
		menuItem11 = new JMenuItem(default_game);
		menuItem11.setActionCommand("default");
		menuItem11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Removes shutter issue (check method header for details)
				table.typeOfGame = "default";
				playersWindow = new PlayersWindow(rightPanel, table);
				playersWindow.setVisible(true);
			}
		});
		menuLine1.add(menuItem11);
		menuLine1.addSeparator();
		
		
		menuItem12 = new JMenuItem(custom_game);
		menuItem12.setActionCommand("custom");
		menuItem12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Removes shutter issue (check method header for details)
				table.typeOfGame = "custom";
				playersWindow = new PlayersWindow(rightPanel, table);
				playersWindow.setVisible(true);
			}
		});

		menuLine1.add(menuItem12);
		
		menuItem41 = new JMenuItem(ttable);
		menuItem41.setActionCommand("ttable");
		menuItem41.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Removes shutter issue (check method header for details)
				table.setBackground(JColorChooser.showDialog(null, choose_color, Color.white));
			}
		});
		menuLine4.add(menuItem41);
		menuLine4.addSeparator();
		
		
		menuItem42 = new JMenuItem(stick);
		menuItem42.setActionCommand("stick");
		menuItem42.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Removes shutter issue (check method header for details)
				table.stickColor = JColorChooser.showDialog(null, choose_color, Color.white);
			}
		});
		menuLine4.add(menuItem42);
		
		menuItem51 = new JMenuItem("EN");
		menuItem51.setActionCommand("en");
		menuItem51.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	flag = 1;
            	ChangeLanguage();
            }

        });
		
		menuLine5.add(menuItem51);
		menuLine5.addSeparator();
		
		menuItem52 = new JMenuItem("PL");
		menuItem52.setActionCommand("pl");
		menuItem52.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	flag = 0;
            	ChangeLanguage();
            }

        });
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
				
//				midPanel = new JPanel();
//					midPanel.setLayout(new GridBagLayout());
//					midPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
//				frame.add(midPanel, BorderLayout.CENTER);
//				
				table = new Table(frame);
//				midPanel.add(table);
				
				frame.add(table, BorderLayout.CENTER);

				ExecutorService exec = Executors.newFixedThreadPool(2);
				exec.execute(table);
				
				frame.setVisible(true);
				frame.pack();
			}
		});
	}
	public void ChangeLanguage() {
		if (flag == 1) {
			if (rightPanel.name_of_player1 == "GRACZ 1") {
				rightPanel.name_of_player1 = "PLAYER 1";
				rightPanel.player2.setText("PLAYER 1");
			}
			if (rightPanel.name_of_player2 == "GRACZ 2") {
				rightPanel.name_of_player1 = "PLAYER 2";
				rightPanel.player2.setText("PLAYER 2");
			}
			playersWindow.label2.setText("Enter players names: (max 7 chars)");
			rightPanel.powerLabel.setText("POWER");
	    	new_game = "New game";	
	    	save = "Save";	
	    	open = "Open";	
	    	change_color = "Change color";
	    	choose_color = "Choose color";
	    	menuLine1.setText(new_game);
	    	menuLine2.setText(save);
	    	menuLine3.setText(open);
	    	menuLine4.setText(change_color);
	    	
	    	default_game = "Default";
	    	custom_game = "Custom";
	    	ttable = "Table";
	    	stick = "Cue";
	    	menuItem11.setText(default_game);
	    	menuItem12.setText(custom_game);
	    	menuItem41.setText(ttable);
	    	menuItem42.setText(stick);
		} else {
			if (rightPanel.name_of_player1 == "PLAYER 1") {
				rightPanel.name_of_player1 = "GRACZ 1";
				rightPanel.player2.setText("GRACZ 1");
			}
			if (rightPanel.name_of_player2 == "PLAYER 2") {
				rightPanel.name_of_player1 = "GRACZ 2";
				rightPanel.player2.setText("GRACZ 2");
			}
			playersWindow.label2.setText("Wprowadz nazwy graczy: (max 7 znaków)");
			rightPanel.powerLabel.setText("MOC");
	    	new_game = "Nowa gra";	
	    	save = "Zapisz";	
	    	open = "Otwórz";	
	    	change_color = "Zmiana koloru";
	    	choose_color = "Wybierz kolor";
	    	menuLine1.setText(new_game);
	    	menuLine2.setText(save);
	    	menuLine3.setText(open);
	    	menuLine4.setText(change_color);
	    	
	    	default_game = "Domyślna";
	    	custom_game = "Niestandardowa";
	    	ttable = "Stół";
	    	stick = "Kij";
	    	menuItem11.setText(default_game);
	    	menuItem12.setText(custom_game);
	    	menuItem41.setText(ttable);
	    	menuItem42.setText(stick);
		}
		
	}
	
}