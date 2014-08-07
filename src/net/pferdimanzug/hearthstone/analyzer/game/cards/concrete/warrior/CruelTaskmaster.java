package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class CruelTaskmaster extends MinionCard {

	public CruelTaskmaster() {
		super("Cruel Taskmaster", 2, 2, Rarity.COMMON, HeroClass.WARRIOR, 2);
		setDescription("Battlecry: Deal 1 damage to a minion and give it +2 Attack.");
	}

	@Override
	public int getTypeId() {
		return 367;
	}

	@Override
	public Minion summon() {
		Minion cruelTaskmaster = createMinion();
		SpellDesc cruelBuffSpell = MetaSpell.create(BuffSpell.create(2, 0), DamageSpell.create(1));
		Battlecry battlecry = Battlecry.createBattlecry(cruelBuffSpell, TargetSelection.MINIONS);
		cruelTaskmaster.setBattlecry(battlecry);
		return cruelTaskmaster;
	}
}
