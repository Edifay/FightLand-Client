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

	public static void init(Communication com) {

//		Sender sender = new Sender(com);
//		netManager = new NetWorkManager(sender, new Receiver(com, sender));

		LoadGame loader = new LoadGame();
		frame.setContentPane(new GraphicsLoad(loader));
		frame.revalidate();
		frame.repaint();
		loader.start();

		fight_land.lobby.Init.dispose();// delete LobbyRessources

		render = new GraphicsRender();// create render
		render.create();

		frame.setContentPane(render.getContent());// set graphics
		frame.revalidate();
		frame.repaint();

		game = new Game(render);

		System.out.println("wait start by server");

		game.start();
	}
}