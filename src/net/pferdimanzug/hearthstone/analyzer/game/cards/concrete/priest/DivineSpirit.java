package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class DivineSpirit extends SpellCard {

	private class DivineSpiritSpell extends Spell {

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			Actor targetActor = (Actor) target;
			int hpBonus = targetActor.getHp();
			targetActor.modifyHpBonus(hpBonus);
		}
		
	}
	
	public DivineSpirit() {
		super("Divine Spirit", Rarity.FREE, HeroClass.PRIEST, 2);
		setDescription("Double a minion's Health.");
		setSpell(new DivineSpiritSpell());
		setTargetRequirement(TargetSelection.MINIONS);
	}



	@Override
	public int getTypeId() {
		return 262;
	}
}
