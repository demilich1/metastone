package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.DamageCausedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class EmperorCobra extends MinionCard {

	public EmperorCobra() {
		super("Emperor Cobra", 2, 3, Rarity.RARE, HeroClass.ANY, 3);
		setDescription("Destroy any minion damaged by this minion.");
		setRace(Race.BEAST);
	}

	@Override
	public int getTypeId() {
		return 124;
	}

	@Override
	public Minion summon() {
		Minion emperorCobra = createMinion();
		SpellDesc killSpell = DestroySpell.create(EntityReference.EVENT_TARGET);
		//emperorCobra.setSpellTrigger(new SpellTrigger(new DamageCausedTrigger(false), killSpell));
		emperorCobra.setSpellTrigger(new SpellTrigger(new DamageCausedTrigger(null), killSpell));
		return emperorCobra;
	}
}
