package net.demilich.metastone.game.cards.concrete.tokens.shaman;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.UniqueEntity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.HealingSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnEndTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class HealingTotem extends MinionCard {

	public HealingTotem() {
		super("Healing Totem", 0, 2, Rarity.FREE, HeroClass.SHAMAN, 1);
		setDescription("At the end of your turn, restore 1 Health to all friendly minions.");
		setRace(Race.TOTEM);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 457;
	}

	@Override
	public Minion summon() {
		Minion healingTotem = createMinion();
		healingTotem.setTag(GameTag.UNIQUE_ENTITY, UniqueEntity.HEALING_TOTEM);
		SpellDesc healSpell = HealingSpell.create(1);
		healSpell.setTarget(EntityReference.FRIENDLY_MINIONS);
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), healSpell);
		healingTotem.setSpellTrigger(trigger);
		return healingTotem;
	}
}
