package app.ajay.ld42;

import javax.activation.UnsupportedDataTypeException;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Main extends ApplicationAdapter {
	public SpriteBatch batch;
	public ShapeRenderer shapeRenderer;

	Level level;
	
	OrthographicCamera cam;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
		//contains all data for the level being created
		//TODO: make a list of these and keep loading them as new levels need to be loaded
		LevelConfiguration levelConfig = new LevelConfiguration();
		
		levelConfig.blocks = new int[10][10];
		levelConfig.blocks[9] = new int[] {0, -1, 0, 0, -1, 0, 0, 0, 0, -1};
		levelConfig.blocks[8] = new int[] {0, -1, 0, 0, -1, 0, 0, 0, 0, -1};
		levelConfig.blocks[7] = new int[] {0, -1, 0, 0, -1, 0, 0, 0, 0, -1};
		levelConfig.blocks[6] = new int[] {0, -1, 0, 0, -1, 0, 0, 0, 0, -1};
		levelConfig.blocks[5] = new int[] {0, -1, 0, 0, -1, 0, 0, -1, 0, -1};
		levelConfig.blocks[4] = new int[] {0, -1, 0, 0, -1, 0, 0, -1, 0, -1};
		levelConfig.blocks[3] = new int[] {0, 0, 0, 0, -1, 0, 0, -1, 0, -1};
		levelConfig.blocks[2] = new int[] {-1, 0, 0, 0, 0, 0, 0, 0, 0, -1};
		levelConfig.blocks[1] = new int[] {-1, 0, 0, 0, -1, 0, 0, -1, 0, -1};
		levelConfig.blocks[0] = new int[] {-1, -1, 0, 0, 1, 0, 0, 0, -1, -1};
		
		levelConfig.playerX = 0;
		levelConfig.playerY = 9;
		levelConfig.endX = 4;
		levelConfig.endY = 0;

		level = new Level(this, levelConfig);
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		
	}
	
	@Override
	public void resize(int width, int height) {
		cam.setToOrtho(false, width, height);
		
		batch.setProjectionMatrix(cam.combined);
		shapeRenderer.setProjectionMatrix(cam.combined);
		cam.update();
	}

	@Override
	public void render() {
		update();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		shapeRenderer.setProjectionMatrix(cam.combined);
		
		level.render();
	}
	
	public void update() {
		level.update();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}
}
