package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.MinionDamagedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class FrothingBerserker extends MinionCard {

	public FrothingBerserker() {
		super("Frothing Berserker", 2, 4, Rarity.RARE, HeroClass.WARRIOR, 3);
		setDescription("Whenever a minion takes damage, gain +1 Attack.");
	}

	@Override
	public Minion summon() {
		Minion frothingBerserker = createMinion();
		Spell buffSpell = new BuffSpell(1);
		buffSpell.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new MinionDamagedTrigger(TargetPlayer.BOTH), buffSpell);
		frothingBerserker.setSpellTrigger(trigger);
		return frothingBerserker;
	}

}
