package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.enrage.EnrageBuffWeapon;

public class SpitefulSmith extends MinionCard {

	public SpitefulSmith() {
		super("Spiteful Smith", 4, 6, Rarity.COMMON, HeroClass.ANY, 5);
		setDescription("Enrage: Your weapon has +2 Attack.");
	}

	@Override
	public int getTypeId() {
		return 204;
	}

	@Override
	public Minion summon() {
		Minion spitefulSmith = createMinion();
		spitefulSmith.setTag(GameTag.ENRAGE_SPELL, EnrageBuffWeapon.create(2));
		return spitefulSmith;
	}
}
