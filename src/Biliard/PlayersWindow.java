package Biliard;


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
	public JPanel panel3;
	public JPanel panel4;
	public JPanel panel5;
	public JTextField textField1;
	public JTextField textField2;
	public JButton start;
	public PlayersWindow(final RightPanel rightPanel, final Table table, JLabel label1, JLabel label2, JLabel label3, String choosePlayer) {
		setSize(300, 180);
		setResizable(false);
		setLocation(table.getWidth()/2-this.getSize().width/2, table.getHeight()/2-this.getSize().height/2);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);	
		setLayout(new GridLayout(4, 0));
		setTitle(choosePlayer);


		label1.setHorizontalAlignment(JLabel.CENTER);
		add(label1);
		
		panel3 = new JPanel();
		panel3.setLayout(new GridLayout(0, 2));
		
		label2.setHorizontalAlignment(JLabel.CENTER);
		panel3.add(label2);

		label3.setHorizontalAlignment(JLabel.CENTER);
		panel3.add(label3);
		add(panel3);
		panel4 = new JPanel();
		panel4.setLayout(new GridLayout(0, 2));
		textField1 = new JTextField();
		textField1.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) { 
		        if (textField1.getText().length() >= 7) // limit textfield to 5 characters
		            e.consume(); 
		    }  
		});
		textField2 = new JTextField();
		textField2.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) { 
		        if (textField2.getText().length() >= 7) // limit textfield to 5 characters
		            e.consume(); 
		    }  
		});
		panel4.add(textField1);
		panel4.add(textField2);
		add(panel4);
		panel5 = new JPanel();
		panel5.setLayout(new FlowLayout());
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
		panel5.add(start);
		add(panel5);

	}
	
}
