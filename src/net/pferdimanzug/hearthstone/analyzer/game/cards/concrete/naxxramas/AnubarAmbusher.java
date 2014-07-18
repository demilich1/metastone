package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ReturnRandomMinionToHandSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class AnubarAmbusher extends MinionCard {

	public AnubarAmbusher() {
		super("Anub'ar Ambusher", 5, 5, Rarity.COMMON, HeroClass.ROGUE, 4);
		setDescription("Deathrattle: Return a friendly minion to your hand.");
	}

	@Override
	public Minion summon() {
		Minion anubarAmbusher = createMinion();
		Spell deathrattle = new ReturnRandomMinionToHandSpell();
		deathrattle.setTarget(EntityReference.FRIENDLY_MINIONS);
		anubarAmbusher.addDeathrattle(deathrattle);
		return anubarAmbusher;
	}

}
