package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.MinionSummonedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

public class StarvingBuzzard extends MinionCard {

	public StarvingBuzzard() {
		super("Starving Buzzard", 2, 1, Rarity.FREE, HeroClass.HUNTER, 2);
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
