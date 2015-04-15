package net.demilich.metastone.game.cards.concrete.warrior;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.valueprovider.ValueProvider;
import net.demilich.metastone.game.targeting.TargetSelection;

public class BattleRage extends SpellCard {

	public BattleRage() {
		super("Battle Rage", Rarity.COMMON, HeroClass.WARRIOR, 2);
		setDescription("Draw a card for each damaged friendly character.");
		ValueProvider damagedCharacterCounter = new ValueProvider(null) {

			@Override
			public int provideValue(GameContext context, Player player, Entity target) {
				int woundedCharacters = player.getHero().isWounded() ? 1 : 0;
				for (Actor minion : player.getMinions()) {
					if (minion.isWounded()) {
						woundedCharacters++;
					}
				}
				return woundedCharacters;
			}
		};
		setSpell(DrawCardSpell.create(damagedCharacterCounter, TargetPlayer.SELF));
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 362;
	}
}
