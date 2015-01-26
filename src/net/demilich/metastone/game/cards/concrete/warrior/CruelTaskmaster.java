package net.demilich.metastone.game.cards.concrete.warrior;

import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

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
