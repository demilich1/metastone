package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.paladin;

import java.util.function.Predicate;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.UniqueEntity;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class Quartermaster extends MinionCard {

	public Quartermaster() {
		super("Quartermaster", 2, 5, Rarity.EPIC, HeroClass.PALADIN, 5);
		setDescription("Battlecry: Give your Silver Hand Recruits +2/+2.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 555;
	}



	@Override
	public Minion summon() {
		Minion quartermaster = createMinion();
		Predicate<Entity> targetFilter = entity -> entity.getTag(GameTag.UNIQUE_ENTITY) == UniqueEntity.SILVER_HAND_RECRUIT;
		SpellDesc buffRecruits = BuffSpell.create(EntityReference.FRIENDLY_MINIONS, +2, +2, targetFilter, false);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(buffRecruits);
		quartermaster.setBattlecry(battlecry);
		return quartermaster;
	}
}
