package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.IValueProvider;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class LilExorcist extends MinionCard {

	public LilExorcist() {
		super("Lil' Exorcist", 2, 3, Rarity.RARE, HeroClass.ANY, 3);
		setDescription("Taunt. Battlecry: Gain +1/+1 for each enemy Deathrattle minion.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public Minion summon() {
		Minion lilExorcist = createMinion(GameTag.TAUNT);
		IValueProvider valueProvider = new IValueProvider() {

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
		SpellDesc buffSpell = BuffSpell.create(valueProvider, valueProvider);
		buffSpell.setTarget(EntityReference.SELF);
		Battlecry battlecry = Battlecry.createBattlecry(buffSpell);
		lilExorcist.setBattlecry(battlecry);
		return lilExorcist;
	}

}
