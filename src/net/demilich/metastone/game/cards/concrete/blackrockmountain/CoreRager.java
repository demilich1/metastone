package net.demilich.metastone.game.cards.concrete.blackrockmountain;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.targeting.EntityReference;

public class CoreRager extends MinionCard {

	public CoreRager() {
		super("Core Rager", 4, 4, Rarity.RARE, HeroClass.HUNTER, 4);
		setDescription("Battlecry: If your hand is empty, gain +3/+3.");
		setRace(Race.BEAST);
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public Minion summon() {
		Minion coreRager = createMinion();
		BattlecryAction battlecry = BattlecryAction.createBattlecry(BuffSpell.create(EntityReference.SELF, 3, 3));
		battlecry.setCondition((context, player) -> player.getHand().isEmpty());
		coreRager.setBattlecry(battlecry);
		return coreRager;
	}



	@Override
	public int getTypeId() {
		return 627;
	}
}
