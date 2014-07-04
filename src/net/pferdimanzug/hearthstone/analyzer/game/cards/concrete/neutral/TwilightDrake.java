package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class TwilightDrake extends MinionCard {

	public TwilightDrake() {
		super("Twilight Drake", 4, 1, Rarity.RARE, HeroClass.ANY, 4);
		setDescription("Battlecry: Gain +1 Health for each card in your hand.");
		setRace(Race.DRAGON);
	}

	@Override
	public Minion summon() {
		Minion twilightDrake = createMinion();
		Spell buffSpell = new BuffSpell(null, (player, target) -> player.getHand().getCount());
		buffSpell.setTarget(EntityReference.SELF);
		Battlecry battlecry = Battlecry.createBattlecry(buffSpell);
		twilightDrake.setBattlecry(battlecry);
		return twilightDrake;
	}

}
