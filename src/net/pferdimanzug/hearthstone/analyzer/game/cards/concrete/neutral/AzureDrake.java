package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;

public class AzureDrake extends MinionCard {

	public AzureDrake() {
		super("Azure Drake", 4, 4, Rarity.RARE, HeroClass.ANY, 5);
		setDescription("Spell Damage +1. Battlecry: Draw a card.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 91;
	}

	@Override
	public Minion summon() {
		Minion azureDrake = createMinion();
		azureDrake.setTag(GameTag.SPELL_POWER, 1);
		azureDrake.setBattlecry(Battlecry.createBattlecry(new DrawCardSpell()));
		return azureDrake;
	}
}
