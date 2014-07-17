package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.IValueProvider;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class KillCommand extends SpellCard {

	public KillCommand() {
		super("Kill Command", Rarity.FREE, HeroClass.HUNTER, 3);
		setDescription("Deal $3 damage. If you have a Beast, deal $5 damage instead.");
		IValueProvider hasBeast = new IValueProvider() {

			private boolean hasBeast(Player player) {
				for (Minion minion : player.getMinions()) {
					if (minion.getRace() == Race.BEAST) {
						return true;
					}
				}
				return false;
			}

			@Override
			public int provideValue(GameContext context, Player player, Entity target) {
				return hasBeast(player) ? 5 : 3;
			}
		};
		setSpell(new DamageSpell(hasBeast));
		setTargetRequirement(TargetSelection.ANY);
	}



	@Override
	public int getTypeId() {
		return 38;
	}
}
