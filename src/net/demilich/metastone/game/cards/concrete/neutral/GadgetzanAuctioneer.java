package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.trigger.SpellCastedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

public class GadgetzanAuctioneer extends MinionCard {

	public GadgetzanAuctioneer() {
		super("Gadgetzan Auctioneer", 4, 4, Rarity.RARE, HeroClass.ANY, 6);
		setDescription("Whenever you cast a spell, draw a card.");
	}

	@Override
	public int getTypeId() {
		return 132;
	}

	@Override
	public Minion summon() {
		Minion gadgetzanAuctioneer = createMinion();
		//SpellTrigger trigger = new SpellTrigger(new SpellCastedTrigger(TargetPlayer.SELF), DrawCardSpell.create());
		SpellTrigger trigger = new SpellTrigger(new SpellCastedTrigger(null), DrawCardSpell.create());
		gadgetzanAuctioneer.setSpellTrigger(trigger);
		return gadgetzanAuctioneer;
	}
}
