package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class DefenderOfArgus extends MinionCard {

	public DefenderOfArgus() {
		super("Defender of Argus", 2, 3, Rarity.RARE, HeroClass.ANY, 4);
		setDescription("Battlecry: Give adjacent minions +1/+1 and Taunt.");
	}

	@Override
	public int getTypeId() {
		return 115;
	}

	@Override
	public Minion summon() {
		Minion defenderOfArgus = createMinion();
		SpellDesc buffSpell = BuffSpell.create(+1, +1);
		buffSpell.setTarget(EntityReference.ADJACENT_MINIONS);
		SpellDesc tauntSpell = ApplyTagSpell.create(GameTag.TAUNT);
		tauntSpell.setTarget(EntityReference.ADJACENT_MINIONS);
		SpellDesc battlecrySpell = MetaSpell.create(buffSpell, tauntSpell);
		battlecrySpell.setTarget(EntityReference.NONE);
		Battlecry battlecry = Battlecry.createBattlecry(buffSpell);
		battlecry.setResolvedLate(true);
		defenderOfArgus.setBattlecry(battlecry);
		return defenderOfArgus;
	}
}
