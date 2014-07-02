package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;

public class ManaTideTotem extends MinionCard {

	public ManaTideTotem() {
		super("Mana Tide Totem", 0, 3, Rarity.RARE, HeroClass.SHAMAN, 3);
		setDescription("At the end of your turn, draw a card.");
		setTag(GameTag.RACE, Race.TOTEM);
	}

	@Override
	public Minion summon() {
		Minion manaTideTotem = createMinion();
		manaTideTotem.setSpellTrigger(new SpellTrigger(new TurnEndTrigger(), new DrawCardSpell()));
		return manaTideTotem;
	}

}
