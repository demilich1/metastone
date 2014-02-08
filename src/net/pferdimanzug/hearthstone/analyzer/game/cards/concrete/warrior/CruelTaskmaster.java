package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class CruelTaskmaster extends MinionCard {

	public CruelTaskmaster() {
		super("Cruel Taskmaster", 2, 2, Rarity.COMMON, HeroClass.WARRIOR, 2);
	}

	@Override
	public Minion summon() {
		Minion cruelTaskmaster = createMinion();
		Spell cruelBuffSpell = new MetaSpell(new BuffSpell(2, 0), new DamageSpell(1));
		Battlecry battlecry = Battlecry.createBattlecry(cruelBuffSpell, TargetSelection.MINIONS);
		cruelTaskmaster.setTag(GameTag.BATTLECRY, battlecry);
		return cruelTaskmaster;
	}
}
