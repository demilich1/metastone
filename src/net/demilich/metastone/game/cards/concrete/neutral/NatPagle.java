package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnStartTrigger;

public class NatPagle extends MinionCard {

	public NatPagle() {
		super("Nat Pagle", 0, 4, Rarity.LEGENDARY, HeroClass.ANY, 2);
		setDescription("At the start of your turn, you have a 50% chance to draw an extra card.");		
	}

	@Override
	public int getTypeId() {
		return 174;
	}
	
	@Override
	public Minion summon() {
		Minion natPagle = createMinion();
		//SpellDesc drawCardSpell = DrawCardSpell.create((context, player, target) -> context.getLogic().randomBool() ? 1 : 0, TargetPlayer.SELF);
		SpellDesc drawCardSpell = DrawCardSpell.create(null, TargetPlayer.SELF);
		SpellTrigger trigger = new SpellTrigger(new TurnStartTrigger(null), drawCardSpell);
		natPagle.setSpellTrigger(trigger);
		return natPagle;
	}
}
