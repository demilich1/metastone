package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.MinionSummonedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

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
		SpellDesc damage = DamageSpell.create(EntityReference.ENEMY_CHARACTERS, 2, true);
		//SpellTrigger trigger = new SpellTrigger(new MinionSummonedTrigger(TargetPlayer.SELF, Race.PIRATE), damage);
		SpellTrigger trigger = new SpellTrigger(new MinionSummonedTrigger(null), damage);
		shipsCannon.setSpellTrigger(trigger);
		return shipsCannon;
	}
}
