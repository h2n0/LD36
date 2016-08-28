package uk.fls.h2n0.main.core.tiles;

import uk.fls.h2n0.main.core.World;

public class BossDoorTrigger extends FloorTile {

	
	public void trigger(World w, int tx, int ty){
		w.triggerBossDoors(w.getData(tx, ty));
	}
}
