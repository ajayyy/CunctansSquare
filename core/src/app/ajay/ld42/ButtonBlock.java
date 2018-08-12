package app.ajay.ld42;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;

public class ButtonBlock extends Block{

	public ButtonBlock(int x, int y) {
		super(x, y);
		
		color = Color.YELLOW;
		
		type = 2;
		
		open = true;
	}
	
	public void update(Level level, Main main) {
		super.update(level, main);
		
		if (level.getBlock(level.player.x, level.player.y) == this) {
			for (Block block : new ArrayList<Block>(level.blocks)) {
				if (block.type == 4) {
					Block newBlock = new OpenBlock(block.x, block.y);
					
					level.blocks.remove(block);
					level.blocks.add(newBlock);
				}
			}
		}
	}
}
