package uk.fls.h2n0.main.core.tiles;

import fls.engine.main.io.FileIO;
import fls.engine.main.util.Renderer;
import fls.engine.main.util.rendertools.SpriteParser;
import uk.fls.h2n0.main.core.World;

public abstract class Tile {


	public static Tile none = new VoidTile();
	public static Tile floor = new FloorTile();
	public static Tile wall = new WallTile();
	public static Tile door = new DoorTile();
	public static Tile fallingDoor = new FallingDoor();
	public static Tile bossDoor = new BossDoor();
	public static Tile bossDoorTrigger = new BossDoorTrigger();

	public boolean blocksMovment;
	
	public Tile(){
		this.blocksMovment = false;
	}
	public SpriteParser sp = new SpriteParser(FileIO.instance.readInternalFile("/tiles/data1.art"));
	
	public abstract void render(World w, Renderer r, int dx, int dy, int tx, int ty);
	
	public abstract void update(World w, int tx, int ty);
}
