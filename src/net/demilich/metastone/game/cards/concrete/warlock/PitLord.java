package net.demilich.metastone.game.cards.concrete.warlock;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class PitLord extends MinionCard {

	public PitLord() {
		super("Pit Lord", 5, 6, Rarity.EPIC, HeroClass.WARLOCK, 4);
		setDescription("Battlecry: Deal 5 damage to your hero.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 346;
	}

	@Override
	public Minion summon() {
		Minion pitLord = createMinion();
		SpellDesc damageHeroSpell = DamageSpell.create(3);
		damageHeroSpell.setTarget(EntityReference.FRIENDLY_HERO);
		Battlecry battlecry = Battlecry.createBattlecry(damageHeroSpell);
		pitLord.setBattlecry(battlecry);
		return pitLord;
	}
}
