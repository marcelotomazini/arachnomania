package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class ArachnoMania extends Game {

	public Texture background;
	Sound gameOver; 
	
	private MenuScreen menuScreen;
	private GameScreen gameScreen;

	@Override
	public void create() {
		background = new Texture("background.png");
		
		gameOver = Gdx.audio.newSound(Gdx.files.internal("slaphard.wav"));
		
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		
		setScreen(menuScreen);
	}
	
	public void setGameScreen() {
		setScreen(gameScreen);
	}

	public void setMenuScreen() {
		gameOver.play();
		setScreen(menuScreen);
	}
}