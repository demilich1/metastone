package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnStartTrigger;

public class NatPagle extends MinionCard {

	public NatPagle() {
		super("Nat Pagle", 0, 4, Rarity.LEGENDARY, HeroClass.ANY, 2);
		setDescription("At the start of your turn, you have a 50% chance to draw an extra card.");		
	}

	@Override
	public Minion summon() {
		Minion natPagle = createMinion();
		Spell drawCardSpell = new DrawCardSpell((context, player, target) -> context.getLogic().randomBool() ? 1 : 0, TargetPlayer.SELF);
		SpellTrigger trigger = new SpellTrigger(new TurnStartTrigger(), drawCardSpell);
		natPagle.setSpellTrigger(trigger);
		return natPagle;
	}
	

}
