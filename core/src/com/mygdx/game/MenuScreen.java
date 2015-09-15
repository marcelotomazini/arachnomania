package com.mygdx.game;

import static com.mygdx.game.Messages.ABOUT;
import static com.mygdx.game.Messages.NEW_GAME;
import static com.mygdx.game.Messages.OPTIONS;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuScreen extends ScreenAdapter {

	private Stage stage = new Stage();
	
	private Skin skin = new Skin(Gdx.files.internal("data/skin.json"));
	private Table mainTable = new Table();
	private Table menuTable = new Table();
	
	private ArachnoMania game;
	
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Viewport viewport;
	
	TextButton btnNew;
	TextButton btnOptions;
	TextButton btnAbout;
	
	public MenuScreen(ArachnoMania arachnoMania) {
		game = arachnoMania;
		create();
	}

	private void create() {
		Gdx.input.setInputProcessor(stage);
		
		batch = new SpriteBatch();
		camera = new OrthographicCamera();	
		viewport = new FillViewport(800, 480, camera);
		viewport.apply();
		
		camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
		
		skin.add("font", new BitmapFont());

		btnNew = new TextButton(NEW_GAME.getValue(), skin);
		btnOptions = new TextButton(OPTIONS.getValue(), skin);
		btnAbout = new TextButton(ABOUT.getValue(), skin);
		
		menuTable.add(btnNew).pad(50);
		menuTable.row();
		menuTable.add(btnOptions).pad(50);
		menuTable.row();
		menuTable.add(btnAbout).pad(50);
		
		
		mainTable.add(menuTable);
		mainTable.setFillParent(true);
		
		stage.addActor(mainTable);
		
		btnNew.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				game.setGameScreen();
			}
		});
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
	    camera.update();
		batch.setProjectionMatrix(camera.combined);    
	    batch.begin();
		batch.draw(game.background, 0, 0);
		stage.act(delta);
		batch.end();
		
		stage.draw();
	}
}