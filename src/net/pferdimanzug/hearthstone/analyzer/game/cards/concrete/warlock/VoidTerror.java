package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class VoidTerror extends MinionCard {

	public VoidTerror() {
		super("Void Terror", 3, 3, Rarity.RARE, HeroClass.WARLOCK, 3);
		setDescription("Battlecry: Destroy the minions on either side of this minion and gain their Attack and Health.");
		setRace(Race.DEMON);
	}

	@Override
	public Minion summon() {
		Minion voidTerror = createMinion();
		Spell buffSpell = new VoidTerrorBuffSpell();
		buffSpell.setTarget(EntityReference.SELF);
		Spell destroySpell = new DestroySpell();
		destroySpell.setTarget(EntityReference.ADJACENT_MINIONS);
		Battlecry battlecry = Battlecry.createBattlecry(new MetaSpell(buffSpell, destroySpell));
		battlecry.setResolvedLate(true);
		voidTerror.setBattlecry(battlecry);
		return voidTerror;
	}

	private class VoidTerrorBuffSpell extends Spell {

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			int attackBonus = 0;
			int hpBonus = 0;
			for (Entity adjacent : context.getAdjacentMinions(player, target.getReference())) {
				Minion minion = (Minion) adjacent;
				attackBonus += minion.getAttack();
				hpBonus += minion.getHp();
			}
			BuffSpell buffSpell = new BuffSpell(attackBonus, hpBonus);
			buffSpell.setTarget(target.getReference());
			context.getLogic().castSpell(player.getId(), buffSpell);
		}

	}

}
