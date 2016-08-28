package uk.fls.h2n0.main.core.entity;

import java.util.List;

import fls.engine.main.util.Point;
import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.core.tiles.BossDoor;
import uk.fls.h2n0.main.core.tiles.Tile;
import uk.fls.h2n0.main.util.Animation;
import uk.fls.h2n0.main.util.AnimationManager;

public class Player extends Entity {

	private int steps;
	private AnimationManager am;
	private Animation anim;

	boolean xFlip;
	public int gemsFound;
	public int score;
	
	private Point target;
	private float range;
	private int delay;

	public Player(int x, int y) {
		this.frameData = this.sp.getData(0);
		this.pos = new Point(x, y);
		this.am = new AnimationManager();
		this.am.addAnimation("walkLR", new Animation(this.sp, Animation.I_STYLE, 0, 1, 2, 1));
		this.am.addAnimation("walkU", new Animation(this.sp, Animation.XY_STYLE, 0, 1, 1, 1, 2, 1, 1, 1));
		this.am.addAnimation("walkD", new Animation(this.sp, Animation.XY_STYLE, 0, 2, 1, 2, 2, 2, 1, 2));
		this.anim = this.am.getAnimation("walkLR");
		this.xFlip = false;
		this.speed = 0.75f;
		this.target = Point.zero;
		this.health = 4;
		this.range = 10;
		this.delay = 0;
	}

	@Override
	public void render(Renderer r) {
		r.renderSection(frameData, this.pos.getIX(), this.pos.getIY(), 8, this.xFlip ? r.xFlip : 0);
		
		if(this.target != Point.zero){
			int tx = this.pos.getIX() + this.target.getIX() + 4;
			int ty = this.pos.getIY() + this.target.getIY() + 4;
			Point p = new Point(this.pos.getIX() + 4, this.pos.getIY() + 4);
			float ang = getAngle(p, target);
			float dx = (float)Math.cos(ang) * this.range;
			float dy = (float)Math.sin(ang) * this.range;
			r.setPixel(p.getIX() + (int)dx, p.getIY() + (int)dy, 255 << 16);
		}
	}

	@Override
	public void update() {
		List<Entity> ents = this.world.getEntitysAround(this);
		for(Entity e : ents){
			if(e instanceof ScoreGem){
				ScoreGem g = (ScoreGem)e;
				if(g.pos.dist(this.pos) > 2 * 2){
					g.floatToward(pos);
				}else{
					g.alive = false;
					this.score += 10 * g.getWeight();
				}
			}else if(e instanceof Gem){
				Gem g = (Gem)e;
				if(g.pos.dist(this.pos) < 16 * 16){
					g.alive = false;
					collectMainGem();
				}
			}
		}
		
		if(this.delay > 0)this.delay--;
	}

	public boolean move(float x, float y) {
		if (x == 0 && y == 0)
			return false;

		if (y < 0) {
			this.anim = this.am.getAnimation("walkU");
		}else if(y > 0){	
			this.anim = this.am.getAnimation("walkD");
		}else if(x > 0 || x < 0){
			this.anim = this.am.getAnimation("walkLR");
		}

		if (this.am.getCurrentName().equals("walkLR")) {
			if (x < 0)
				xFlip = true;
			else
				xFlip = false;
		}
		
		this.steps++;
		if (steps % 12 == 0) {
			this.anim.nextFrame();
			this.frameData = this.anim.getFrame();
		}
		
		boolean m = super.move(x, y);
		if(m){
			int tx = (this.pos.getIX()+4)/8;
			int ty = (this.pos.getIY()+4)/8;
			if(this.world.getTile(tx, ty) == Tile.bossDoor){
				BossDoor b = (BossDoor)this.world.getTile(tx, ty);
				b.activate();
			}
		}
		
		return m;
	}
	
	public void action(){
		if(this.target == Point.zero || delay > 0 || this.gemsFound == 0)return;
		Point p = new Point(this.pos.getIX(), this.pos.getIY());
		float ang = getAngle(p, target);
		float dx = (float)Math.cos(ang) * this.range;
		float dy = (float)Math.sin(ang) * this.range;
		this.world.addEntity(new FireBall((int)(p.x + dx), (int)(p.y + dy), ang));
		this.delay = 20;
	}
	
	public void aim(float ax, float ay){
		if(ax == 0f && ay == 0f)this.target = Point.zero;
		else this.target = new Point(ax,ay);
	}
	
	private void collectMainGem(){
		this.gemsFound++;
		this.world.updateGemCount();
		this.world.showPopup("Gem found", 60 * 2);
	}
	
	private float getAngle(Point origin, Point target){
		float ax = origin.x - target.x;
		float ay = origin.y - target.y;
		return (float)Math.atan2(origin.y - ay, origin.x - ax);
	}

}
