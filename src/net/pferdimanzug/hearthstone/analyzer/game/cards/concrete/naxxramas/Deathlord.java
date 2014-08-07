package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.DeathlordSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Deathlord extends MinionCard {

	public Deathlord() {
		super("Deathlord", 2, 8, Rarity.RARE, HeroClass.ANY, 3);
		setDescription("Taunt. Deathrattle: Your opponent puts a minion from their deck into the battlefield.");
	}

	@Override
	public int getTypeId() {
		return 405;
	}
	
	@Override
	public Minion summon() {
		Minion deathlord = createMinion(GameTag.TAUNT);
		SpellDesc deathlordSpell = DeathlordSpell.create();
		deathlordSpell.setTarget(EntityReference.NONE);
		deathlord.addDeathrattle(deathlordSpell);
		return deathlord;
	}

	
}
