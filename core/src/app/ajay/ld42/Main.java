package app.ajay.ld42;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.bitfire.postprocessing.PostProcessor;
import com.bitfire.postprocessing.effects.Bloom;
import com.bitfire.utils.ShaderLoader;

public class Main extends ApplicationAdapter {
	public SpriteBatch batch;
	public ShapeRenderer shapeRenderer;

	Level level;
	
	OrthographicCamera cam;
	
	ArrayList<LevelConfiguration> levels = new ArrayList<LevelConfiguration>();
	
	PostProcessor postProcessor;
	
	Bloom bloom;
	
	@Override
	public void create() {
		
		//Add bloom effect
		ShaderLoader.BasePath = "data/shaders/";
        postProcessor = new PostProcessor(false, false, Gdx.app.getType() == ApplicationType.Desktop);
        bloom = new Bloom((int) (Gdx.graphics.getWidth() * 0.25f), (int) (Gdx.graphics.getHeight() * 0.25f));
        postProcessor.addEffect( bloom );
		
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
		loadLevels();
		
		level = new Level(this, levels.get(0));
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		
	}
	
	@Override
	public void resize(int width, int height) {
		cam.setToOrtho(false, width, height);
		
		postProcessor.removeEffect(bloom);
		bloom = new Bloom((int) (width * 0.25f), (int) (height * 0.25f));
		postProcessor.addEffect(bloom);
		
		batch.setProjectionMatrix(cam.combined);
		shapeRenderer.setProjectionMatrix(cam.combined);
		cam.update();
	}

	@Override
	public void render() {
		update();
		
		//setup post processing
		postProcessor.capture();
		
		Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		shapeRenderer.setProjectionMatrix(cam.combined);
		
		level.render();
		
		//render the result
        postProcessor.render();
	}
	
	 @Override
    public void resume() {
        postProcessor.rebind();
    }
	 
	public void nextLevel() {
		if(!level.endAnimation) {
			level.nextLevel = true;
			level.endAnimation = true;
		}
	}
	 
