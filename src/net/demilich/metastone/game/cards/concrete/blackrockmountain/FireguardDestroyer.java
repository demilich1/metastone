package net.demilich.metastone.game.cards.concrete.blackrockmountain;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class FireguardDestroyer extends MinionCard {

	public FireguardDestroyer() {
		super("Fireguard Destroyer", 3, 6, Rarity.COMMON, HeroClass.SHAMAN, 4);
		setDescription("Battlecry: Gain 1-4 Attack. Overload: (1)");
		setTag(GameTag.BATTLECRY);
		setTag(GameTag.OVERLOAD, 1);
	}

	@Override
	public Minion summon() {
		Minion fireguardDestroyer = createMinion();
		//IValueProvider valueProvider = (context, player, target) -> 1 + context.getLogic().random(4);
		SpellDesc buffSpell = BuffSpell.create(EntityReference.SELF, null, null);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(buffSpell);
		fireguardDestroyer.setBattlecry(battlecry);
		return fireguardDestroyer;
	}



	@Override
	public int getTypeId() {
		return 633;
	}
}
