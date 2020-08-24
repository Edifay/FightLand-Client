package fight_land.init;

import static fight_land.AssetsContener.assets;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import fight_land.AssetsContener;
import fight_land.frame.FightLandFrame;

public class InitLoading {

	private int loaded = 0;
	private final int atLoad = 8;

	public InitLoading() {
	}

	public void start() {

		new AssetsContener().setThisAssetsAsAssets();

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				ContentLoading pan = new ContentLoading();
				FightLandFrame.frame.setContentPane(pan);
				FightLandFrame.frame.revalidate();
				FightLandFrame.frame.repaint();
			}
		});

		load();
	}

	public int getLoaded() {
		return loaded;
	}

	private int load() {
		try {
			assets.getBackgrounds()
					.add(ImageIO.read(getClass().getResource("/assets/menu/backgrounds/background_1.png")));
			loaded++;
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < 6; i++) {
			try {
				assets.getIconHero()
						.add(ImageIO.read(getClass().getResource("/assets/lobby/icons/hero" + (i + 1) + ".png")));
				loaded++;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	public int getAtLoad() {
		return atLoad;
	}
}