	public void loadLevels() {
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
		
//		levels.add(levelConfig);
		
		//level 2
		levelConfig = new LevelConfiguration();
		
		levelConfig.blocks = new int[20][20];                           
		levelConfig.blocks[19] = new int[] {-1, -1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[18] = new int[] {-1, -1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, -1, -1, -1,  0,  0};
		levelConfig.blocks[17] = new int[] {-1, -1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, -1, -1, -1,  0,  0};
		levelConfig.blocks[16] = new int[] {-1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, -1, -1, -1,  0,  0};
		levelConfig.blocks[15] = new int[] {-1,  0,  0,  0,  0,  0,  0,  0, -1, -1, -1,  0,  0,  0,  0, -1, -1, -1,  0,  0};
		levelConfig.blocks[14] = new int[] { 0,  0,  0,  0,  0,  0,  0,  0, -1, -1, -1,  0,  0,  0,  0, -1, -1, -1,  0,  0};
		levelConfig.blocks[13] = new int[] { 0,  0,  0,  0,  0,  0,  0,  0, -1, -1, -1,  0,  0,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[12] = new int[] { 0,  0,  0,  0,  0,  0,  0,  0, -1, -1, -1,  0,  0,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[11] = new int[] { 0,  0,  0,  0,  0,  0,  0,  0, -1, -1, -1,  0,  0,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[10] = new int[] {-1,  0,  0,  0,  0,  0,  0,  0, -1, -1, -1,  0,  0,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[9] = new int[]  {-1,  0,  0,  0,  0,  0,  0,  0, -1, -1, -1,  0,  0,  0,  0, -1, -1, -1,  0,  0};
		levelConfig.blocks[8] = new int[]  {-1,  0,  0, -1, -1,  0,  0,  0, -1, -1, -1,  0,  0,  0,  0, -1, -1, -1,  0,  0};
		levelConfig.blocks[7] = new int[]  {-1,  0,  0, -1, -1,  0,  0,  0, -1, -1, -1,  0,  0,  0,  0, -1, -1, -1,  0,  0};
		levelConfig.blocks[6] = new int[]  {-1,  0,  0, -1, -1,  0,  0,  0, -1, -1, -1,  0,  0,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[5] = new int[]  {-1,  0,  0, -1, -1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[4] = new int[]  {-1,  0,  0, -1, -1,  0,  0,  0,  0,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[3] = new int[]  {-1,  0,  0, -1, -1,  0,  0,  0,  0,  0,  0,  0,  0,  0, -1, -1, -1,  0,  0,  0};
		levelConfig.blocks[2] = new int[]  {-1,  0,  0, -1, -1,  0,  0,  0,  0,  0,  0,  0,  0,  0, -1, -1, -1,  0,  0,  0};
		levelConfig.blocks[1] = new int[]  { 0,  0,  0, -1, -1,  0,  0,  0,  0,  0,  0,  0,  0,  0, -1, -1, -1,  0,  0,  0};
		levelConfig.blocks[0] = new int[]  { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, -1, -1, -1,  0,  0,  0};
		                                                                                                             
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
		levelConfig.endX = 11;
		levelConfig.endY = 4;
		
		levelConfig.blockSize = 32;
		
//		levels.add(levelConfig);
		
		//level 3
		levelConfig = new LevelConfiguration();
		
		levelConfig.blocks = new int[20][20];                           
		levelConfig.blocks[19] = new int[] { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, -1, -1, -1, -1, -1};
		levelConfig.blocks[18] = new int[] { 0,  0,  0,  0,  0,  0,  0, -1, -1, -1, -1, -1, -1, -1,  0, -1, -1, -1, -1, -1};
		levelConfig.blocks[17] = new int[] { 0,  0,  0,  0,  0,  0,  0, -1, -1, -1, -1, -1, -1, -1,  0, -1, -1, -1, -1, -1};
		levelConfig.blocks[16] = new int[] { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, -1, -1, -1, -1, -1};
		levelConfig.blocks[15] = new int[] { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, -1, -1, -1, -1, -1};
		levelConfig.blocks[14] = new int[] { 0,  0,  0,  0,  0, -1, -1, -1, -1, -1,  0,  0,  0,  0,  0, -1, -1, -1, -1, -1};
		levelConfig.blocks[13] = new int[] { 0, -1,  0,  0,  0, -1, -1, -1, -1, -1,  0,  0,  0,  0,  0, -1, -1, -1, -1, -1};
		levelConfig.blocks[12] = new int[] { 0, -1,  0,  0,  0, -1, -1, -1, -1, -1,  0,  0, -1,  0,  0,  0,  0,  0,  1,  0};
		levelConfig.blocks[11] = new int[] { 0, -1,  0,  0,  0, -1, -1, -1, -1, -1,  0,  0, -1,  0,  0, -1, -1, -1, -1, -1};
		levelConfig.blocks[10] = new int[] { 0, -1,  0,  0,  0, -1, -1, -1, -1, -1,  0,  0, -1,  0,  0, -1, -1, -1, -1, -1};
		levelConfig.blocks[9] = new int[]  { 0, -1,  0,  0,  0, -1, -1, -1, -1, -1,  0,  0, -1,  0,  0, -1, -1, -1, -1, -1};
		levelConfig.blocks[8] = new int[]  { 0, -1,  0,  0,  0, -1, -1, -1, -1, -1,  0,  0, -1,  0,  0, -1, -1, -1, -1, -1};
		levelConfig.blocks[7] = new int[]  { 0, -1,  0,  0,  0, -1, -1, -1, -1, -1,  0,  0, -1,  0,  0, -1, -1, -1, -1, -1};
		levelConfig.blocks[6] = new int[]  { 0, -1,  0,  0,  0, -1, -1, -1, -1, -1,  0,  0, -1,  0,  0, -1, -1, -1, -1, -1};
		levelConfig.blocks[5] = new int[]  { 0, -1,  0,  0,  0, -1, -1, -1, -1, -1,  0,  0, -1,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[4] = new int[]  { 0, -1,  0,  0,  0, -1, -1, -1, -1, -1,  0,  0, -1,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[3] = new int[]  { 0, -1,  0,  0,  0, -1, -1, -1, -1, -1,  0,  0, -1,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[2] = new int[]  { 0, -1,  0,  0,  0, -1, -1, -1, -1, -1,  0,  0, -1,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[1] = new int[]  { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, -1,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[0] = new int[]  { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, -1,  0,  0,  0,  0,  0,  0,  0};
		                                                                                                             
		levelConfig.enemies = new Vector2[10];
		levelConfig.enemies[0] = new Vector2(4, 3);
		levelConfig.enemies[1] = new Vector2(3, 12);
		levelConfig.enemies[2] = new Vector2(0, 18);
		levelConfig.enemies[3] = new Vector2(5, 0);
		levelConfig.enemies[4] = new Vector2(16, 3);
		levelConfig.enemies[5] = new Vector2(13, 1);
		levelConfig.enemies[6] = new Vector2(19, 3);
		levelConfig.enemies[7] = new Vector2(10, 14);
		levelConfig.enemies[8] = new Vector2(11, 6);
		levelConfig.enemies[9] = new Vector2(14, 18);
		
		levelConfig.playerX = 0;
		levelConfig.playerY = 9;
		levelConfig.endX = 18;
		levelConfig.endY = 12;
		
		levelConfig.blockSize = 32;
		
//		levels.add(levelConfig);
		
		//level 4
		levelConfig = new LevelConfiguration();
		
		levelConfig.blocks = new int[20][20];                                                                               
		levelConfig.blocks[19] = new int[] { 0, -1, -1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  3,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[18] = new int[] { 0, -1, -1,  0,  3,  0,  0,  0,  0,  0,  0,  0,  3,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[17] = new int[] { 0, -1, -1,  0,  3,  0,  0,  0,  0,  0,  0,  0,  3,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[16] = new int[] { 0, -1, -1,  0,  3,  0,  0, -1, -1, -1, -1, -1,  0,  0,  0,  0,  0,  3,  0,  0};
		levelConfig.blocks[15] = new int[] { 0, -1, -1,  0,  0,  0,  0, -1, -1, -1, -1, -1,  0,  0,  0,  0,  0,  3,  0,  0};
		levelConfig.blocks[14] = new int[] { 0, -1, -1,  0,  0,  0,  0, -1, -1, -1, -1, -1,  0,  0,  0,  0,  0,  3,  0,  0};
		levelConfig.blocks[13] = new int[] { 0, -1, -1,  0,  0,  0,  0, -1, -1, -1, -1, -1,  0,  0,  0,  0,  0,  3,  0,  0};
		levelConfig.blocks[12] = new int[] { 0, -1, -1,  0,  0,  0,  0, -1, -1, -1, -1, -1,  0,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[11] = new int[] { 0,  0,  0,  0,  3,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[10] = new int[] { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[9] = new int[]  { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  3,  0,  0};
		levelConfig.blocks[8] = new int[]  {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  0};
		levelConfig.blocks[7] = new int[]  {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  0};
		levelConfig.blocks[6] = new int[]  { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[5] = new int[]  { 0,  0,  0,  0,  0,  3,  0,  0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
		levelConfig.blocks[4] = new int[]  { 0,  0,  0,  0,  0,  3,  0,  0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
		levelConfig.blocks[3] = new int[]  { 0,  0,  0, -1, -1, -1, -1,  0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
		levelConfig.blocks[2] = new int[]  { 0,  0,  0, -1, -1, -1, -1,  0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
		levelConfig.blocks[1] = new int[]  { 0,  0,  0, -1, -1, -1, -1,  0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
		levelConfig.blocks[0] = new int[]  { 0,  0,  0, -1, -1, -1, -1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1};
		
		levelConfig.createEnemies();
		                                                                                                             
		levelConfig.playerX = 0;
		levelConfig.playerY = 9;
		levelConfig.endX = 19;
		levelConfig.endY = 0;
		
		levelConfig.blockSize = 32;
		
//		levels.add(levelConfig);
		
		//level 5
		levelConfig = new LevelConfiguration();
		
		levelConfig.blocks = new int[20][20];                                                                               
		levelConfig.blocks[19] = new int[] { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  3,  3,  0,  0,  0,  0,  0, -1,  0};
		levelConfig.blocks[18] = new int[] { 0,  0,  3,  0,  0,  0,  0,  0,  3,  0,  0, -1, -1,  0,  0,  3, -1,  0, -1,  0};
		levelConfig.blocks[17] = new int[] { 0,  0,  3,  0,  0,  0,  0,  0,  3,  0,  0, -1, -1, -1, -1, -1,  0,  0, -1,  0};
		levelConfig.blocks[16] = new int[] { 0,  0,  3,  0,  0,  0,  0,  0,  3,  0,  0, -1, -1,  0,  0,  0,  0,  0, -1,  0};
		levelConfig.blocks[15] = new int[] { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, -1, -1,  0,  0,  0,  0,  0, -1,  0};
		levelConfig.blocks[14] = new int[] { 0,  0,  0,  0,  0, -1, -1, -1, -1,  0,  0, -1, -1,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[13] = new int[] { 0,  0,  0,  0,  0, -1, -1, -1, -1,  0,  0, -1, -1,  3,  0,  3,  0,  0,  0,  0};
		levelConfig.blocks[12] = new int[] { 0,  0,  0,  0,  0, -1,  0,  0,  0,  0,  0, -1, -1,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[11] = new int[] { 0,  3,  0,  3,  0, -1,  0,  0,  0,  0,  0, -1, -1,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[10] = new int[] { 0,  0,  0,  0,  0, -1,  0,  0,  0,  0,  0, -1, -1,  0,  0, -1, -1, -1, -1, -1};
		levelConfig.blocks[9] = new int[]  { 0,  0,  0,  0,  0, -1, -1, -1, -1,  0,  0, -1, -1,  0,  0, -1, -1, -1, -1, -1};
		levelConfig.blocks[8] = new int[]  {-1, -1,  0,  0,  0, -1, -1, -1, -1,  0,  0, -1,  0,  0,  0, -1, -1, -1, -1, -1};
		levelConfig.blocks[7] = new int[]  {-1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, -1,  0,  0,  0, -1, -1, -1, -1, -1};
		levelConfig.blocks[6] = new int[]  {-1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  3, -1, -1,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[5] = new int[]  {-1,  0,  0, -1,  0,  0,  0,  0,  0,  0,  3, -1, -1,  0,  0,  0,  0,  3,  0,  0};
		levelConfig.blocks[4] = new int[]  {-1,  0,  0, -1,  0,  0,  0,  3,  0,  0,  3, -1, -1,  0,  0,  0,  0,  3,  0,  0};
		levelConfig.blocks[3] = new int[]  {-1,  0,  0, -1,  0,  0,  0,  3,  0,  0,  0, -1, -1,  0,  0,  0,  0,  3,  0,  0};
		levelConfig.blocks[2] = new int[]  {-1,  0,  0, -1,  0,  0,  0,  0,  0,  0,  0, -1, -1,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[1] = new int[]  {-1,  0,  0, -1,  0,  0,  0,  0,  0,  0,  0, -1, -1,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[0] = new int[]  { 1,  0,  0, -1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0};
		
		levelConfig.createEnemies();
		                                                                                                             
		levelConfig.playerX = 19;
		levelConfig.playerY = 19;
		levelConfig.endX = 0;
		levelConfig.endY = 0;
		
		levelConfig.blockSize = 32;
		
//		levels.add(levelConfig);
		
		//level 6
		levelConfig = new LevelConfiguration();
		
		levelConfig.blocks = new int[20][20];                                                                               
		levelConfig.blocks[19] = new int[] { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[18] = new int[] { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[17] = new int[] { 0,  0,  0,  0,  0,  0,  2,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[16] = new int[] { 0,  0,  0,  0,  0,  0, -1, -1, -1,  4, -1, -1, -1,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[15] = new int[] { 0,  0,  0,  0,  0,  0, -1, -1, -1,  0, -1, -1, -1,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[14] = new int[] { 0,  0,  0,  0,  0,  0, -1, -1, -1,  3, -1, -1, -1,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[13] = new int[] {-1, -1, -1,  3,  0,  0, -1, -1, -1,  0, -1, -1, -1,  0,  3,  0,  0,  0,  0,  0};
		levelConfig.blocks[12] = new int[] {-1, -1, -1,  3,  0,  0, -1, -1, -1,  0, -1, -1, -1,  0,  3,  0,  0,  0,  0,  0};
		levelConfig.blocks[11] = new int[] {-1, -1, -1,  3,  0,  0, -1, -1, -1,  0, -1, -1, -1,  0,  3,  0,  0,  0,  0,  0};
		levelConfig.blocks[10] = new int[] {-1, -1, -1,  3,  0,  0, -1, -1, -1,  1, -1, -1, -1,  0,  3,  0,  0,  0,  0,  0};
		levelConfig.blocks[9] = new int[]  {-1, -1, -1,  3,  0,  0, -1, -1, -1,  0, -1, -1, -1,  0,  3,  0,  0,  0,  0,  0};
		levelConfig.blocks[8] = new int[]  {-1, -1, -1,  3,  0,  0, -1, -1, -1,  0, -1, -1, -1,  0,  3,  0,  0,  0,  0,  0};
		levelConfig.blocks[7] = new int[]  {-1, -1, -1,  3,  0,  0, -1, -1, -1,  0, -1, -1, -1,  0,  3,  0,  0,  0,  0,  0};
		levelConfig.blocks[6] = new int[]  {-1, -1, -1,  3,  0,  0, -1, -1, -1,  0, -1, -1, -1,  0,  3,  0,  0,  0,  0,  0};
		levelConfig.blocks[5] = new int[]  {-1, -1, -1,  0,  0,  0, -1, -1, -1,  0, -1, -1, -1,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[4] = new int[]  {-1, -1, -1,  0,  0,  0, -1, -1, -1,  0, -1, -1, -1,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[3] = new int[]  {-1, -1, -1,  0,  0,  0, -1, -1, -1,  0, -1, -1, -1,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[2] = new int[]  {-1, -1, -1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[1] = new int[]  {-1, -1, -1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[0] = new int[]  {-1, -1, -1,  0,  0,  0,  3,  0,  0,  0,  0,  0,  3,  0,  0,  0,  0,  0,  0,  0};
		
		levelConfig.createEnemies();
		                                                                                                             
		levelConfig.playerX = 0;
		levelConfig.playerY = 19;
		levelConfig.endX = 9;
		levelConfig.endY = 10;
		
		levelConfig.hint = "Touching the yellow block will open the brown door";
		
		levelConfig.blockSize = 32;
		
		levels.add(levelConfig);
		
		//level 7
		levelConfig = new LevelConfiguration();
		
		levelConfig.blocks = new int[20][20];                                                                               
		levelConfig.blocks[19] = new int[] { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  4,  0,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[18] = new int[] { 0,  0,  0,  0,  3,  0,  0,  0,  3,  0,  0,  4,  0,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[17] = new int[] { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  4,  0,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[16] = new int[] { 0,  0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  0,  0};
		levelConfig.blocks[15] = new int[] { 0,  0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  0,  0};
		levelConfig.blocks[14] = new int[] { 0,  0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  0,  0};
		levelConfig.blocks[13] = new int[] { 0,  0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  0,  0};
		levelConfig.blocks[12] = new int[] { 0,  0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  0,  0};
		levelConfig.blocks[11] = new int[] { 0,  0,  0,  0,  0,  0,  0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  0,  0};
		levelConfig.blocks[10] = new int[] { 0,  0,  0,  0,  0,  0,  2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  0,  0};
		levelConfig.blocks[9] = new int[]  { 0,  0,  3,  0,  0,  0,  2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  0,  0};
		levelConfig.blocks[8] = new int[]  { 0,  0,  0,  0,  0,  0,  0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  0,  0};
		levelConfig.blocks[7] = new int[]  { 0,  0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  0,  0};
		levelConfig.blocks[6] = new int[]  { 0,  0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  0,  0};
		levelConfig.blocks[5] = new int[]  { 0,  0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  0,  0};
		levelConfig.blocks[4] = new int[]  { 0,  0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  0,  0};
		levelConfig.blocks[3] = new int[]  { 0,  0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  0,  0};
		levelConfig.blocks[2] = new int[]  { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  4,  0,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[1] = new int[]  { 0,  0,  0,  0,  0,  3,  0,  0,  0,  0,  0,  4,  0,  0,  0,  0,  0,  0,  0,  0};
		levelConfig.blocks[0] = new int[]  { 0,  0,  3,  0,  0,  0,  0,  0,  0,  0,  0,  4,  0,  0,  0,  0,  0,  0,  0,  1};
		
		levelConfig.createEnemies();
		                                                                                                             
		levelConfig.playerX = 0;
		levelConfig.playerY = 19;
		levelConfig.endX = 19;
		levelConfig.endY = 0;
		
		levelConfig.blockSize = 32;
		
		levels.add(levelConfig);
	}
	
	public void update() {
		level.update();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		shapeRenderer.dispose();
        postProcessor.dispose();
	}
}
