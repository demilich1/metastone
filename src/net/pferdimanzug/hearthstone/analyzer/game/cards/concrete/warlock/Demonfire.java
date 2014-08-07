package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.EitherOrSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ISpellConditionChecker;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

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
