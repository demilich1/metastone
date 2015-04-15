package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.valueprovider.ValueProvider;
import net.demilich.metastone.game.targeting.EntityReference;

public class LilExorcist extends MinionCard {

	public LilExorcist() {
		super("Lil' Exorcist", 2, 3, Rarity.RARE, HeroClass.ANY, 3);
		setDescription("Taunt. Battlecry: Gain +1/+1 for each enemy Deathrattle minion.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 527;
	}



	@Override
	public Minion summon() {
		Minion lilExorcist = createMinion(GameTag.TAUNT);
		ValueProvider valueProvider = new ValueProvider(null) {

			@Override
			public int provideValue(GameContext context, Player player, Entity target) {
				Player opponent = context.getOpponent(player);
				int deathrattleCount = 0;
				for (Minion minion : opponent.getMinions()) {
					if (minion.hasTag(GameTag.DEATHRATTLES)) {
						deathrattleCount++;
					}
				}
				return deathrattleCount;
			}
		};
		SpellDesc buffSpell = BuffSpell.create(EntityReference.SELF, valueProvider, valueProvider);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(buffSpell);
		lilExorcist.setBattlecry(battlecry);
		return lilExorcist;
	}
}
