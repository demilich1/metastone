package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class UnstableGhoul extends MinionCard {

	public UnstableGhoul() {
		super("Unstable Ghoul", 1, 3, Rarity.COMMON, HeroClass.ANY, 2);
		setDescription("Taunt. Deathrattle: Deal 1 damage to all minions.");
	}

	@Override
	public Minion summon() {
		Minion unstableGhoul = createMinion(GameTag.TAUNT);
		Spell deathrattle = new DamageSpell(1);
		deathrattle.setTarget(EntityReference.ALL_MINIONS);
		unstableGhoul.addDeathrattle(deathrattle);
		return unstableGhoul;
	}



	@Override
	public int getTypeId() {
		return 402;
	}
}
