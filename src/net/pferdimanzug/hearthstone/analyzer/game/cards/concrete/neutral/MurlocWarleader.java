package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.aura.Aura;
import net.pferdimanzug.hearthstone.analyzer.game.spells.aura.BuffAura;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class MurlocWarleader extends MinionCard {

	public MurlocWarleader() {
		super("Murloc Warleader", 3, 3, Rarity.EPIC, HeroClass.ANY, 3);
		setDescription("ALL other Murlocs have +2/+1.");
		setRace(Race.MURLOC);
	}

	@Override
	public int getTypeId() {
		return 173;
	}



	@Override
	public Minion summon() {
		Minion murlocWarleader = createMinion();
		Aura warleaderAura = new BuffAura(+2, +1, EntityReference.ALL_MINIONS, Race.MURLOC);
		murlocWarleader.setSpellTrigger(warleaderAura);
		return murlocWarleader;
	}
}
