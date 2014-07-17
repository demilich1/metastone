package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SpellSource;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Shadowflame extends SpellCard {

	private class ShadowflameSpell extends DestroySpell {

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			Minion targetMinion = (Minion) target;

			// deal attack damage to all enemy minions
			DamageSpell damageSpell = new DamageSpell(targetMinion.getAttack());
			damageSpell.setSource(SpellSource.SPELL_CARD);
			damageSpell.setTarget(EntityReference.ENEMY_MINIONS);
			context.getLogic().castSpell(player.getId(), damageSpell);

			// destroy minion
			super.onCast(context, player, target);
		}

	}

	public Shadowflame() {
		super("Shadowflame", Rarity.RARE, HeroClass.WARLOCK, 4);
		setDescription("Destroy a friendly minion and deal its Attack damage to all enemy minions.");

		setSpell(new ShadowflameSpell());
		setTargetRequirement(TargetSelection.FRIENDLY_MINIONS);
	}



	@Override
	public int getTypeId() {
		return 351;
	}
}
