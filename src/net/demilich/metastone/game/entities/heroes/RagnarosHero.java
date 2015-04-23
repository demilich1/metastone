package net.demilich.metastone.game.entities.heroes;

import net.demilich.metastone.game.heroes.powers.DieInsect;

public class RagnarosHero extends Hero {

	public RagnarosHero() {
		super("Ragnaros the Firelord", null, new DieInsect());

		setMaxHp(8);
		setHp(8);
	}

}
