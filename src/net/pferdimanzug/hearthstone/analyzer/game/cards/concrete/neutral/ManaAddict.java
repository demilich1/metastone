package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellCastedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
		SpellDesc buffSpell = BuffSpell.create(2, 0, true);
		buffSpell.setTarget(EntityReference.SELF);
		manaAddict.setSpellTrigger(new SpellTrigger(new SpellCastedTrigger(TargetPlayer.SELF), buffSpell));
		return manaAddict;
	}
}
