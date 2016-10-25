package net.demilich.metastone.game.decks;

import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.logic.ProceduralGameLogic;

public class Bench extends Deck {
	public Bench(HeroClass heroClass) {
		super(heroClass);
	}
	public Bench(HeroClass heroClass, boolean arbitrary) {
		super(heroClass, arbitrary);

	}
	
	public boolean isComplete() {
		return cards.getCount() == ProceduralGameLogic.BENCH_SIZE;
	}
	
	public boolean isFull() {
		return cards.getCount() == ProceduralGameLogic.BENCH_SIZE;
	}
	
	public boolean isTooBig() {
		return cards.getCount() > ProceduralGameLogic.BENCH_SIZE;
	}
}
