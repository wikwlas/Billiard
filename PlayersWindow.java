package Billiard3;


import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PlayersWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String choosePlayer = "Wybierz Gracza";
	public JLabel label2;
	public JPanel panel3;
	public JPanel panel4;
	public JTextField textField1;
	public JTextField textField2;
	public JButton start;
	public PlayersWindow(final RightPanel rightPanel, final Table table) {
		setSize(300, 180);
		setResizable(false);
		setLocation(table.getWidth()/2-this.getSize().width/2, table.getHeight()/2-this.getSize().height/2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		setLayout(new GridLayout(3, 0));
		setTitle(choosePlayer);

		label2 = new JLabel("Wprowadz nazwy graczy: (max 7 znaków)");
		label2.setHorizontalAlignment(JLabel.CENTER);
		add(label2);
		panel3 = new JPanel();
		panel3.setLayout(new GridLayout(0, 2));
		textField1 = new JTextField("GRACZ 1");
		textField1.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) { 
		        if (textField1.getText().length() >= 7) // limit textfield to 5 characters
		            e.consume(); 
		    }  
		});
		textField2 = new JTextField("GRACZ 2");
		textField2.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) { 
		        if (textField2.getText().length() >= 7) // limit textfield to 5 characters
		            e.consume(); 
		    }  
		});
		panel3.add(textField1);
		panel3.add(textField2);
		add(panel3);
		panel4 = new JPanel();
		panel4.setLayout(new FlowLayout());
		start = new JButton("START");
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rightPanel.player1.setText(textField1.getText());
				rightPanel.player2.setText(textField2.getText());
				if (table.typeOfGame == "default") {
					table.StandardGameStart();
				} else {
					table.CustomGameStart();
				}
				dispose();
				setVisible(false);
				
				
			}
		}); 
		panel4.add(start);
		add(panel4);

	}
	
}
