package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class FlameImp extends MinionCard {

	public FlameImp() {
		super("Flame Imp", 3, 2, Rarity.COMMON, HeroClass.WARLOCK, 1);
		setDescription("Battlecry: Deal 3 damage to your hero.");
		setTag(GameTag.RACE, Race.DEMON);
	}

	@Override
	public Minion summon() {
		Minion flameImp = createMinion();
		Spell damageHeroSpell = new DamageSpell(3);
		damageHeroSpell.setTarget(EntityReference.FRIENDLY_HERO);
		Battlecry battlecry = Battlecry.createBattlecry(damageHeroSpell, TargetSelection.NONE);
		flameImp.setBattlecry(battlecry);
		return flameImp;
	}

}
