package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.rogue;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.UniqueEntity;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.SpellCastedEvent;
import net.demilich.metastone.game.spells.CopyPlayedCardSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellCastedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

public class TradePrinceGallywix extends MinionCard {

	public TradePrinceGallywix() {
		super("Trade Prince Gallywix", 5, 8, Rarity.LEGENDARY, HeroClass.ROGUE, 6);
		setDescription("Whenever your opponent casts a spell, gain a copy of it and give them a Coin.");
	}

	@Override
	public int getTypeId() {
		return 574;
	}

	@Override
	public Minion summon() {
		Minion tradePriceGallywix = createMinion();
		SpellDesc copySpell = CopyPlayedCardSpell.create();
		//SpellTrigger trigger = new SpellTrigger(new GallywixTrigger(), copySpell);
		SpellTrigger trigger = new SpellTrigger(new SpellCastedTrigger(null), copySpell);
		tradePriceGallywix.setSpellTrigger(trigger);
		return tradePriceGallywix;
	}

//	private class GallywixTrigger extends SpellCastedTrigger {
//
//		public GallywixTrigger() {
//			super(TargetPlayer.OPPONENT);
//		}
//
//		@Override
//		public boolean fire(GameEvent event, Entity host) {
//			if (!super.fire(event, host)) {
//				return false;
//			}
//			SpellCastedEvent spellCastedEvent = (SpellCastedEvent) event;
//			return spellCastedEvent.getSourceCard().getTag(GameTag.UNIQUE_ENTITY) != UniqueEntity.GALLYWIX_COIN;
//
//		}
//
//	}
}
