package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.paladin;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.UniqueEntity;
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
		SpellDesc buffRecruits = BuffSpell.create(+2, +2);
		buffRecruits.setTarget(EntityReference.FRIENDLY_MINIONS);
		buffRecruits.setTargetFilter(entity -> entity.getTag(GameTag.UNIQUE_ENTITY) == UniqueEntity.SILVER_HAND_RECRUIT);
		Battlecry battlecry = Battlecry.createBattlecry(buffRecruits);
		quartermaster.setBattlecry(battlecry);
		return quartermaster;
	}
}
