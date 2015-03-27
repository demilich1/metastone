package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warlock;

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

public class Demonheart extends SpellCard {

	public Demonheart() {
		super("Demonheart", Rarity.EPIC, HeroClass.WARLOCK, 5);
		setDescription("Deal $5 damage to a minion. If it's a friendly Demon, give it +5/+5 instead.");
		SpellDesc buffSpell = BuffSpell.create(2, 2);
		SpellDesc damageSpell = DamageSpell.create(2);
		ISpellConditionChecker isFriendlyMinion = new ISpellConditionChecker() {

			@Override
			public boolean isFulfilled(GameContext context, Player player, Entity target) {
				Minion minion = (Minion) target;
				return minion.getOwner() == player.getId() && minion.getRace() == Race.DEMON;
			}
		};
		SpellDesc demonheartSpell = EitherOrSpell.create(buffSpell, damageSpell, isFriendlyMinion);
		setSpell(demonheartSpell);
		setTargetRequirement(TargetSelection.MINIONS);

	}



	@Override
	public int getTypeId() {
		return 597;
	}
}
