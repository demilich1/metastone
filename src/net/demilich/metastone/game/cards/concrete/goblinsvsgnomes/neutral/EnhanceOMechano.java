package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.ApplyTagSpell;
import net.demilich.metastone.game.spells.CastRandomSpellSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class EnhanceOMechano extends MinionCard {

	public EnhanceOMechano() {
		super("Enhance-o Mechano", 3, 2, Rarity.EPIC, HeroClass.ANY, 4);
		setDescription("Battlecry: Give your other minions Windfury, Taunt, or Divine Shield. (at random)");
		setRace(Race.MECH);
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 510;
	}

	@Override
	public Minion summon() {
		Minion enhanceOMechano = createMinion();
		SpellDesc randomBuff = CastRandomSpellSpell.create(ApplyTagSpell.create(GameTag.WINDFURY), ApplyTagSpell.create(GameTag.TAUNT),
				ApplyTagSpell.create(GameTag.DIVINE_SHIELD));
		randomBuff.setTarget(EntityReference.FRIENDLY_MINIONS);
		Battlecry battlecry = Battlecry.createBattlecry(randomBuff);
		enhanceOMechano.setBattlecry(battlecry);
		return enhanceOMechano;
	}
}
