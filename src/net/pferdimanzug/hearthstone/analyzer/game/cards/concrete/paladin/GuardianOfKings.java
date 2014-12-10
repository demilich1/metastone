package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
