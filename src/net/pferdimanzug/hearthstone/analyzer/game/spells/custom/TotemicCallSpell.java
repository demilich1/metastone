package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.UniqueMinion;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.shaman.HealingTotem;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.shaman.SearingTotem;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.shaman.StoneclawTotem;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.shaman.WrathOfAirTotem;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class TotemicCallSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(TotemicCallSpell.class);
		return desc;
	}
	
	private boolean alreadyOnBoard(List<Minion> minions, UniqueMinion uniqueMinion) {
		for (Entity minion : minions) {
			if (minion.getTag(GameTag.UNIQUE_MINION) == uniqueMinion) {
				return true;
			}
		}
		return false;
	}
	

	private List<Minion> getTotems() {
		List<Minion> minions = new ArrayList<Minion>(4);
		minions.add(new HealingTotem().summon());
		minions.add(new StoneclawTotem().summon());
		minions.add(new SearingTotem().summon());
		minions.add(new WrathOfAirTotem().summon());
		return minions;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		List<Minion> availableTotems = new ArrayList<Minion>();
		for (Minion totem : getTotems()) {
			if (!alreadyOnBoard(player.getMinions(), (UniqueMinion) totem.getTag(GameTag.UNIQUE_MINION))) {
				availableTotems.add(totem);
			}
		}

		Minion randomTotem = availableTotems.get(ThreadLocalRandom.current().nextInt(availableTotems.size()));
		context.getLogic().summon(player.getId(), randomTotem, null, null, true);
	}
	
}
