package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.priest;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

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
		BattlecryAction battlecry = BattlecryAction.createBattlecry(buffSpell, TargetSelection.FRIENDLY_MINIONS);
		battlecry.setEntityFilter(entity -> entity.getTag(GameTag.RACE) == Race.MECH);
		upgradedRepairBot.setBattlecry(battlecry);
		return upgradedRepairBot;
	}
}
