package net.demilich.metastone.game.cards.concrete.shaman;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnEndTrigger;

public class ManaTideTotem extends MinionCard {

	public ManaTideTotem() {
		super("Mana Tide Totem", 0, 3, Rarity.RARE, HeroClass.SHAMAN, 3);
		setDescription("At the end of your turn, draw a card.");
		setRace(Race.TOTEM);
	}

	@Override
	public int getTypeId() {
		return 327;
	}



	@Override
	public Minion summon() {
		Minion manaTideTotem = createMinion();
		manaTideTotem.setSpellTrigger(new SpellTrigger(new TurnEndTrigger(), DrawCardSpell.create()));
		return manaTideTotem;
	}
}
