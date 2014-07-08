package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.ChooseBattlecryCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
		Spell buffSpell = new BuffSpell(5);
		buffSpell.setTarget(EntityReference.SELF);
		return Battlecry.createBattlecry(buffSpell);
	}

	@Override
	protected Battlecry getBattlecry2() {
		Spell buffHpSpell = new BuffSpell(0, 5);
		buffHpSpell.setTarget(EntityReference.SELF);
		Spell tauntUpSpell = new ApplyTagSpell(GameTag.TAUNT);
		tauntUpSpell.setTarget(EntityReference.SELF);
		return Battlecry.createBattlecry(new MetaSpell(buffHpSpell, tauntUpSpell));
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
	
	
}
