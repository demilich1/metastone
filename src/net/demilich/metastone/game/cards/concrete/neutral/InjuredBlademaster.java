package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class InjuredBlademaster extends MinionCard {

	public InjuredBlademaster() {
		super("Injured Blademaster", 4, 7, Rarity.RARE, HeroClass.ANY, 3);
		setDescription("Battlecry: Deal 4 damage to HIMSELF.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 145;
	}

	@Override
	public Minion summon() {
		Minion injuredBlademaster = createMinion();
		SpellDesc damageSpell = DamageSpell.create(4);
		damageSpell.setTarget(EntityReference.SELF);
		Battlecry battlecry = Battlecry.createBattlecry(damageSpell);
		injuredBlademaster.setBattlecry(battlecry);
		return injuredBlademaster;
	}
}
