package net.demilich.metastone.game.cards.concrete.paladin;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SecretCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.SetHpSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.MinionCardPlayedTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class Repentance extends SecretCard {

	public Repentance() {
		super("Repentance", Rarity.COMMON, HeroClass.PALADIN, 1);
		setDescription("Secret: When your opponent plays a minion, reduce its Health to 1.");

		SpellDesc setHpSpell = SetHpSpell.create(1);
		setHpSpell.setTarget(EntityReference.EVENT_TARGET);
		setTriggerAndEffect(new MinionCardPlayedTrigger(), setHpSpell);
	}

	@Override
	public int getTypeId() {
		return 255;
	}
}
