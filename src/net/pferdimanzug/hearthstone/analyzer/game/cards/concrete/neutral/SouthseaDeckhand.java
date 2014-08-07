package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class SouthseaDeckhand extends MinionCard {

	public SouthseaDeckhand() {
		super("Southsea Deckhand", 2, 1, Rarity.COMMON, HeroClass.ANY, 1);
		setDescription("Has Charge while you have a weapon equipped.");
		setRace(Race.PIRATE);
	}

	@Override
	public int getTypeId() {
		return 202;
	}

	@Override
	public Minion summon() {
		SpellDesc chargeWhileWeaponEquipped = ApplyTagSpell.create(GameTag.CHARGE);
		chargeWhileWeaponEquipped.setTarget(EntityReference.SELF);
		Battlecry battlecry = Battlecry.createBattlecry(chargeWhileWeaponEquipped);
		battlecry.setCondition((context, player) -> player.getHero().getWeapon() != null);
		Minion southseaDeckhand = createMinion();
		southseaDeckhand.setBattlecry(battlecry);
		return southseaDeckhand;
	}
}
