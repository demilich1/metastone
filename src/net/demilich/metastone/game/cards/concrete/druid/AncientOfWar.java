package net.demilich.metastone.game.cards.concrete.druid;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.ChooseBattlecryCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.ApplyTagSpell;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class AncientOfWar extends ChooseBattlecryCard {

	public AncientOfWar() {
		super("Ancient of War", 5, 5, Rarity.EPIC, HeroClass.DRUID, 7);
		setDescription("Choose One - +5 Attack; or +5 Health and Taunt.");
	}

	@Override
	protected String getAction1Suffix() {
		return "+5 Attack";
	}

	@Override
	protected String getAction2Suffix() {
		return "+5 Health/Taunt";
	}

	@Override
	protected Battlecry getBattlecry1() {
		SpellDesc buffSpell = BuffSpell.create(5);
		buffSpell.setTarget(EntityReference.SELF);
		return Battlecry.createBattlecry(buffSpell);
	}

	@Override
	protected Battlecry getBattlecry2() {
		SpellDesc buffHpSpell = BuffSpell.create(0, 5);
		buffHpSpell.setTarget(EntityReference.SELF);
		SpellDesc tauntUpSpell = ApplyTagSpell.create(GameTag.TAUNT);
		tauntUpSpell.setTarget(EntityReference.SELF);
		
		return Battlecry.createBattlecry(MetaSpell.create(buffHpSpell, tauntUpSpell));
	}

	@Override
	public int getTypeId() {
		return 2;
	}


	@Override
	public Minion summon() {
		return createMinion();
	}
}
