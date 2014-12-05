package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class AnimaGolem extends MinionCard {

	public AnimaGolem() {
		super("Anima Golem", 9, 9, Rarity.EPIC, HeroClass.WARLOCK, 6);
		setDescription("At the end of each turn, destroy this minion if it's your only one.");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 595;
	}

	@Override
	public Minion summon() {
		Minion animaGolem = createMinion();
		SpellDesc destroySpell = DestroySpell.create();
		destroySpell.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new AnimaGolemTrigger(), destroySpell);
		animaGolem.setSpellTrigger(trigger);
		return animaGolem;
	}



	private class AnimaGolemTrigger extends TurnEndTrigger {
		public AnimaGolemTrigger() {
			super(TargetPlayer.BOTH);
		}

		@Override
		public boolean fire(GameEvent event, Entity host) {
			if (!super.fire(event, host)) {
				return false;
			}
			Player owner = event.getGameContext().getPlayer(getOwner());
			return owner.getMinions().size() < 2;
		}

	}
}
