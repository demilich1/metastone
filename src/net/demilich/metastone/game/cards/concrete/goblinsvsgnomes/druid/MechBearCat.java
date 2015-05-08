package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.druid;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.custom.ReceiveSparePartSpell;
import net.demilich.metastone.game.spells.trigger.DamageReceivedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

public class MechBearCat extends MinionCard {

	public MechBearCat() {
		super("Mech-Bear-Cat", 7, 6, Rarity.RARE, HeroClass.DRUID, 6);
		setDescription("Whenever this minion takes damage, add a Spare Part card to your hand.");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 481;
	}



	@Override
	public Minion summon() {
		Minion mechBearCat = createMinion();
		SpellTrigger trigger = new SpellTrigger(new DamageReceivedTrigger(null), ReceiveSparePartSpell.create(TargetPlayer.SELF));
		mechBearCat.setSpellTrigger(trigger);
		return mechBearCat;
	}
}
