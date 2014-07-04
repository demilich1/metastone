package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonRandomSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class BaneOfDoom extends SpellCard {

	public BaneOfDoom() {
		super("Bane of Doom", Rarity.EPIC, HeroClass.WARLOCK, 5);
		setDescription("Deal 2 damage to a character.  If that kills it, summon a random Demon.");
		
		setSpell(new BaneOfDoomSpell());
		setTargetRequirement(TargetSelection.ANY);
	}
	
	private class BaneOfDoomSpell extends DamageSpell {

		public BaneOfDoomSpell() {
			super(2);
		}

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			super.onCast(context, player, target);
			Actor targetActor = (Actor) target;
			if (targetActor.isDead()) {
				SummonRandomSpell summonRandomSpell = new SummonRandomSpell(
						new BloodImp(),
						new Voidwalker(),
						new FlameImp(),
						new Succubus(),
						new Felguard(),
						new DreadInfernal()
						);
				context.getLogic().castSpell(player.getId(), summonRandomSpell);
			}
		}
		
	}

}
