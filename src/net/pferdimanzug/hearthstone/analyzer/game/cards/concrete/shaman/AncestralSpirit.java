package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class AncestralSpirit extends SpellCard {

	public AncestralSpirit() {
		super("Ancestral Spirit", Rarity.RARE, HeroClass.SHAMAN, 2);
		setDescription("Choose a minion. When that minion is destroyed, return it to the battlefield.");

		setSpell(new AncestralSpiritSpell());
		setTargetRequirement(TargetSelection.MINIONS);
	}

	@Override
	public int getTypeId() {
		return 311;
	}



	private class AncestralSpiritSpell extends Spell {

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			Actor targetMinion = (Actor) target;
			MinionCard minionCard = (MinionCard) targetMinion.getSourceCard();
			targetMinion.addDeathrattle(new SummonSpell(minionCard));
		}
	}
}
