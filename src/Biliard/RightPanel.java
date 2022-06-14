package Biliard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RightPanel  extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel player1, player2, powerLabel;
	JTextField score1, score2, powerValue;
	JSlider powerSlider;
	Box p1Info, p2Info;

	String power = "MOC";
	static final int SLIDER_MIN = 0;
	static final int SLIDER_MAX = 100;
	static final int SLIDER_INIT = 100;
	
	static String title = "Biliard";
	
	public RightPanel() throws HeadlessException
	{	
		setPreferredSize(new Dimension(150, this.getHeight()));
		setBackground(Color.WHITE);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(Box.createRigidArea(new Dimension(0,20)));
		
		p1Info = new Box(BoxLayout.X_AXIS);
		p1Info.setPreferredSize(new Dimension(100, 30));
		p1Info.setMaximumSize(new Dimension(100,30));
		
		add(p1Info, BoxLayout.Y_AXIS);
		
		add(Box.createRigidArea(new Dimension(0,5)));
		
		player1 = new JLabel();
		player1.setPreferredSize(new Dimension(60, 25));
		p1Info.add(player1);
		
		p1Info.add(Box.createRigidArea(new Dimension(10,0)));
		
		score1 = new JTextField("0");
		score1.setHorizontalAlignment(SwingConstants.CENTER);
		score1.setEditable(false);
		p1Info.add(score1);
		
		p2Info = new Box(BoxLayout.X_AXIS);
		p2Info.setPreferredSize(new Dimension(this.getWidth(), 30));
		p2Info.setMaximumSize(new Dimension(100,30));
		add(p2Info);
		
		player2 = new JLabel();
		player2.setPreferredSize(new Dimension(60, 25));
		p2Info.add(player2);
		
		p2Info.add(Box.createRigidArea(new Dimension(10,0)));
		
		score2 = new JTextField("0");
		score2.setHorizontalAlignment(SwingConstants.CENTER);
		score2.setEditable(false);
		p2Info.add(score2);
		
		add(Box.createRigidArea(new Dimension(0,20)));
		
		powerLabel = new JLabel(power);
		powerLabel.setFont(new Font("Serif", Font.PLAIN, 30));
		powerLabel.setAlignmentX(CENTER_ALIGNMENT);
		add(powerLabel);
		
		add(Box.createRigidArea(new Dimension(0,10)));
		
		powerValue = new JTextField(Integer.toString(SLIDER_INIT));
		powerValue.setPreferredSize(new Dimension(50,30));
		powerValue.setMaximumSize(new Dimension(50,30));
		powerValue.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				powerSlider.setValue(Integer.parseInt(powerValue.getText()));
				
			}
			
		});
		powerValue.setHorizontalAlignment(SwingConstants.CENTER);
		add(powerValue);
		
		add(Box.createRigidArea(new Dimension(0,5)));

		powerSlider = new JSlider(JSlider.VERTICAL, SLIDER_MIN, SLIDER_MAX, SLIDER_INIT);
		powerSlider.setPreferredSize(new Dimension(60, 250));
		powerSlider.setPaintTicks(true);
		powerSlider.setPaintLabels(true);
		powerSlider.setMinorTickSpacing(5);
		powerSlider.setMajorTickSpacing(20);
		powerSlider.setBackground(Color.WHITE);
		powerSlider.addChangeListener(new SliderChangeListener());
		add(powerSlider);
		
		add(Box.createRigidArea(new Dimension(0,20)));
	}
	
    public class SliderChangeListener implements ChangeListener{
		
		@Override
		public void stateChanged(ChangeEvent arg0) {
			String value = String.format("%d", powerSlider.getValue());
			powerValue.setText(value);
		}
    }
    
    public int getSliderValue() {
    	return powerSlider.getValue();
    }

}