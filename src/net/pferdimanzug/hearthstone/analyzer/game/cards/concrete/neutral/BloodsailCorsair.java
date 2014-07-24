package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ChangeDurabilitySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
		Spell changeDurability = new ChangeDurabilitySpell(-1);
		changeDurability.setTarget(EntityReference.ENEMY_HERO);
		bloodsailCorsair.setBattlecry(Battlecry.createBattlecry(changeDurability));
		return bloodsailCorsair;
	}
}
