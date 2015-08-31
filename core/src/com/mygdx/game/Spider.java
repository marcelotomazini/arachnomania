package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameScreen.GameState;

public class Spider {

	private static final float SPIDER_START_Y = 240;
	private static final float SPIDER_START_X = 50;

	private static final float PLANE_JUMP_IMPULSE = 350;
	private static final float PLANE_VELOCITY_X = 200;

	private static final float GRAVITY = -10;
	
	private final Vector2 spiderVelocity = new Vector2();
	private final Vector2 spiderPosition = new Vector2();
	private final Vector2 gravity = new Vector2();
	
	private Animation spider;
	
	public Spider() {
		Texture frame1 = new Texture("spider1.png");
		Texture frame2 = new Texture("spider2.png");
		Texture frame3 = new Texture("spider3.png");

		frame1.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		spider = new Animation(0.15f, new TextureRegion(frame1),
				new TextureRegion(frame2), new TextureRegion(frame3),
				new TextureRegion(frame2));
		spider.setPlayMode(PlayMode.LOOP_RANDOM);
	}

	public TextureRegion getKeyFrame(float planeStateTime) {
		return spider.getKeyFrame(planeStateTime);
	}

	public void reset() {
		spiderPosition.set(SPIDER_START_X, SPIDER_START_Y);
		spiderVelocity.set(0, 0);
		gravity.set(0, GRAVITY);
	}

	public void touched(Camera camera) {
		Vector3 worldCoordinates = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(),0));
		if(worldCoordinates.x > spiderPosition.x + spider.getKeyFrames()[0].getRegionWidth() / 2)
			spiderVelocity.set(PLANE_VELOCITY_X, PLANE_JUMP_IMPULSE);
		else
			spiderVelocity.set(-PLANE_VELOCITY_X, PLANE_JUMP_IMPULSE);		
	}

	public GameState update(GameState gameState, OrthographicCamera camera, Viewport viewport) {
		if (gameState != GameState.Start)
			spiderVelocity.add(gravity);
		
		spiderPosition.mulAdd(spiderVelocity, 0.01f);
		
		float left = 0;
		float bottom = 0;
		float top = viewport.getWorldHeight() - spider.getKeyFrames()[0].getRegionHeight();
	    float right = viewport.getWorldWidth() - spider.getKeyFrames()[0].getRegionWidth();
	    
		if(spiderPosition.x < left)
			spiderPosition.x = left;
		
		if(spiderPosition.x > right)
			spiderPosition.x = right;
		
		if(spiderPosition.y > top)
			spiderPosition.y = top;

		if(spiderPosition.y < bottom)
			return GameState.GameOver;
		
		return gameState;
	}
	
	public float getScaledY(float y, Viewport viewport) {
		 return y * viewport.getWorldHeight() / viewport.getScreenHeight();
	}

	public float getY() {
		return spiderPosition.y;
	}

	public float getX() {
		return spiderPosition.x;
	}
	
	public float getWidth() {
		return spider.getKeyFrames()[0].getRegionWidth();
	}

	public float getHeight() {
		return spider.getKeyFrames()[0].getRegionHeight();
	}
}
