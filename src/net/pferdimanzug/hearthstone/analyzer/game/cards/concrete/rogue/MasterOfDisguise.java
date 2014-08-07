package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

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
