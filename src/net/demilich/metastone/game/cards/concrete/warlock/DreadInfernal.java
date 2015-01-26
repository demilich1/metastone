package net.demilich.metastone.game.cards.concrete.warlock;

import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class DreadInfernal extends MinionCard {

	public DreadInfernal() {
		super("Dread Infernal", 6, 6, Rarity.FREE, HeroClass.WARLOCK, 6);
		setDescription("Battlecry: Deal 1 damage to ALL other characters. ");
		setRace(Race.DEMON);
	}

	@Override
	public int getTypeId() {
		return 340;
	}
	



	@Override
	public Minion summon() {
		Minion dreadInfernal = createMinion();
		Battlecry infernoBattlecry = Battlecry.createBattlecry(DamageSpell.create(1), TargetSelection.NONE);
		infernoBattlecry.setTargetKey(EntityReference.ALL_CHARACTERS);
		dreadInfernal.setBattlecry(infernoBattlecry);
		return dreadInfernal;
	}
}
