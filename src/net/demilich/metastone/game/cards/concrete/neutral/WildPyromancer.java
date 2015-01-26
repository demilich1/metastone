package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.AfterSpellCastedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class WildPyromancer extends MinionCard {

	public WildPyromancer() {
		super("Wild Pyromancer", 3, 2, Rarity.RARE, HeroClass.ANY, 2);
		setDescription("After you cast a spell, deal 1 damage to ALL minions.");
	}

	@Override
	public int getTypeId() {
		return 225;
	}

	@Override
	public Minion summon() {
		Minion wildPyromancer = createMinion();
		SpellDesc damageSpell = DamageSpell.create(1);
		damageSpell.setTarget(EntityReference.ALL_MINIONS);
		SpellTrigger trigger = new SpellTrigger(new AfterSpellCastedTrigger(TargetPlayer.SELF), damageSpell);
		wildPyromancer.setSpellTrigger(trigger);
		return wildPyromancer;
	}
}
