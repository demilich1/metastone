package net.demilich.metastone.game.cards.concrete.paladin;

import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.HealingSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class GuardianOfKings extends MinionCard {

	public GuardianOfKings() {
		super("Guardian of Kings", 5, 6, Rarity.FREE, HeroClass.PALADIN, 7);
		setDescription("Battlecry: Restore #6 Health to your hero.");
	}

	@Override
	public int getTypeId() {
		return 245;
	}

	@Override
	public Minion summon() {
		Minion guardianOfKings = createMinion();
		SpellDesc healHero = HealingSpell.create(6);
		healHero.setTarget(EntityReference.FRIENDLY_HERO);
		Battlecry battlecry = Battlecry.createBattlecry(healHero);
		guardianOfKings.setBattlecry(battlecry);
		return guardianOfKings;
	}
}
