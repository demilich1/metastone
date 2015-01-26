package net.demilich.metastone.game.cards.concrete.naxxramas;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.ReturnMinionToHandSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class AnubarAmbusher extends MinionCard {

	public AnubarAmbusher() {
		super("Anub'ar Ambusher", 5, 5, Rarity.COMMON, HeroClass.ROGUE, 4);
		setDescription("Deathrattle: Return a friendly minion to your hand.");
	}

	@Override
	public int getTypeId() {
		return 384;
	}

	@Override
	public Minion summon() {
		Minion anubarAmbusher = createMinion();
		SpellDesc deathrattle = ReturnMinionToHandSpell.create();
		deathrattle.pickRandomTarget(true);
		deathrattle.setTarget(EntityReference.FRIENDLY_MINIONS);
		anubarAmbusher.addDeathrattle(deathrattle);
		return anubarAmbusher;
	}
}
