package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class CrazedAlchemist extends MinionCard {

	private class SwapAttackAndHpSpell extends Spell {

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			Actor targetActor = (Actor) target;
			int attackBonus = targetActor.getHp() - targetActor.getTagValue(GameTag.BASE_ATTACK);
			int hpBonus = targetActor.getAttack() - targetActor.getTagValue(GameTag.MAX_HP);
			targetActor.setTag(GameTag.ATTACK_BONUS, attackBonus);
			targetActor.setTag(GameTag.HP_BONUS, hpBonus);
			targetActor.setHp(targetActor.getMaxHp());
		}
		
	}

	public CrazedAlchemist() {
		super("Crazed Alchemist", 2, 2, Rarity.RARE, HeroClass.ANY, 2);
		setDescription("Battlecry: Swap the Attack and Health of a minion.");
	}
	
	@Override
	public int getTypeId() {
		return 109;
	}



	@Override
	public Minion summon() {
		Minion crazedAlchemist = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(new SwapAttackAndHpSpell(), TargetSelection.MINIONS);
		crazedAlchemist.setBattlecry(battlecry);
		return crazedAlchemist;
	}
}
