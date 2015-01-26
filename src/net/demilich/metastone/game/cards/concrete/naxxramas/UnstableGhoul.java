package net.demilich.metastone.game.cards.concrete.naxxramas;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class UnstableGhoul extends MinionCard {

	public UnstableGhoul() {
		super("Unstable Ghoul", 1, 3, Rarity.COMMON, HeroClass.ANY, 2);
		setDescription("Taunt. Deathrattle: Deal 1 damage to all minions.");
	}

	@Override
	public int getTypeId() {
		return 402;
	}

	@Override
	public Minion summon() {
		Minion unstableGhoul = createMinion(GameTag.TAUNT);
		SpellDesc deathrattle = DamageSpell.create(1);
		deathrattle.setTarget(EntityReference.ALL_MINIONS);
		unstableGhoul.addDeathrattle(deathrattle);
		return unstableGhoul;
	}
}
