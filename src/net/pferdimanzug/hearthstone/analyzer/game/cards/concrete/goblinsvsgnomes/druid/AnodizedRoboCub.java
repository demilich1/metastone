package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.druid;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.ChooseBattlecryCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class AnodizedRoboCub extends ChooseBattlecryCard {

	public AnodizedRoboCub() {
		super("Anodized Robo Cub", 2, 2, Rarity.COMMON, HeroClass.DRUID, 2);
		setDescription("Taunt. Choose One - +1 Attack; or +1 Health.");
		setRace(Race.MECH);
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}

	@Override
	protected String getAction1Suffix() {
		return "+1 Attack";
	}

	@Override
	protected String getAction2Suffix() {
		return "+1 Health";
	}

	@Override
	protected Battlecry getBattlecry1() {
		SpellDesc spell = BuffSpell.create(+1);
		spell.setTarget(EntityReference.SELF);
		return Battlecry.createBattlecry(spell);
	}

	@Override
	protected Battlecry getBattlecry2() {
		SpellDesc spell = BuffSpell.create(0, +1);
		spell.setTarget(EntityReference.SELF);
		return Battlecry.createBattlecry(spell);
	}

}
