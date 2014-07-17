package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.aura.Aura;
import net.pferdimanzug.hearthstone.analyzer.game.aura.BuffAura;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class FlametongueTotem extends MinionCard {

	public FlametongueTotem() {
		super("Flametongue Totem", 0, 3, Rarity.FREE, HeroClass.SHAMAN, 2);
		setDescription("Adjacent minions have +2 Attack.");
	}

	@Override
	public int getTypeId() {
		return 320;
	}



	@Override
	public Minion summon() {
		Minion flametongueTotem = createMinion();
		flametongueTotem.setRace(Race.TOTEM);
		Aura flametongueTotemAura = new BuffAura(2, 0, EntityReference.ADJACENT_MINIONS);
		flametongueTotem.setSpellTrigger(flametongueTotemAura);
		return flametongueTotem;
	}
}
