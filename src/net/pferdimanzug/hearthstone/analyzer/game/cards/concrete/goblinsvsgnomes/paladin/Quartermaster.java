package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.UniqueEntity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
