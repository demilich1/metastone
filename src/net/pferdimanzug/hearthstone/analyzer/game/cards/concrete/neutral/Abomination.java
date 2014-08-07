package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Abomination extends MinionCard {

	public Abomination() {
		super("Abomination", 4, 4, Rarity.RARE, HeroClass.ANY, 5);
		setDescription("Taunt. Deathrattle: Deal 2 damage to ALL characters.");
	}

	@Override
	public int getTypeId() {
		return 76;
	}

	@Override
	public Minion summon() {
		Minion abomination = createMinion(GameTag.TAUNT);
		SpellDesc deathrattle = DamageSpell.create(2);
		deathrattle.setTarget(EntityReference.ALL_CHARACTERS);
		abomination.addDeathrattle(deathrattle);
		return abomination;
	}
}
