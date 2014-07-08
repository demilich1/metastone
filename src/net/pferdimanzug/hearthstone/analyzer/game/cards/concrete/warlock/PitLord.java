package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class PitLord extends MinionCard {

	public PitLord() {
		super("Pit Lord", 5, 6, Rarity.EPIC, HeroClass.WARLOCK, 4);
		setDescription("Battlecry: Deal 5 damage to your hero.");
	}

	@Override
	public Minion summon() {
		Minion pitLord = createMinion();
		Spell damageHeroSpell = new DamageSpell(3);
		damageHeroSpell.setTarget(EntityReference.FRIENDLY_HERO);
		Battlecry battlecry = Battlecry.createBattlecry(damageHeroSpell);
		pitLord.setBattlecry(battlecry);
		return pitLord;
	}

}
