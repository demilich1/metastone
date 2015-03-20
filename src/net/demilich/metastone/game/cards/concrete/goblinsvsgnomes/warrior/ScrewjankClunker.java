package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warrior;

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

public class ScrewjankClunker extends MinionCard {

	public ScrewjankClunker() {
		super("Screwjank Clunker", 2, 5, Rarity.RARE, HeroClass.WARRIOR, 4);
		setDescription("Battlecry: Give a friendly Mech +2/+2.");
		setTag(GameTag.BATTLECRY);

		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 607;
	}



	@Override
	public Minion summon() {
		Minion screwjankClunker = createMinion();
		SpellDesc buffMechSpell = BuffSpell.create(+2, +2);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(buffMechSpell, TargetSelection.FRIENDLY_MINIONS);
		battlecry.setEntityFilter(entity -> entity.getTag(GameTag.RACE) == Race.MECH);
		screwjankClunker.setBattlecry(battlecry);
		return screwjankClunker;
	}
}
