package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffRandomSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class DarkCultist extends MinionCard {

	public DarkCultist() {
		super("Dark Cultist", 3, 4, Rarity.COMMON, HeroClass.PRIEST, 3);
		setDescription("Deathrattle: Give a random friendly minion +3 Health.");
	}

	@Override
	public int getTypeId() {
		return 387;
	}

	@Override
	public Minion summon() {
		Minion darkCultist = createMinion();
		Spell randomBuffSpell = new BuffRandomSpell(0, 3);
		randomBuffSpell.setTarget(EntityReference.FRIENDLY_MINIONS);
		darkCultist.addDeathrattle(randomBuffSpell);
		return darkCultist;
	}
}
