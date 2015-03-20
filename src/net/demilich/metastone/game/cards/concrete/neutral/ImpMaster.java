package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.concrete.tokens.neutral.Imp;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.RelativeToSource;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnEndTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class ImpMaster extends MinionCard {

	public ImpMaster() {
		super("Imp Master", 1, 5, Rarity.RARE, HeroClass.ANY, 3);
		setDescription("At the end of your turn, deal 1 damage to this minion and summon a 1/1 Imp.");
	}

	@Override
	public int getTypeId() {
		return 144;
	}
	
	@Override
	public Minion summon() {
		Minion impMaster = createMinion();
		SpellDesc damageSelfSpell = DamageSpell.create(EntityReference.SELF, 1);
		SpellDesc summonSpell = SummonSpell.create(RelativeToSource.RIGHT, new Imp());
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), MetaSpell.create(damageSelfSpell, summonSpell));
		impMaster.setSpellTrigger(trigger);
		return impMaster;
	}
}
