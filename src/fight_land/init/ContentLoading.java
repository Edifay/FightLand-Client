package fight_land.init;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ContentLoading extends JPanel {

	private static final long serialVersionUID = -2780082303742485350L;

	private BufferedImage bufferResizer;
	private Graphics gBufferResizer;

	public ContentLoading() {
		this.setLayout(null);
		this.bufferResizer = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB);
		this.gBufferResizer = this.bufferResizer.createGraphics();
		
		JLabel text = new JLabel("LOADING");
		text.setLocation((int)(1000*((double)fight_land.frame.FightLandFrame.frame.getWidth()/(double)1920)), (int)(500*((double)fight_land.frame.FightLandFrame.frame.getHeight()/(double)1080)));
		text.setSize((int)(300*((double)fight_land.frame.FightLandFrame.frame.getWidth()/(double)1920)), (int)(200*((double)fight_land.frame.FightLandFrame.frame.getHeight()/(double)1080)));
		text.setFont(new Font("", 1, 45));
		this.add(text);
        this.addComponentListener((ComponentListener) new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
            	text.setLocation((int)(850*((double)fight_land.frame.FightLandFrame.frame.getWidth()/(double)1920)), (int)(350*((double)fight_land.frame.FightLandFrame.frame.getHeight()/(double)1080)));
        		text.setSize((int)(300*((double)fight_land.frame.FightLandFrame.frame.getWidth()/(double)1920)), (int)(200*((double)fight_land.frame.FightLandFrame.frame.getHeight()/(double)1080)));
            }
        });
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.gBufferResizer.setColor(Color.white);
		this.gBufferResizer.fillRect(0, 0, 1920, 1080);

		this.gBufferResizer.setColor(Color.black);
		this.gBufferResizer.fillRoundRect(4, 1060,
				(int) ((double) 1912 * ((double) Init.getLoaded() / (double) Init.getAtLoad())), 30, 20, 20);

		this.gBufferResizer.drawRoundRect(4, 1060, 1912, 30, 20, 20);
		
		g.drawImage(this.bufferResizer, 0, 0, this.getWidth(), this.getHeight(), null);
	}
}