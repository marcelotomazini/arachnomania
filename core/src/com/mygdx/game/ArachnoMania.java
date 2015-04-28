package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class ArachnoMania extends ApplicationAdapter {
	private static final int BOTTOM_BAR_HEIGHT = 600;
	private static final float PLANE_JUMP_IMPULSE = 350;
	private static final float GRAVITY = -10;
	private static final float PLANE_VELOCITY_X = 200;
	private static final float PLANE_START_Y = 240;
	private static final float PLANE_START_X = 50;
	ShapeRenderer shapeRenderer;
	SpriteBatch batch;
	OrthographicCamera camera;
	OrthographicCamera uiCamera;
	Texture background;
	TextureRegion ground;
	float groundOffsetX = 0;
	TextureRegion ceiling;
	TextureRegion rock;
	TextureRegion rockDown;
	Animation spider;
	TextureRegion ready;
	TextureRegion gameOver;
	BitmapFont font;
	Vector2 spiderPosition = new Vector2();
	Vector2 planeVelocity = new Vector2();
	float planeStateTime = 0;
	Vector2 gravity = new Vector2();
	GameState gameState = GameState.Start;
	int score = 0;
	Rectangle rect1 = new Rectangle();
	Rectangle rect2 = new Rectangle();
	Music music;
	Sound explode;
	private Animation spiderWeb;
	private Vector2 spiderWebPosition = new Vector2();
	private Texture spiderWebFrame;

	@Override
	public void create() {
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		uiCamera = new OrthographicCamera();
		uiCamera.setToOrtho(false, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		uiCamera.update();
		font = new BitmapFont(Gdx.files.internal("arial.fnt"));
		background = new Texture("background.png");
		ground = new TextureRegion(new Texture("ground.png"));
		ceiling = new TextureRegion(ground);
		ceiling.flip(true, true);
		rock = new TextureRegion(new Texture("rock.png"));
		rockDown = new TextureRegion(rock);
		rockDown.flip(false, true);
		createSpider();
		createSpiderWeb();
		ready = new TextureRegion(new Texture("ready.png"));
		gameOver = new TextureRegion(new Texture("gameover.png"));
		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		music.setLooping(true);
		music.play();
		explode = Gdx.audio.newSound(Gdx.files.internal("explode.wav"));
		
		resetWorld();
	}

	private void createSpiderWeb() {
		spiderWebFrame = new Texture("spiderweb.png");
		
		spiderWebFrame.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		spiderWeb = new Animation(0.15f, new TextureRegion(spiderWebFrame));
		spider.setPlayMode(PlayMode.LOOP_RANDOM);
	}

	private void createSpider() {
		Texture frame1 = new Texture("spider1.png");
		Texture frame2 = new Texture("spider2.png");
		Texture frame3 = new Texture("spider3.png");
		
		frame1.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		spider = new Animation(0.15f, new TextureRegion(frame1),
				new TextureRegion(frame2), new TextureRegion(frame3),
				new TextureRegion(frame2));
		spider.setPlayMode(PlayMode.LOOP_RANDOM);
	}

	private void resetWorld() {
		score = 0;
		groundOffsetX = 0;
		spiderPosition.set(PLANE_START_X, PLANE_START_Y);
		spiderWebPosition.set(0, 0);
		planeVelocity.set(0, 0);
		gravity.set(0, GRAVITY);
		camera.position.x = 400;
	}

	private void updateWorld() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		planeStateTime += deltaTime;
		if (Gdx.input.justTouched()) {
			if (gameState == GameState.Start) {
				gameState = GameState.Running;
			}
			if (gameState == GameState.Running) {
				if(Gdx.input.getX() > spiderPosition.x + BOTTOM_BAR_HEIGHT / 2)
					planeVelocity.set(PLANE_VELOCITY_X, PLANE_JUMP_IMPULSE);
				else
					planeVelocity.set(-PLANE_VELOCITY_X, PLANE_JUMP_IMPULSE);
			}
			if (gameState == GameState.GameOver) {
				gameState = GameState.Start;
				resetWorld();
			}
		}
		if (gameState != GameState.Start)
			planeVelocity.add(gravity);
		
		spiderPosition.mulAdd(planeVelocity, 0.01f);
		
		final Rectangle screenBounds = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		float left = screenBounds.getX();
	    float right = left + screenBounds.getWidth();
	    
		if(spiderPosition.x < left)
			spiderPosition.x = left;
		
		if(spiderPosition.x + BOTTOM_BAR_HEIGHT > right)
			spiderPosition.x = right - BOTTOM_BAR_HEIGHT;
		
		if (camera.position.x - groundOffsetX > ground.getRegionWidth() + 400) {
			groundOffsetX += ground.getRegionWidth();
		}
		rect1.set(spiderPosition.x + 20, spiderPosition.y,
				spider.getKeyFrames()[0].getRegionWidth() - 20,
				spider.getKeyFrames()[0].getRegionHeight());
		
		if (spiderPosition.y < ground.getRegionHeight() - 20
				|| spiderPosition.y + spider.getKeyFrames()[0].getRegionHeight() > 480 - ground
						.getRegionHeight() + 20) {
			if (gameState != GameState.GameOver)
				explode.play();
			gameState = GameState.GameOver;
			planeVelocity.x = 0;
		}
	}

	private void drawWorld() {
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(background, 0, 0);
		batch.draw(ground, groundOffsetX, 0);
		batch.draw(ground, groundOffsetX + ground.getRegionWidth(), 0);
		batch.draw(ceiling, groundOffsetX, 480 - ceiling.getRegionHeight());
		batch.draw(ceiling, groundOffsetX + ceiling.getRegionWidth(), 480 - ceiling.getRegionHeight());
		batch.draw(spider.getKeyFrame(planeStateTime), spiderPosition.x, spiderPosition.y);
		
//		if(spiderWebPosition.x > 0 && spiderWebPosition.y > 0) {
			
//			if (Gdx.input.justTouched()) {
				spiderWebPosition.x = spiderPosition.x + spider.getKeyFrames()[0].getRegionWidth() / 2 - 25;
				spiderWebPosition.y = spiderPosition.y + spider.getKeyFrames()[0].getRegionHeight() - 21;
				
				batch.draw(
						spiderWeb.getKeyFrames()[0], 
						spiderWebPosition.x, 
						spiderWebPosition.y - 10,
						spiderWebPosition.x, 
						spiderWebPosition.y - 10,
						spiderWebFrame.getWidth(),
						calculateHeight(),
						1,
						1,
						calculateAngle());
//			} else {
//				spiderWebPosition.x = spiderWebPosition.x + 10;
//				spiderWebPosition.y = spiderWebPosition.y + 10;
//			}
//		}
		
		batch.end();
		batch.setProjectionMatrix(uiCamera.combined);
		batch.begin();
		if (gameState == GameState.Start) {
			batch.draw(ready,
					Gdx.graphics.getWidth() / 2 - ready.getRegionWidth() / 2,
					Gdx.graphics.getHeight() / 2 - ready.getRegionHeight() / 2);
		}
		if (gameState == GameState.GameOver) {
			batch.draw(
					gameOver,
					Gdx.graphics.getWidth() / 2 - gameOver.getRegionWidth() / 2,
					Gdx.graphics.getHeight() / 2 - gameOver.getRegionHeight()
							/ 2);
		}
		if (gameState == GameState.GameOver || gameState == GameState.Running) {
			font.draw(batch, "" + score, Gdx.graphics.getWidth() / 2,
					Gdx.graphics.getHeight() - 60);
		}
		batch.end();
	}

	private float calculateAngle() {
		for(int i = 1; i < 75; i++) {
			float x = 3 / i;
		}
		
		
		return 45;
	}

	private float calculateHeight() {
		final Rectangle screenBounds = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		float ret = screenBounds.getHeight() - spiderPosition.y;
		return ret > screenBounds.getHeight() ? 0 : ret;
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		updateWorld();
		drawWorld();
	}

	static class Rock {
		Vector2 position = new Vector2();
		TextureRegion image;
		boolean counted;

		public Rock(float x, float y, TextureRegion image) {
			this.position.x = x;
			this.position.y = y;
			this.image = image;
		}
	}

	static enum GameState {
		Start, Running, GameOver
	}
}