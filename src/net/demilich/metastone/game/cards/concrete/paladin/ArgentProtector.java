package net.demilich.metastone.game.cards.concrete.paladin;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.ApplyTagSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class ArgentProtector extends MinionCard {

	public ArgentProtector() {
		super("Argent Protector", 2, 2, Rarity.COMMON, HeroClass.PALADIN, 2);
		setDescription("Battlecry: Give a friendly minion Divine Shield.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 235;
	}

	@Override
	public Minion summon() {
		Minion argentProtector = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(ApplyTagSpell.create(GameTag.DIVINE_SHIELD), TargetSelection.FRIENDLY_MINIONS);
		argentProtector.setBattlecry(battlecry);
		return argentProtector;
	}
}
