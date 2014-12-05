package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.priest;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class UpgradedRepairBot extends MinionCard {

	public UpgradedRepairBot() {
		super("Upgraded Repair Bot", 5, 5, Rarity.RARE, HeroClass.PRIEST, 5);
		setDescription("Battlecry: Give a friendly Mech +4 Health");
		setTag(GameTag.BATTLECRY);
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 564;
	}



	@Override
	public Minion summon() {
		Minion upgradedRepairBot = createMinion();
		SpellDesc buffSpell = BuffSpell.create(0, 4);
		Battlecry battlecry = Battlecry.createBattlecry(buffSpell, TargetSelection.FRIENDLY_MINIONS);
		battlecry.setEntityFilter(entity -> entity.getTag(GameTag.RACE) == Race.MECH);
		upgradedRepairBot.setBattlecry(battlecry);
		return upgradedRepairBot;
	}
}
