package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.ApplyTagSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class SunfuryProtector extends MinionCard {

	public SunfuryProtector() {
		super("Sunfury Protector", 2, 3, Rarity.RARE, HeroClass.ANY, 2);
		setDescription("Battlecry: Give adjacent minions Taunt.");
	}

	@Override
	public int getTypeId() {
		return 211;
	}



	@Override
	public Minion summon() {
		Minion sunfuryProtector = createMinion();
		SpellDesc buffSpell = ApplyTagSpell.create(EntityReference.ADJACENT_MINIONS, GameTag.TAUNT);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(buffSpell, TargetSelection.NONE);
		battlecry.setResolvedLate(true);
		sunfuryProtector.setBattlecry(battlecry);
		return sunfuryProtector;
	}
}
