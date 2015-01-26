package net.demilich.metastone.game.cards.concrete.warrior;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.MinionDamagedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class FrothingBerserker extends MinionCard {

	public FrothingBerserker() {
		super("Frothing Berserker", 2, 4, Rarity.RARE, HeroClass.WARRIOR, 3);
		setDescription("Whenever a minion takes damage, gain +1 Attack.");
	}

	@Override
	public int getTypeId() {
		return 370;
	}

	@Override
	public Minion summon() {
		Minion frothingBerserker = createMinion();
		SpellDesc buffSpell = BuffSpell.create(1);
		buffSpell.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new MinionDamagedTrigger(TargetPlayer.BOTH), buffSpell);
		frothingBerserker.setSpellTrigger(trigger);
		return frothingBerserker;
	}
}
