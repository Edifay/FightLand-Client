package fight_land.game.listeners;

import static fight_land.game.listeners.KeyBind.DOWN;
import static fight_land.game.listeners.KeyBind.JUMP;
import static fight_land.game.listeners.KeyBind.LEFT;
import static fight_land.game.listeners.KeyBind.RIGHT;
import static fight_land.game.listeners.KeyBind.ROULADE;

import static fight_land.game.listeners.KeyBind.OTHER_DOWN;
import static fight_land.game.listeners.KeyBind.OTHER_JUMP;
import static fight_land.game.listeners.KeyBind.OTHER_LEFT;
import static fight_land.game.listeners.KeyBind.OTHER_RIGHT;
import static fight_land.game.listeners.KeyBind.OTHER_ROULADE;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import fight_land.game.loop.Game;

public class GameListeners implements KeyListener, MouseListener {

	private Game game;

	public GameListeners(Game game) {
		this.game = game;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(e.getKeyCode());

		if (e.getKeyCode() == JUMP) {
			this.game.setJUMP(true);
		} else if (e.getKeyCode() == DOWN) {
			this.game.setDOWN(true);
		} else if (e.getKeyCode() == RIGHT) {
			this.game.setRIGHT(true);
		} else if (e.getKeyCode() == LEFT) {
			this.game.setLEFT(true);
		} else if (e.getKeyCode() == ROULADE) {
			this.game.setROULADE(true);
		} else

		if (e.getKeyCode() == OTHER_JUMP) {
			this.game.setOtherJUMP(true);
		} else if (e.getKeyCode() == OTHER_DOWN) {
			this.game.setOtherDOWN(true);
		} else if (e.getKeyCode() == OTHER_RIGHT) {
			this.game.setOtherLEFT(true);
		} else if (e.getKeyCode() == OTHER_LEFT) {
			this.game.setOtherRIGHT(true);
		} else if (e.getKeyCode() == OTHER_ROULADE) {
			this.game.setOtherROULADE(true);
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == JUMP) {
			this.game.setJUMP(false);
		} else if (e.getKeyCode() == DOWN) {
			this.game.setDOWN(false);
		} else if (e.getKeyCode() == RIGHT) {
			this.game.setRIGHT(false);
		} else if (e.getKeyCode() == LEFT) {
			this.game.setLEFT(false);
		} else if (e.getKeyCode() == ROULADE) {
			this.game.setROULADE(false);
		} else

		if (e.getKeyCode() == OTHER_JUMP) {
			this.game.setOtherJUMP(false);
		} else if (e.getKeyCode() == OTHER_DOWN) {
			this.game.setOtherDOWN(false);
		} else if (e.getKeyCode() == OTHER_RIGHT) {
			this.game.setOtherLEFT(false);
		} else if (e.getKeyCode() == OTHER_LEFT) {
			this.game.setOtherRIGHT(false);
		}else if(e.getKeyCode() == OTHER_ROULADE) {
			this.game.setOtherROULADE(false);
		}
	}
}
