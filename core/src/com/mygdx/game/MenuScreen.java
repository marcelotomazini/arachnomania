package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MenuScreen extends ScreenAdapter {

	private Stage stage = new Stage();
	
	private Skin skin = new Skin(Gdx.files.internal("data/skin.json"));
	private Table mainTable = new Table();
	private Table menuTable = new Table();
	
	private SpriteBatch batch;
	private ArachnoMania game;
	private OrthographicCamera camera;
	private BitmapFont font;
	
	TextButton btnNew;

	public MenuScreen(ArachnoMania arachnoMania) {
		game = arachnoMania;

		create();
		
		game.setScreen(this);
	}

	private void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		batch = new SpriteBatch();
		font = new BitmapFont();
		
		BitmapFont font = new BitmapFont();
		skin.add("font", font);

		btnNew = new TextButton("New", skin);
		
		menuTable.add(btnNew).pad(50);
		
		
		mainTable.add(menuTable);
		mainTable.setFillParent(true);
		stage.addActor(mainTable);
//		stage.setViewport(width, height);
	}
	
	@Override
	public void render(float delta) {
//		update();
//		draw();
		
		Gdx.gl20.glClearColor(1, 1, 1, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
//		Table.drawDebug(stage);
	}
	
	private void update() {
//		if (Gdx.input.isTouched()) {
//		    game.setScreen(new GameScreen(game));
//		    dispose();
//		}		
	}

	private void draw() {
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		
		batch.draw(game.background, 0, 0);
		font.draw(batch, "Welcome to Slingshot Steve!!! ", 100, 150);
		font.draw(batch, "Tap anywhere to begin!", 100, 100);
		
		batch.end();
		
		stage.draw();
	}
}