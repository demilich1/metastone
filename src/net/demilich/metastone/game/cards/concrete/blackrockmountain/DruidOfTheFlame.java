package net.demilich.metastone.game.cards.concrete.blackrockmountain;

import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.ChooseBattlecryCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.TransformMinionSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class DruidOfTheFlame extends ChooseBattlecryCard {

	public DruidOfTheFlame() {
		super("Druid of the Flame", 2, 2, Rarity.COMMON, HeroClass.DRUID, 3);
		setDescription("Choose One - Transform into a 5/2 minion; or a 2/5 minion.");
	}

	@Override
	protected String getAction1Suffix() {
		return "5/2";
	}

	@Override
	protected String getAction2Suffix() {
		return "2/5";
	}

	@Override
	protected BattlecryAction getBattlecry1() {
		SpellDesc transformSpell = TransformMinionSpell.create(EntityReference.SELF, new FlameLionForm(), false);
		return BattlecryAction.createBattlecry(transformSpell);
	}

	@Override
	protected BattlecryAction getBattlecry2() {
		SpellDesc transformSpell = TransformMinionSpell.create(EntityReference.SELF, new FlameBirdForm(), false);
		return BattlecryAction.createBattlecry(transformSpell);
	}

	@Override
	public Minion summon() {
		return createMinion();
	}


	@Override
	public int getTypeId() {
		return 631;
	}
}
