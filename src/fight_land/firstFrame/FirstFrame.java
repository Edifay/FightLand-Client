package fight_land.firstFrame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import fight_land.main.Main;

public class FirstFrame {
	
	private static Boolean next = false;

	public static void openFirstFrame() {
		
		JFrame frame = new JFrame("OptionFrame");
		frame.setSize(480, 480);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lab = new JLabel();
		lab.setText("Pseudo:");
		lab.setBounds(20, 20, 60, 30);
		frame.add(lab);
		
		JTextField text = new JTextField();
		text.setBounds(80, 25, 120, 20);
		frame.add(text);
		
		JButton button = new JButton();
		button.setText("OK");
		button.setBounds(200, 400, 60, 40);
		button.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				next = true;
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		frame.add(button);
		
		frame.setVisible(true);
		do {
			while(!next) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			next = false;
			if(text.getText().length() <= 0 || text.getText().length() > 16) {
				JDialog dialog = new JDialog();
				dialog.setSize(200, 200);
				dialog.setLocationRelativeTo(frame);
				JLabel labdiag = new JLabel("ERROR : PSEUDO size 1 - 16.");
				dialog.add(labdiag);
				dialog.setVisible(true);
			}
		}while(text.getText().length() <= 0 || text.getText().length() > 16);
		Main.name = text.getText();
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.dispose();
	}
}
