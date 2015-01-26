package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class ExplosiveSheep extends MinionCard {

	public ExplosiveSheep() {
		super("Explosive Sheep", 1, 1, Rarity.COMMON, HeroClass.ANY, 2);
		setDescription("Deathrattle: Deal 2 damage to all minions.");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 511;
	}



	@Override
	public Minion summon() {
		Minion explosiveSheep = createMinion();
		
		SpellDesc explosion = DamageSpell.create(2);
		explosion.setTarget(EntityReference.ALL_MINIONS);
		explosiveSheep.addDeathrattle(explosion);
		return explosiveSheep;
	}
}
