package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.tokens.V07TR0N;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SpellUtils;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnStartTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
		SpellDesc destroyAllMechs = DestroySpell.create();
		destroyAllMechs.setTargetFilter(entity -> entity.getTag(GameTag.RACE) == Race.MECH);
		destroyAllMechs.setTarget(EntityReference.FRIENDLY_MINIONS);
		SpellDesc summon = SummonSpell.create(new V07TR0N());
		summon.setTarget(EntityReference.NONE);
		SpellDesc metaSpell = MetaSpell.create(destroyAllMechs, summon);
		metaSpell.setTarget(EntityReference.NONE);
		SpellTrigger trigger = new SpellTrigger(new StartOfTurnHaveMechsTrigger(), metaSpell);
		mimironsHead.setSpellTrigger(trigger);
		return mimironsHead;
	}

	private class StartOfTurnHaveMechsTrigger extends TurnStartTrigger {

		@Override
		public boolean fire(GameEvent event, Entity host) {
			if (!super.fire(event, host)) {
				return false;
			}
			Player player = event.getGameContext().getPlayer(host.getOwner());
			int mechCount = SpellUtils.hasHowManyOfRace(player, Race.MECH);
			return mechCount >= 3;
		}

	}
}
