package com.mygdx.game;

import static com.mygdx.game.Messages.ABOUT;
import static com.mygdx.game.Messages.NEW_GAME;
import static com.mygdx.game.Messages.OPTIONS;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MenuScreen extends ScreenAdapter {

	private Stage stage = new Stage();
	
	private Skin skin = new Skin(Gdx.files.internal("data/skin.json"));
	private Table mainTable = new Table();
	private Table menuTable = new Table();
	
	private ArachnoMania game;
	
	TextButton btnNew;
	TextButton btnOptions;
	TextButton btnAbout;
	
	public MenuScreen(ArachnoMania arachnoMania) {
		game = arachnoMania;
		create();
	}

	private void create() {
		Gdx.input.setInputProcessor(stage);
		
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
		
		stage.act(delta);
	    stage.draw();
	}
}