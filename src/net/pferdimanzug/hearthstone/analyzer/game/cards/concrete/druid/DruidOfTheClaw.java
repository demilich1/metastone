package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.ChooseBattlecryCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.druid.BearForm;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.druid.CatForm;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TransformMinionSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class DruidOfTheClaw extends ChooseBattlecryCard {

	public DruidOfTheClaw() {
		super("Druid of the Claw", 4, 4, Rarity.COMMON, HeroClass.DRUID, 5);
		setDescription("Choose One - Charge; or +2 Health and Taunt.");
	}

	@Override
	protected String getAction1Suffix() {
		return "Charge";
	}

	@Override
	protected String getAction2Suffix() {
		return "Taunt";
	}

	@Override
	protected Battlecry getBattlecry1() {
		SpellDesc transformSpell = TransformMinionSpell.create(new CatForm());
		transformSpell.setTarget(EntityReference.SELF);
		return Battlecry.createBattlecry(transformSpell);
	}

	@Override
	protected Battlecry getBattlecry2() {
		SpellDesc transformSpell = TransformMinionSpell.create(new BearForm());
		transformSpell.setTarget(EntityReference.SELF);
		return Battlecry.createBattlecry(transformSpell);
	}

	@Override
	public int getTypeId() {
		return 6;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
