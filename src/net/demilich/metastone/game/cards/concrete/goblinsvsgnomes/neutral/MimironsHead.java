package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.tokens.V07TR0N;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnStartTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class MimironsHead extends MinionCard {

	public MimironsHead() {
		super("Mimiron's Head", 4, 5, Rarity.LEGENDARY, HeroClass.ANY, 5);
		setDescription("At the start of your turn, if you have at least 3 Mechs, destroy them all, and form V-07-TR-0N.");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 534;
	}

	@Override
	public Minion summon() {
		Minion mimironsHead = createMinion();
		SpellDesc destroyAllMechs = DestroySpell.create(EntityReference.FRIENDLY_MINIONS, entity -> entity.getTag(GameTag.RACE) == Race.MECH, false);
		SpellDesc summon = SummonSpell.create(new V07TR0N());
		SpellDesc metaSpell = MetaSpell.create(EntityReference.NONE, destroyAllMechs, summon);
		//SpellTrigger trigger = new SpellTrigger(new StartOfTurnHaveMechsTrigger(), metaSpell);
		SpellTrigger trigger = new SpellTrigger(new TurnStartTrigger(null), metaSpell);
		mimironsHead.setSpellTrigger(trigger);
		return mimironsHead;
	}

//	private class StartOfTurnHaveMechsTrigger extends TurnStartTrigger {
//
//		@Override
//		public boolean fire(GameEvent event, Entity host) {
//			if (!super.fire(event, host)) {
//				return false;
//			}
//			Player player = event.getGameContext().getPlayer(host.getOwner());
//			int mechCount = SpellUtils.hasHowManyOfRace(player, Race.MECH);
//			return mechCount >= 3;
//		}
//
//	}
}
