package net.demilich.metastone.game.cards.concrete.hunter;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.SpellUtils;
import net.demilich.metastone.game.spells.desc.valueprovider.ValueProvider;
import net.demilich.metastone.game.targeting.TargetSelection;

public class KillCommand extends SpellCard {

	public KillCommand() {
		super("Kill Command", Rarity.FREE, HeroClass.HUNTER, 3);
		setDescription("Deal $3 damage. If you have a Beast, deal $5 damage instead.");
		ValueProvider hasBeast = new ValueProvider(null) {

			@Override
			public int provideValue(GameContext context, Player player, Entity target) {
				return SpellUtils.hasMinionOfRace(player, Race.BEAST) ? 5 : 3;
			}
		};
		setSpell(DamageSpell.create(hasBeast));
		setTargetRequirement(TargetSelection.ANY);
	}

	@Override
	public int getTypeId() {
		return 38;
	}
}
