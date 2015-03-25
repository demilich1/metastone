package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DamageRandomSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.RemoveAttributeSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.MinionSummonedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class KnifeJuggler extends MinionCard {

	public KnifeJuggler() {
		super("Knife Juggler", 3, 2, Rarity.RARE, HeroClass.ANY, 2);
		setDescription("After you summon a minion, deal 1 damage to a random enemy.");
	}

	@Override
	public int getTypeId() {
		return 151;
	}

	@Override
	public Minion summon() {
		Minion knifeJuggler = createMinion();
		SpellDesc damageRandomSpell = DamageRandomSpell.create(EntityReference.ENEMY_CHARACTERS, 1, 1);
		SpellDesc removeStealth = RemoveAttributeSpell.create(EntityReference.SELF, GameTag.STEALTHED);
		SpellTrigger trigger = new SpellTrigger(new MinionSummonedTrigger(TargetPlayer.SELF), MetaSpell.create(damageRandomSpell,
				removeStealth));
		knifeJuggler.setSpellTrigger(trigger);
		return knifeJuggler;
	}
}
