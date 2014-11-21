package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.ReceiveSparePartSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.DamageReceivedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

public class MechBearCat extends MinionCard {

	public MechBearCat() {
		super("Mech-Bear-Cat", 7, 6, Rarity.RARE, HeroClass.DRUID, 6);
		setDescription("Whenever this minion takes damage, add a Spare Part card to your hand.");
		setRace(Race.MECH);
	}

	@Override
	public Minion summon() {
		Minion mechBearCat = createMinion();
		SpellTrigger trigger = new SpellTrigger(new DamageReceivedTrigger(), ReceiveSparePartSpell.create(TargetPlayer.SELF));
		mechBearCat.setSpellTrigger(trigger);
		return mechBearCat;
	}

}
