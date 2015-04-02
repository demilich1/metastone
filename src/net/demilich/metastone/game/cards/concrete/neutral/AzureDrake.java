package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.DrawCardSpell;

public class AzureDrake extends MinionCard {

	public AzureDrake() {
		super("Azure Drake", 4, 4, Rarity.RARE, HeroClass.ANY, 5);
		setDescription("Spell Damage +1. Battlecry: Draw a card.");
		setTag(GameTag.BATTLECRY);
		setRace(Race.DRAGON);
	}

	@Override
	public int getTypeId() {
		return 91;
	}

	@Override
	public Minion summon() {
		Minion azureDrake = createMinion();
		azureDrake.setTag(GameTag.SPELL_DAMAGE, 1);
		azureDrake.setBattlecry(BattlecryAction.createBattlecry(DrawCardSpell.create()));
		return azureDrake;
	}
}
