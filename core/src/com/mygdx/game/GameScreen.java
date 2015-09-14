package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen extends ScreenAdapter {

	private Spider spider;
	private Guy guy;
	private Slipper slipper;
	
	
	SpriteBatch batch;
	OrthographicCamera camera;
	Viewport viewport;

	float groundOffsetX = 0;
	TextureRegion ready;
	TextureRegion gameOver;
	BitmapFont font;
	
	
	float deltaTime = 0;
	
	GameState gameState = GameState.Start;
	int score = 0;
	Rectangle rect1 = new Rectangle();
	Rectangle rect2 = new Rectangle();
	Music music;
	Sound explode;
	private ArachnoMania arachnoMania;

	public GameScreen(ArachnoMania arachnoMania) {
		this.arachnoMania = arachnoMania;
		create();
	}

//	@Override
	public void create() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();	
		viewport = new FillViewport(800, 480, camera);
		viewport.apply();
		
		camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
		
		
		font = new BitmapFont(Gdx.files.internal("arial.fnt"));
		
		spider = new Spider();
		guy = new Guy();
		slipper = new Slipper();
		
		ready = new TextureRegion(new Texture("ready.png"));
		gameOver = new TextureRegion(new Texture("gameover.png"));
//		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
//		music.setLooping(true);
//		music.play();
		explode = Gdx.audio.newSound(Gdx.files.internal("explode.wav"));
		
		resetWorld();
	}
	
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
	}

	private void resetWorld() {
		score = 0;
		spider.reset();
		guy.reset();
		slipper.reset(guy.getX());
	}

	private void update() {
		deltaTime += Gdx.graphics.getDeltaTime();
		if (Gdx.input.justTouched()) {
			if (gameState == GameState.Start) {
				gameState = GameState.Running;
			}
			if (gameState == GameState.Running) {
				spider.touched(camera);
			}
		}

		
		gameState = spider.update(gameState, camera, viewport);
		
		if (gameState == GameState.GameOver) {
			gameState = GameState.Start;
			resetWorld();
		}
		
		guy.update(viewport, spider.getX());
		slipper.update(viewport, guy.getX());
		
		Rectangle spiderRectangle = new Rectangle(spider.getX(), spider.getY(), spider.getWidth(), spider.getHeight());
		Rectangle slipperRectangle = new Rectangle(slipper.getX(), slipper.getY(), slipper.getWidth(), slipper.getHeight());
		
		if(spiderRectangle.overlaps(slipperRectangle)) {
			gameState = GameState.GameOver;
			arachnoMania.setMenuScreen();
			resetWorld();
		}
	}

	private void draw() {
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(arachnoMania.background, 0, 0);
		
		batch.draw(spider.getKeyFrame(deltaTime), spider.getX(), spider.getY());
		batch.draw(guy.getKeyFrame(deltaTime), guy.getX(), guy.getY());
		batch.draw(slipper.getKeyFrame(deltaTime), slipper.getX(), slipper.getY());
		
		batch.end();
		
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

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		update();
		draw();
	}

	static enum GameState {
		Start, Running, GameOver
	}
}