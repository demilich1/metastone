package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.RemoveTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.aura.Aura;
import net.pferdimanzug.hearthstone.analyzer.game.spells.aura.AuraSpellBuff;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.filter.RaceFilter;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
		SpellDesc demonBuff = AuraSpellBuff.create(+2, +2);
		demonBuff.setTargetFilter(new RaceFilter(Race.DEMON));
		SpellDesc immunity = ApplyTagSpell.create(GameTag.IMMUNE);
		immunity.setTarget(EntityReference.FRIENDLY_HERO);
		SpellDesc applyAuraSpell = MetaSpell.create(demonBuff, immunity);
		
		// aura remove
		SpellDesc removeDemonBuff = AuraSpellBuff.create(-2, -2);
		demonBuff.setTargetFilter(new RaceFilter(Race.DEMON));
		SpellDesc removeImmunity = RemoveTagSpell.create(GameTag.IMMUNE);
		removeImmunity.setTarget(EntityReference.FRIENDLY_HERO);
		SpellDesc removeAuraSpell = MetaSpell.create(removeDemonBuff, removeImmunity);
		
		Aura aura = new Aura(applyAuraSpell, removeAuraSpell, EntityReference.FRIENDLY_MINIONS);
		malGanis.setSpellTrigger(aura);
		return malGanis;
	}
}
