package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warlock;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.AddAttributeSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.RemoveAttributeSpell;
import net.demilich.metastone.game.spells.aura.Aura;
import net.demilich.metastone.game.spells.aura.AuraBuffSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class MalGanis extends MinionCard {

	public MalGanis() {
		super("Mal'Ganis", 9, 7, Rarity.LEGENDARY, HeroClass.WARLOCK, 9);
		setDescription("Your other Demons have +2/+2. Your hero is Immune.");
		setRace(Race.DEMON);
	}

	@Override
	public int getTypeId() {
		return 601;
	}

	@Override
	public Minion summon() {
		Minion malGanis = createMinion();
		// aura apply
		//SpellDesc demonBuff = AuraBuffSpell.create(+2, +2, new RaceFilter(Race.DEMON));
		SpellDesc demonBuff = AuraBuffSpell.create(+2, +2, null);
		SpellDesc immunity = AddAttributeSpell.create(EntityReference.FRIENDLY_HERO, GameTag.IMMUNE);
		SpellDesc applyAuraSpell = MetaSpell.create(demonBuff, immunity);
		
		// aura remove
		//SpellDesc removeDemonBuff = AuraBuffSpell.create(-2, -2, new RaceFilter(Race.DEMON));
		SpellDesc removeDemonBuff = AuraBuffSpell.create(-2, -2, null);
		SpellDesc removeImmunity = RemoveAttributeSpell.create(EntityReference.FRIENDLY_HERO, GameTag.IMMUNE);
		SpellDesc removeAuraSpell = MetaSpell.create(removeDemonBuff, removeImmunity);
		
		Aura aura = new Aura(applyAuraSpell, removeAuraSpell, EntityReference.FRIENDLY_MINIONS);
		malGanis.setSpellTrigger(aura);
		return malGanis;
	}
}
