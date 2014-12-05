package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ConditionalAttackBonusSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.GameStateChangedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class GoblinSapper extends MinionCard {

	public GoblinSapper() {
		super("Goblin Sapper", 2, 4, Rarity.RARE, HeroClass.ANY, 3);
		setDescription("Has +4 Attack while your opponent has 6 or more cards in hand.");
	}

	@Override
	public int getTypeId() {
		return 520;
	}


	@Override
	public Minion summon() {
		Minion goblinSapper = createMinion();
		SpellDesc buffSpell = ConditionalAttackBonusSpell.create((context, player, target) -> {
			Player opponent = context.getOpponent(player);
			return opponent.getHand().getCount() >= 6 ? +4 : 0;
		});
		buffSpell.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new GameStateChangedTrigger(), buffSpell);
		goblinSapper.setSpellTrigger(trigger);
		return goblinSapper;
	}
}
