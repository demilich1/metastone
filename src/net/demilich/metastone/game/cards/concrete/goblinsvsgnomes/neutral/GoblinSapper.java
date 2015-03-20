package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.ConditionalAttackBonusSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.GameStateChangedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

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
		SpellDesc buffSpell = ConditionalAttackBonusSpell.create(EntityReference.SELF, (context, player, target) -> {
			Player opponent = context.getOpponent(player);
			return opponent.getHand().getCount() >= 6 ? +4 : 0;
		});
		SpellTrigger trigger = new SpellTrigger(new GameStateChangedTrigger(), buffSpell);
		goblinSapper.setSpellTrigger(trigger);
		return goblinSapper;
	}
}
