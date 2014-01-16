package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class DarkscaleHealer extends MinionCard {

	public DarkscaleHealer() {
		super("Darkscale Healer", Rarity.FREE, HeroClass.ANY, 5);
	}

	@Override
	public Minion summon() {
		Minion darkscaleHealer = createMinion(4, 5);
		Battlecry battlecry = Battlecry.createBattlecry(new HealingSpell(2));
		battlecry.setTargetKey(EntityReference.FRIENDLY_CHARACTERS);
		darkscaleHealer.setTag(GameTag.BATTLECRY, battlecry);
		return darkscaleHealer;
	}


}
