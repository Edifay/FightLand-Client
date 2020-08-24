package fight_land.frame;

import javax.swing.JFrame;

public class FightLandFrame extends JFrame {

	private static final long serialVersionUID = 993565244129171423L;

	public static FightLandFrame frame;

// refresh frame
	private Boolean refresh;
	private long nanoRefresh;
	private Thread tRefresh;

	public FightLandFrame() {
		this.tRefresh = new Thread();
		this.nanoRefresh = 100000;
		this.refresh = false;

	}

	public synchronized void setThisFrameAsFrame() {
		frame = this;
	}

	public synchronized void setRefresh(Boolean bool) {
		if (bool) {
			this.refresh = bool;
			if (!this.tRefresh.isAlive()) {
				this.tRefresh = new Thread(() -> {
					while (this.refresh) {
						this.repaint();
						try {
							Thread.sleep((int) (this.nanoRefresh / 1000000), (int) (this.nanoRefresh % 1000000));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				});
				this.tRefresh.start();
			}
		} else {
			this.refresh = false;
		}
	}

	public long getNanoRefresh() {
		return nanoRefresh;
	}

	public void setNanoRefresh(long nanoRefresh) {
		this.nanoRefresh = nanoRefresh;
	}

	public static FightLandFrame getFrame() {
		return frame;
	}
}
