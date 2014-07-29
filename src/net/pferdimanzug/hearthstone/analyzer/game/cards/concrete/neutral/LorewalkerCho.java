package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellCastedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class LorewalkerCho extends MinionCard {

	public LorewalkerCho() {
		super("Lorewalker Cho", 0, 4, Rarity.LEGENDARY, HeroClass.ANY, 2);
		setDescription("Whenever a player casts a spell, put a copy into the other player's hand.");
	}

	@Override
	public int getTypeId() {
		return 158;
	}

	@Override
	public Minion summon() {
		Minion lorewalkerCho = createMinion();
		Spell copySpell = new LorewalkerChoSpell();
		copySpell.setTarget(EntityReference.EVENT_TARGET);
		SpellTrigger trigger = new SpellTrigger(new SpellCastedTrigger(TargetPlayer.BOTH), copySpell);
		lorewalkerCho.setSpellTrigger(trigger);
		return lorewalkerCho;
	}


	private class LorewalkerChoSpell extends Spell {

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			Card targetCard = (Card) target;
			Player opponent = context.getOpponent(player);
			context.getLogic().receiveCard(opponent.getId(), targetCard.clone());
		}
	}
}
