package uk.fls.h2n0.main.core.entity;

import fls.engine.main.io.FileIO;
import fls.engine.main.util.Point;
import fls.engine.main.util.Renderer;
import fls.engine.main.util.rendertools.SpriteParser;
import uk.fls.h2n0.main.core.World;
import uk.fls.h2n0.main.core.tiles.Tile;

public abstract class Entity {

	protected Point pos;
	protected int[] frameData;
	protected float speed = 1f;
	public World world;
	protected boolean alive = true;
	
	protected Point targetPoint = Point.zero;
	protected SpriteParser sp = new SpriteParser(FileIO.instance.readInternalFile("/entitys/data1.art"));

	public abstract void render(Renderer r);

	public abstract void update();

	public void move(int dx, int dy) {
		float nx = this.pos.x - dx;
		float ny = this.pos.y - dy;

		float rad = (float) Math.atan2(this.pos.y - ny, this.pos.x - nx);

		float npx = (float) (this.pos.x + (Math.cos(rad) * speed));
		float npy = (float) (this.pos.y + (Math.sin(rad) * speed));
		
		
		int xPadding = 4;
		int yPadding = 4;
		if(dx > 0)xPadding = 9;
		else if(dx < 0) xPadding = -1;
		if(dy > 0)yPadding = 9;
		else if(dy < 0) yPadding = -1;
		this.targetPoint = new Point(this.pos.x + xPadding, this.pos.y + yPadding);
		
		int ntx = (int)this.pos.x + xPadding;
		int nty = (int)this.pos.y + yPadding;

		Tile t = this.world.getTile(ntx/8, nty/8); 
		if(t == null)return;
		if (!t.blocksMovment)
			this.pos.setPos(npx, npy);
	}

	public Point getPos() {
		return this.pos;
	}
	
	public boolean isAlive(){
		return this.alive;
	}
}
