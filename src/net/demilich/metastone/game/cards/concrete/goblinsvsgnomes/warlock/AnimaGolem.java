package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warlock;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnEndTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

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
