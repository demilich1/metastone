package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.PutRandomMinionOnBoardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Voidcaller extends MinionCard {

	public Voidcaller() {
		super("Voidcaller", 3, 4, Rarity.COMMON, HeroClass.WARLOCK, 4);
		setDescription("Deathrattle: Put a random Demon from your hand into the battlefield.");
		setRace(Race.DEMON);
	}

	@Override
	public int getTypeId() {
		return 411;
	}
	
	@Override
	public Minion summon() {
		Minion voidcaller = createMinion();
		SpellDesc voidcallerSpell = PutRandomMinionOnBoardSpell.create(TargetPlayer.SELF, Race.DEMON);
		voidcallerSpell.setTarget(EntityReference.NONE);
		voidcaller.addDeathrattle(voidcallerSpell);
		return voidcaller;
	}

	
}
