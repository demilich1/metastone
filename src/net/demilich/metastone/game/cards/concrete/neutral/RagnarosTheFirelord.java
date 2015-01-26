package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.RemoveTagSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnEndTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class RagnarosTheFirelord extends MinionCard {

	public RagnarosTheFirelord() {
		super("Ragnaros the Firelord", 8, 8, Rarity.LEGENDARY, HeroClass.ANY, 8);
		setDescription("Can't Attack.  At the end of your turn, deal 8 damage to a random enemy.");
	}

	@Override
	public int getTypeId() {
		return 186;
	}

	@Override
	public Minion summon() {
		Minion ragnaros = createMinion(GameTag.CANNOT_ATTACK);
		SpellDesc damageSpell = DamageSpell.create(8);
		damageSpell.setTarget(EntityReference.ENEMY_CHARACTERS);
		damageSpell.pickRandomTarget(true);
		SpellDesc removeStealth = RemoveTagSpell.create(GameTag.STEALTHED);
		removeStealth.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), MetaSpell.create(damageSpell, removeStealth));
		ragnaros.setSpellTrigger(trigger);
		return ragnaros;
	}
}
