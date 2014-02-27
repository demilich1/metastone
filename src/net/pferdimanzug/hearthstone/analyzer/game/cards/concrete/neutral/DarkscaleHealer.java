package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class DarkscaleHealer extends MinionCard {

	public DarkscaleHealer() {
		super("Darkscale Healer", 4, 5, Rarity.FREE, HeroClass.ANY, 5);
		setDescription("Battlecry: Restore 2 Health to all friendly characters.");
	}

	@Override
	public Minion summon() {
		Minion darkscaleHealer = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(new HealingSpell(2));
		battlecry.setTargetKey(EntityReference.FRIENDLY_CHARACTERS);
		darkscaleHealer.setBattlecry(battlecry);
		return darkscaleHealer;
	}


}
