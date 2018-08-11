package app.ajay.ld42;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;

public class Player extends Block{

	public Player(float x, float y) {
		super(x, y);
		
		color = Color.BLUE;
	}
	
	public void update() {
		if(Gdx.input.isKeyJustPressed(Keys.W)) {
			y++;
		} else if(Gdx.input.isKeyJustPressed(Keys.S)) {
			y--;
		} else if(Gdx.input.isKeyJustPressed(Keys.A)) {
			x--;
		} else if(Gdx.input.isKeyJustPressed(Keys.D)) {
			x++;
		}
	}

}
