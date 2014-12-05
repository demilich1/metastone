package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class ExplosiveSheep extends MinionCard {

	public ExplosiveSheep() {
		super("Explosive Sheep", 1, 1, Rarity.COMMON, HeroClass.ANY, 2);
		setDescription("Deathrattle: Deal 2 damage to all minions.");
		setRace(Race.MECH);
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
