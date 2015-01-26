package net.demilich.metastone.game.cards.concrete.hunter;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.trigger.MinionSummonedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

public class StarvingBuzzard extends MinionCard {

	public StarvingBuzzard() {
		super("Starving Buzzard", 3, 2, Rarity.FREE, HeroClass.HUNTER, 5);
		setDescription("Whenever you summon a Beast, draw a card.");
		setRace(Race.BEAST);
	}

	@Override
	public int getTypeId() {
		return 46;
	}

	@Override
	public Minion summon() {
		Minion starvingBuzzard = createMinion();
		SpellTrigger trigger = new SpellTrigger(new MinionSummonedTrigger(TargetPlayer.SELF, Race.BEAST), DrawCardSpell.create());
		starvingBuzzard.setSpellTrigger(trigger);
		return starvingBuzzard;
	}
}
