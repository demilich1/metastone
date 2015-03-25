package net.demilich.metastone.game.cards.concrete.mage;

import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.ReceiveCardSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellCastedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

public class ArchmageAntonidas extends MinionCard {

	public ArchmageAntonidas() {
		super("Archmage Antonidas", 5, 7, Rarity.LEGENDARY, HeroClass.MAGE, 7);
		setDescription("Whenever you cast a spell, put a 'Fireball' spell into your hand.");
	}

	@Override
	public int getTypeId() {
		return 54;
	}

	@Override
	public Minion summon() {
		Minion archmageAntonidas = createMinion();
		SpellDesc receiveFireballSpell = ReceiveCardSpell.create(CardCatalogue.getCardById("fireball"));
		SpellTrigger trigger = new SpellTrigger(new SpellCastedTrigger(TargetPlayer.SELF), receiveFireballSpell);
		archmageAntonidas.setSpellTrigger(trigger);
		return archmageAntonidas;
	}
}
