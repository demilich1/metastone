package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SecretCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AddSecretSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class MadScientist extends MinionCard {

	public MadScientist() {
		super("Mad Scientist", 2, 2, Rarity.COMMON, HeroClass.ANY, 2);
		setDescription("Deathrattle: Put a Secret from your deck into the battlefield.");
	}

	@Override
	public int getTypeId() {
		return 392;
	}
	
	@Override
	public Minion summon() {
		Minion madScientist = createMinion();
		Spell deathrattle = new PlayRandomSecretSpell();
		deathrattle.setTarget(EntityReference.NONE);
		madScientist.addDeathrattle(deathrattle);
		return madScientist;
	}



	private class PlayRandomSecretSpell extends Spell {
		private CardCollection findSecretCards(CardCollection cardCollection) {
			CardCollection secretCards = new CardCollection();
			for (Card card : cardCollection) {
				if (card.hasTag(GameTag.SECRET)) {
					secretCards.add(card);
				}
			}
			return secretCards;
		}

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			CardCollection secretCards = findSecretCards(player.getDeck());
			
			if (secretCards.isEmpty()) {
				return;
			}
			
			SecretCard secretCard = (SecretCard) secretCards.getRandom();
			AddSecretSpell secretSpell = (AddSecretSpell) secretCard.getSpell();
			context.getLogic().playSecret(player, secretSpell.getSecret());
			player.getDeck().remove(secretCard);
		}
		
	}
}
