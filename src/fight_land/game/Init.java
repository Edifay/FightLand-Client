package fight_land.game;

import static fight_land.frame.FightLandFrame.frame;

import fight_land.game.load.GraphicsLoad;
import fight_land.game.load.LoadGame;
import fight_land.game.loop.Game;
import fight_land.game.network.NetWorkManager;
import fight_land.game.render.GraphicsRender;
import networkAPI.Communication;

public class Init {

	public static GraphicsRender render;
	public static Game game;
	public static NetWorkManager netManager;
	public static LoadGame loader;

	public static void init(Communication com) {

		netManager = new NetWorkManager(com);
		
		render = new GraphicsRender();// create render
		render.create();
		
		game = new Game(render);// create game
		
		loader = new LoadGame();
		frame.setContentPane(new GraphicsLoad(loader));
		frame.revalidate();
		frame.repaint();
		
		while(!loader.getLoadFinish()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		fight_land.lobby.Init.dispose();// delete LobbyRessources

		frame.setContentPane(render.getContent());// set graphics
		frame.revalidate();
		frame.repaint();
		
		System.out.println("wait start by server");

		game.start();
	}
}