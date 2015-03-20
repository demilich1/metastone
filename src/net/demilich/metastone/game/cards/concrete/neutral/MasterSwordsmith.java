package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnEndTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class MasterSwordsmith extends MinionCard {

	public MasterSwordsmith() {
		super("Master Swordsmith", 1, 3, Rarity.RARE, HeroClass.ANY, 2);
		setDescription("At the end of your turn, give another random friendly minion +1 Attack.");
	}

	@Override
	public int getTypeId() {
		return 164;
	}

	@Override
	public Minion summon() {
		Minion masterSwordsmith = createMinion();
		SpellDesc randomBuffSpell = BuffSpell.create(EntityReference.OTHER_FRIENDLY_MINIONS, +1, 0, true);
		masterSwordsmith.setSpellTrigger(new SpellTrigger(new TurnEndTrigger(), randomBuffSpell));
		return masterSwordsmith;
	}
}
