package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;

public class ArachnoMania extends Game {

	public Texture background;
	
	private MenuScreen menuScreen;
	private GameScreen gameScreen;

	@Override
	public void create() {
		background = new Texture("background.png");
		
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		
		setScreen(menuScreen);
	}
	
	public void setGameScreen() {
		setScreen(gameScreen);
	}

	public void setMenuScreen() {
		setScreen(menuScreen);
	}
}