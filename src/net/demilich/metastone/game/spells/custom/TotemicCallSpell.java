package net.demilich.metastone.game.spells.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.UniqueEntity;
import net.demilich.metastone.game.cards.concrete.tokens.shaman.HealingTotem;
import net.demilich.metastone.game.cards.concrete.tokens.shaman.SearingTotem;
import net.demilich.metastone.game.cards.concrete.tokens.shaman.StoneclawTotem;
import net.demilich.metastone.game.cards.concrete.tokens.shaman.WrathOfAirTotem;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class TotemicCallSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(TotemicCallSpell.class);
		return desc;
	}
	
	private boolean alreadyOnBoard(List<Minion> minions, UniqueEntity uniqueMinion) {
		for (Entity minion : minions) {
			if (minion.getTag(GameTag.UNIQUE_ENTITY) == uniqueMinion) {
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
			if (!alreadyOnBoard(player.getMinions(), (UniqueEntity) totem.getTag(GameTag.UNIQUE_ENTITY))) {
				availableTotems.add(totem);
			}
		}

		Minion randomTotem = availableTotems.get(ThreadLocalRandom.current().nextInt(availableTotems.size()));
		context.getLogic().summon(player.getId(), randomTotem);
	}
	
}
