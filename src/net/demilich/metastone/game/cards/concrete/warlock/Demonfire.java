package net.demilich.metastone.game.cards.concrete.warlock;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.EitherOrSpell;
import net.demilich.metastone.game.spells.desc.ISpellConditionChecker;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Demonfire extends SpellCard {

	public Demonfire() {
		super("Demonfire", Rarity.COMMON, HeroClass.WARLOCK, 2);
		setDescription("Deal $2 damage to a minion. If it's a friendly Demon, give it +2/+2 instead.");
		SpellDesc buffSpell = BuffSpell.create(2, 2);
		SpellDesc damageSpell = DamageSpell.create(2);
		ISpellConditionChecker isFriendlyMinion = new ISpellConditionChecker() {

			@Override
			public boolean isFulfilled(GameContext context, Player player, Entity target) {
				Minion minion = (Minion) target;
				return minion.getOwner() == player.getId() && minion.getRace() == Race.DEMON;
			}
		};
		SpellDesc demonfireSpell = EitherOrSpell.create(buffSpell, damageSpell, isFriendlyMinion);
		setSpell(demonfireSpell);
		setTargetRequirement(TargetSelection.MINIONS);
	}

	@Override
	public int getTypeId() {
		return 337;
	}
}
