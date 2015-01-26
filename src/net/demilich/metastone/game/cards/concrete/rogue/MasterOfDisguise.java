package net.demilich.metastone.game.cards.concrete.rogue;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.ApplyTagSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class MasterOfDisguise extends MinionCard {

	public MasterOfDisguise() {
		super("Master of Disguise", 4, 4, Rarity.RARE, HeroClass.ROGUE, 4);
		setDescription("Battlecry: Give a friendly minion Stealth.");
	}

	@Override
	public int getTypeId() {
		return 298;
	}

	@Override
	public Minion summon() {
		Minion masterOfDisguise = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(ApplyTagSpell.create(GameTag.STEALTHED), TargetSelection.FRIENDLY_MINIONS);
		masterOfDisguise.setBattlecry(battlecry);
		return masterOfDisguise;
	}
}
