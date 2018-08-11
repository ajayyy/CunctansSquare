package app.ajay.ld42;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Main extends ApplicationAdapter {
	public SpriteBatch batch;
	public ShapeRenderer shapeRenderer;

	Level level;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
		//contains all data for the level being created
		//TODO: make a list of these and keep loading them as new levels need to be loaded
		LevelConfiguration levelConfig = new LevelConfiguration();
		
		levelConfig.blocks = new int[5][5];
		levelConfig.blocks[0] = new int[] {-1, -1, 0, 0, -1};

		level = new Level(this, levelConfig);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		level.render();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}
}
