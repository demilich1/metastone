package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.HealingSpell;
import net.demilich.metastone.game.targeting.EntityReference;

public class DarkscaleHealer extends MinionCard {

	public DarkscaleHealer() {
		super("Darkscale Healer", 4, 5, Rarity.FREE, HeroClass.ANY, 5);
		setDescription("Battlecry: Restore 2 Health to all friendly characters.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 113;
	}

	@Override
	public Minion summon() {
		Minion darkscaleHealer = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(HealingSpell.create(2));
		battlecry.setTargetKey(EntityReference.FRIENDLY_CHARACTERS);
		darkscaleHealer.setBattlecry(battlecry);
		return darkscaleHealer;
	}
}
