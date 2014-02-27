package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class CrazedAlchemist extends MinionCard {

	public CrazedAlchemist() {
		super("Crazed Alchemist", 2, 2, Rarity.RARE, HeroClass.ANY, 2);
		setDescription("Battlecry: Swap the Attack and Health of a minion.");
	}

	@Override
	public Minion summon() {
		Minion crazedAlchemist = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(new SwapAttackAndHpSpell(), TargetSelection.MINIONS);
		crazedAlchemist.setBattlecry(battlecry);
		return crazedAlchemist;
	}
	
	private class SwapAttackAndHpSpell extends Spell {

		@Override
		protected void onCast(GameContext context, Player player, Actor target) {
			int attackBonus = target.getHp() - target.getTagValue(GameTag.BASE_ATTACK);
			int hpBonus = target.getAttack() - target.getTagValue(GameTag.MAX_HP);
			target.setTag(GameTag.ATTACK_BONUS, attackBonus);
			target.setTag(GameTag.HP_BONUS, hpBonus);
			target.setHp(target.getMaxHp());
		}
		
	}

}
