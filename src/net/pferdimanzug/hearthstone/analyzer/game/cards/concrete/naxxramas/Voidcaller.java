package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SpellUtils;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Voidcaller extends MinionCard {

	public Voidcaller() {
		super("Voidcaller", 3, 4, Rarity.COMMON, HeroClass.WARLOCK, 4);
		setDescription("Deathrattle: Put a random Demon from your hand into the battlefield.");
		setRace(Race.DEMON);
	}

	@Override
	public Minion summon() {
		Minion voidcaller = createMinion();
		Spell voidcallerSpell = new VoidcallerSpell();
		voidcallerSpell.setTarget(EntityReference.NONE);
		voidcaller.addDeathrattle(voidcallerSpell);
		return voidcaller;
	}
	
	private class VoidcallerSpell extends Spell {

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			CardCollection demonsInHand = SpellUtils.getCards(player.getHand(), card -> card.getTag(GameTag.RACE) == Race.DEMON);
			if (demonsInHand.isEmpty()) {
				return;
			}
			
			MinionCard randomDemonCard = (MinionCard) demonsInHand.getRandom();
			context.getLogic().summon(player.getId(), randomDemonCard.summon(), null, null, false);
		}
		
	}

}
