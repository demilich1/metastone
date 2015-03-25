package net.demilich.metastone.game.cards.concrete.druid;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.ChooseBattlecryCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.AddAttributeSpell;
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
	protected BattlecryAction getBattlecry1() {
		SpellDesc buffSpell = BuffSpell.create(EntityReference.SELF, 5, 0);
		return BattlecryAction.createBattlecry(buffSpell);
	}

	@Override
	protected BattlecryAction getBattlecry2() {
		SpellDesc buffHpSpell = BuffSpell.create(EntityReference.SELF, 0, 5);
		SpellDesc tauntUpSpell = AddAttributeSpell.create(EntityReference.SELF, GameTag.TAUNT, null);
		
		return BattlecryAction.createBattlecry(MetaSpell.create(buffHpSpell, tauntUpSpell));
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
