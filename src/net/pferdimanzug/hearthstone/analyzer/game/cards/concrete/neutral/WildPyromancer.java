package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellCastedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class WildPyromancer extends MinionCard {

	public WildPyromancer() {
		super("Wild Pyromancer", 3, 2, Rarity.RARE, HeroClass.PRIEST, 2);
		setDescription("After you cast a spell, deal 1 damage to ALL minions.");
	}

	@Override
	public int getTypeId() {
		return 225;
	}



	@Override
	public Minion summon() {
		Minion wildPyromancer = createMinion();
		Spell damageSpell = new DamageSpell(1);
		damageSpell.setTarget(EntityReference.ALL_MINIONS);
		SpellTrigger trigger = new SpellTrigger(new SpellCastedTrigger(TargetPlayer.SELF), damageSpell);
		wildPyromancer.setSpellTrigger(trigger);
		return wildPyromancer;
	}
}
