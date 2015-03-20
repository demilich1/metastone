package net.demilich.metastone.game.cards.concrete.warlock;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class FlameImp extends MinionCard {

	public FlameImp() {
		super("Flame Imp", 3, 2, Rarity.COMMON, HeroClass.WARLOCK, 1);
		setDescription("Battlecry: Deal 3 damage to your hero.");
		setRace(Race.DEMON);
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 342;
	}

	@Override
	public Minion summon() {
		Minion flameImp = createMinion();
		SpellDesc damageHeroSpell = DamageSpell.create(EntityReference.FRIENDLY_HERO, 3);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(damageHeroSpell);
		flameImp.setBattlecry(battlecry);
		return flameImp;
	}
}
