package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Slipper {

	private static final float SLIPPER_START_Y = 0;
	
	private static final float SLIPPER_JUMP_IMPULSE = 350;
	private static final float SLIPPER_VELOCITY_X = 200;
	
	private static final float GRAVITY = -10;

	private final Vector2 slipperPosition = new Vector2();
	private final Vector2 slipperVelocity = new Vector2();
	private final Vector2 gravity = new Vector2();
	
	private Animation slipper;
	private boolean stopUpdate = false;
	
	public Slipper() {
		Texture frame1 = new Texture("slipper1.png");
		Texture frame2 = new Texture("slipper2.png");
		Texture frame3 = new Texture("slipper3.png");
		Texture frame4 = new Texture("slipper4.png");

		frame1.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		slipper = new Animation(0.15f, new TextureRegion(frame1),
				new TextureRegion(frame2), new TextureRegion(frame3),
				new TextureRegion(frame4));
		slipper.setPlayMode(PlayMode.LOOP);
	}

	public TextureRegion getKeyFrame(float planeStateTime) {
		return slipper.getKeyFrame(planeStateTime);
	}

	public void reset(float x) {
		slipperPosition.set(x, SLIPPER_START_Y);
		gravity.set(0, GRAVITY);
		stopUpdate = false;
	}

	public void update(Viewport viewport, float x) {
		if(!stopUpdate) {
			float top = viewport.getWorldHeight() - slipper.getKeyFrames()[0].getRegionHeight();
			if(slipperPosition.y > top)
				stopUpdate  = true;
			
			slipperVelocity.set(SLIPPER_VELOCITY_X, SLIPPER_JUMP_IMPULSE);
		}
		
		if(slipperPosition.y < 0)
			reset(x);
		
		slipperVelocity.add(gravity);
		slipperPosition.mulAdd(slipperVelocity, 0.01f);
	}

	public float getY() {
		return slipperPosition.y;
	}

	public float getX() {
		return slipperPosition.x;
	}
	
	public float getWidth() {
		return slipper.getKeyFrames()[0].getRegionWidth();
	}

	public float getHeight() {
		return slipper.getKeyFrames()[0].getRegionHeight();
	}
}
