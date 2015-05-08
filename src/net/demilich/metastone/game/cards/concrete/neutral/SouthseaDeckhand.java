package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.AddAttributeSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

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
		SpellDesc chargeWhileWeaponEquipped = AddAttributeSpell.create(EntityReference.SELF, GameTag.CHARGE);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(chargeWhileWeaponEquipped);
		//battlecry.setCondition((context, player) -> player.getHero().getWeapon() != null);
		Minion southseaDeckhand = createMinion();
		southseaDeckhand.setBattlecry(battlecry);
		return southseaDeckhand;
	}
}
