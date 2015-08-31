package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;

public class ArachnoMania extends Game {

	public Texture background;
	
	@Override
	public void create() {
		background = new Texture("background.png");
		
		new MenuScreen(this);
	}
	
	
}