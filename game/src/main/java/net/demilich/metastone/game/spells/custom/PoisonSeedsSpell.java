package net.demilich.metastone.game.spells.custom;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class PoisonSeedsSpell extends Spell {

	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(PoisonSeedsSpell.class);
		arguments.put(SpellArg.TARGET, EntityReference.NONE);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Player opponent = context.getOpponent(player);

		EntityReference sourceReference = source != null ? source.getReference() : null;
		poisonSeeds(context, opponent, sourceReference);
		poisonSeeds(context, player, sourceReference);
	}

	private void poisonSeeds(GameContext context, Player player, EntityReference source) {
		int minionCount = player.getMinions().size();
		if (minionCount == 0) {
			return;
		}
		
		SpellDesc destroySpell = DestroySpell.create(EntityReference.FRIENDLY_MINIONS);
		context.getLogic().castSpell(player.getId(), destroySpell, source, null, true);
		context.getLogic().checkForDeadEntities();

		String[] treants = new String[minionCount];
		for (int i = 0; i < minionCount; i++) {
			treants[i] = "token_treant";
		}
		SpellDesc summonTreants = SummonSpell.create(treants);
		context.getLogic().castSpell(player.getId(), summonTreants, source, EntityReference.NONE, true);

	}

}
