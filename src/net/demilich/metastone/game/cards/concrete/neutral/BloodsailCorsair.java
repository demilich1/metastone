package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.ChangeDurabilitySpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class BloodsailCorsair extends MinionCard {

	public BloodsailCorsair() {
		super("Bloodsail Corsair", 1, 2, Rarity.RARE, HeroClass.ANY, 1);
		setDescription("Battlecry: Remove 1 Durability from your opponent's weapon.");
		setRace(Race.PIRATE);
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 97;
	}

	@Override
	public Minion summon() {
		Minion bloodsailCorsair = createMinion();
		SpellDesc changeDurability = ChangeDurabilitySpell.create(-1);
		changeDurability.setTarget(EntityReference.ENEMY_HERO);
		bloodsailCorsair.setBattlecry(Battlecry.createBattlecry(changeDurability));
		return bloodsailCorsair;
	}
}
