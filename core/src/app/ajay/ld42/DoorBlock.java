package app.ajay.ld42;

import com.badlogic.gdx.graphics.Color;

public class DoorBlock extends Block{
	
	public DoorBlock(int x, int y) {
		super(x, y);
		
		color = Color.BROWN;
		
		type = 4;
		
		open = false;
	}
	
	public void update(Level level, Main main) {
		super.update(level, main);
	}
}
