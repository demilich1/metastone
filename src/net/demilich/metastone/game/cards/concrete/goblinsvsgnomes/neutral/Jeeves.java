package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.custom.JeevesSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnEndTrigger;

public class Jeeves extends MinionCard {

	public Jeeves() {
		super("Jeeves", 1, 4, Rarity.RARE, HeroClass.ANY, 4);
		setDescription("At the end of each player's turn, that player draws until they have 3 cards.");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 524;
	}



	@Override
	public Minion summon() {
		Minion jeeves = createMinion();
		SpellDesc drawCardSpell = JeevesSpell.create();
		//SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(TargetPlayer.BOTH), drawCardSpell);
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(null), drawCardSpell);
		jeeves.setSpellTrigger(trigger);
		return jeeves;
	}
}
