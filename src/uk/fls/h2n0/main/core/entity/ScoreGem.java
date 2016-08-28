package uk.fls.h2n0.main.core.entity;

import java.util.List;

import fls.engine.main.util.Point;

public class ScoreGem extends Gem{

	private int weight;
	private boolean shouldFloat = false;
	private int idleTime = 0;
	
	public ScoreGem(int x, int y) {
		super(x, y);
		this.frameData = sp.getData(7);
		this.shadowWidth = 2;
		this.weight = (int)(Math.random() * 5);
		this.idleTime = 60;
	}
	
	public void update(){
		super.update();
		
		if(this.idleTime > 0)this.idleTime--;
		else{
			List<Entity> ents = this.world.getEntitysAround(this, 16 + this.weight * 4);
			for(Entity e : ents){
				if(e instanceof ScoreGem){
					ScoreGem e2 = (ScoreGem)e;
					float dist = e2.pos.dist(pos);
					if(e2.getWeight() < this.weight){
						if(dist < 2){
							e2.alive = false;
							this.weight++;
							this.color = genColor();
						}
					}else if(e2.getWeight() >= this.weight){
						floatToward(e2.pos);
					}
				}
			}
			
			if(this.shouldFloat){
				speed += 0.0005f / this.weight;
				float max = 0.2f;
				if(speed > max)this.speed = max;
			}
		}
	}
	
	public int getWeight(){
		return this.weight;
	}
	
	public void floatToward(Point p){
		//this.pos.x += (p.x-this.pos.x) * speed;
		//this.pos.y += (p.y-this.pos.y) * speed;
		move((p.x-this.pos.x), (p.y-this.pos.y));
		this.shouldFloat = true;
	}

}
