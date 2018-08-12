package app.ajay.ld42;

import javax.activation.UnsupportedDataTypeException;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

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
		
		levelConfig.blocks = new int[10][20];
		levelConfig.blocks[9] = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		levelConfig.blocks[8] = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		levelConfig.blocks[7] = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		levelConfig.blocks[6] = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		levelConfig.blocks[5] = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		levelConfig.blocks[4] = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		levelConfig.blocks[3] = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		levelConfig.blocks[2] = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		levelConfig.blocks[1] = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		levelConfig.blocks[0] = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1};
		
		levelConfig.enemies = new Vector2[10];
		levelConfig.enemies[0] = new Vector2(4, 3);
		levelConfig.enemies[1] = new Vector2(7, 4);
		levelConfig.enemies[2] = new Vector2(1, 6);
		levelConfig.enemies[3] = new Vector2(5, 0);
		levelConfig.enemies[4] = new Vector2(16, 3);
		levelConfig.enemies[5] = new Vector2(13, 1);
		levelConfig.enemies[6] = new Vector2(19, 8);
		levelConfig.enemies[7] = new Vector2(12, 5);
		levelConfig.enemies[8] = new Vector2(11, 6);
		levelConfig.enemies[9] = new Vector2(15, 8);
		
		levelConfig.playerX = 0;
		levelConfig.playerY = 9;
		levelConfig.endX = 19;
		levelConfig.endY = 0;
		
		levelConfig.blockSize = 32;

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
