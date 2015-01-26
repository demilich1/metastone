package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.HealingSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class EarthenRingFarseer extends MinionCard {

	public EarthenRingFarseer() {
		super("Earthen Ring Farseer", 3, 3, Rarity.COMMON, HeroClass.ANY, 3);
		setDescription("Battlecry: Restore 3 Health.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 121;
	}

	@Override
	public Minion summon() {
		Minion earthenRingFarseer = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(HealingSpell.create(3), TargetSelection.ANY);
		earthenRingFarseer.setBattlecry(battlecry);
		return earthenRingFarseer;
	}
}
