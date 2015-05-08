package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class BloodsailRaider extends MinionCard {

	public BloodsailRaider() {
		super("Bloodsail Raider", 2, 3, Rarity.COMMON, HeroClass.ANY, 2);
		setDescription("Battlecry: Gain Attack equal to the Attack of your weapon.");
		setRace(Race.PIRATE);
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 98;
	}

	@Override
	public Minion summon() {
		Minion bloodsailRaider = createMinion();
		//SpellDesc buffSpell = BuffSpell.create(EntityReference.SELF, (context, player, target) -> player.getHero().getWeapon().getWeaponDamage(), null);
		SpellDesc buffSpell = BuffSpell.create(EntityReference.SELF, null, null);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(buffSpell);
		//battlecry.setCondition((context, player) -> player.getHero().getWeapon() != null);
		battlecry.setResolvedLate(true);
		bloodsailRaider.setBattlecry(battlecry);
		return bloodsailRaider;
	}
}
