package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.ArachnoMania.GameState;

public class Guy {
	
	private static final float GUY_START_Y = 0;
	private static final float GUY_START_X = 50;
	
	private static final float VELOCITY_X = 200;
	
	private final Vector2 guyVelocity = new Vector2();
	private final Vector2 guyPosition = new Vector2();
	
	private Animation guy;

	public Guy() {
//		Texture frame1 = new Texture("guy1.png");
		Texture frame2 = new Texture("guy2.png");
		Texture frame3 = new Texture("guy3.png");
		
		frame2.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		guy = new Animation(0.15f, new TextureRegion(frame2), new TextureRegion(frame3));
		guy.setPlayMode(PlayMode.LOOP_RANDOM);
	}

	public void reset() {
		guyPosition.set(GUY_START_X, GUY_START_Y);
		guyVelocity.set(0, 0);
	}

	public void update(GameState gameState) {
		if (gameState != GameState.Start)
			return;
			
		Random random = new Random(13);
		if(random.nextInt() % 2 == 0)
			guyVelocity.set(VELOCITY_X, 0);
		else
			guyVelocity.set(-VELOCITY_X, 0);
		
//			guyVelocity.add(1, 0);
		
		guyPosition.mulAdd(guyVelocity, 0.03f);
	}

	public TextureRegion getKeyFrame(float planeStateTime) {
		return guy.getKeyFrame(planeStateTime);
	}

	public float getX() {
		return guyPosition.x;
	}

	public float getY() {
		return guyPosition.y;
	}

}
