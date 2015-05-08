package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.TemporaryAttackSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellCastedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class ManaAddict extends MinionCard {

	public ManaAddict() {
		super("Mana Addict", 1, 3, Rarity.RARE, HeroClass.ANY, 2);
		setDescription("Whenever you cast a spell, gain +2 Attack this turn.");
	}

	@Override
	public int getTypeId() {
		return 162;
	}

	@Override
	public Minion summon() {
		Minion manaAddict = createMinion();
		SpellDesc buffSpell = TemporaryAttackSpell.create(EntityReference.SELF, 2);
		//manaAddict.setSpellTrigger(new SpellTrigger(new SpellCastedTrigger(TargetPlayer.SELF), buffSpell));
		manaAddict.setSpellTrigger(new SpellTrigger(new SpellCastedTrigger(null), buffSpell));
		return manaAddict;
	}
}
