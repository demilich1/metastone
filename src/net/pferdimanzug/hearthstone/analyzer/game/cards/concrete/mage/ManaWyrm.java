package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellCastedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class ManaWyrm extends MinionCard {

	public ManaWyrm() {
		super("Mana Wyrm", 1, 3, Rarity.COMMON, HeroClass.MAGE, 1);
		setDescription("Whenever you cast a spell, gain +1 Attack.");
	}

	@Override
	public int getTypeId() {
		return 67;
	}



	@Override
	public Minion summon() {
		Minion manaWyrm = createMinion();
		Spell buffSpell = new BuffSpell(1);
		buffSpell.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new SpellCastedTrigger(TargetPlayer.SELF), buffSpell);
		manaWyrm.setSpellTrigger(trigger);
		return manaWyrm;
	}
}
