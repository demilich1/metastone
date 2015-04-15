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

public class TwilightDrake extends MinionCard {

	public TwilightDrake() {
		super("Twilight Drake", 4, 1, Rarity.RARE, HeroClass.ANY, 4);
		setDescription("Battlecry: Gain +1 Health for each card in your hand.");
		setRace(Race.DRAGON);
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 220;
	}

	@Override
	public Minion summon() {
		Minion twilightDrake = createMinion();
		//SpellDesc buffSpell = BuffSpell.create(EntityReference.SELF, null, (context, player, target) -> player.getHand().getCount());
		SpellDesc buffSpell = BuffSpell.create(EntityReference.SELF, null, null);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(buffSpell);
		twilightDrake.setBattlecry(battlecry);
		return twilightDrake;
	}
}
