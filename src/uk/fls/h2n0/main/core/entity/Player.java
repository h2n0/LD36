package uk.fls.h2n0.main.core.entity;

import java.util.List;

import fls.engine.main.util.Point;
import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.util.Animation;
import uk.fls.h2n0.main.util.AnimationManager;

public class Player extends Entity {

	private int steps;
	private AnimationManager am;
	private Animation anim;

	boolean xFlip;
	public int gemsFound;
	public int score;

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
	}

	@Override
	public void render(Renderer r) {
		r.renderSection(frameData, this.pos.getIX(), this.pos.getIY(), 8, this.xFlip ? r.xFlip : 0);
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
	}

	public void move(int x, int y) {
		if (x == 0 && y == 0)
			return;

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
		
		super.move(x, y);
		this.steps++;
		if (steps % 12 == 0) {
			this.anim.nextFrame();
			this.frameData = this.anim.getFrame();
		}
	}
	
	public void action(){
		this.world.addEntity(new ScoreGem(this.pos.getIX(), this.pos.getIY() - 8));
	}
	
	private void collectMainGem(){
		this.gemsFound++;
		this.world.updateGemCount();
		this.world.showPopup("Gem found", 60);
	}

}
