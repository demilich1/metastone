package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.ApplyTagSpell;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

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
		SpellDesc tauntSpell = ApplyTagSpell.create(GameTag.TAUNT);
		SpellDesc battlecrySpell = MetaSpell.create(EntityReference.ADJACENT_MINIONS, buffSpell, tauntSpell);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(battlecrySpell);
		battlecry.setResolvedLate(true);
		defenderOfArgus.setBattlecry(battlecry);
		return defenderOfArgus;
	}
}
