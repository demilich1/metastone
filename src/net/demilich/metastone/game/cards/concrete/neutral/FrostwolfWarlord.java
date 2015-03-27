package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.valueprovider.IValueProvider;
import net.demilich.metastone.game.targeting.EntityReference;

public class FrostwolfWarlord extends MinionCard {

	public FrostwolfWarlord() {
		super("Frostwolf Warlord", 4, 4, Rarity.FREE, HeroClass.ANY, 5);
		setDescription("Battlecry: Gain +1/+1 for each other friendly minion on the battlefield. ");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 131;
	}

	@Override
	public Minion summon() {
		Minion frostwolfWarlord = createMinion();
		IValueProvider valueProvider = (context, player, target) -> player.getMinions().size();
		SpellDesc buffSpell = BuffSpell.create(EntityReference.SELF, valueProvider, valueProvider);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(buffSpell);
		frostwolfWarlord.setBattlecry(battlecry);
		return frostwolfWarlord;
	}
}
