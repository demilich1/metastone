package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class InjuredBlademaster extends MinionCard {

	public InjuredBlademaster() {
		super("Injured Blademaster", 4, 7, Rarity.RARE, HeroClass.ANY, 3);
		setDescription("Battlecry: Deal 4 damage to HIMSELF.");
	}

	@Override
	public Minion summon() {
		Minion injuredBlademaster = createMinion();
		Spell damageSpell = new DamageSpell(4);
		damageSpell.setTarget(EntityReference.SELF);
		Battlecry battlecry = Battlecry.createBattlecry(damageSpell);
		injuredBlademaster.setBattlecry(battlecry);
		return injuredBlademaster;
	}

}
