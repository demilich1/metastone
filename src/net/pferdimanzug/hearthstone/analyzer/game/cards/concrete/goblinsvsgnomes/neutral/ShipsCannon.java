package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.MinionSummonedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class ShipsCannon extends MinionCard {

	public ShipsCannon() {
		super("Ship's Cannon", 2, 3, Rarity.COMMON, HeroClass.ANY, 2);
		setDescription("Whenever you summon a Pirate, deal 2 damage to a random enemy.");
	}

	@Override
	public int getTypeId() {
		return 543;
	}



	@Override
	public Minion summon() {
		Minion shipsCannon = createMinion();
		SpellDesc damage = DamageSpell.create(2);
		damage.setTarget(EntityReference.ENEMY_CHARACTERS);
		damage.pickRandomTarget(true);
		SpellTrigger trigger = new SpellTrigger(new MinionSummonedTrigger(TargetPlayer.SELF, Race.PIRATE), damage);
		shipsCannon.setSpellTrigger(trigger);
		return shipsCannon;
	}
}
