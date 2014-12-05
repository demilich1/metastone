package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.UniqueEntity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.SpellCastedEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.CopySpellCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellCastedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class TradePrinceGallywix extends MinionCard {

	public TradePrinceGallywix() {
		super("Trade Prince Gallywix", 5, 8, Rarity.LEGENDARY, HeroClass.ROGUE, 6);
		setDescription("Whenever your opponent casts a spell, gain a copy of it and give them a Coin.");
	}

	@Override
	public Minion summon() {
		Minion tradePriceGallywix = createMinion();
		SpellDesc copySpell = CopySpellCardSpell.create();
		copySpell.setTarget(EntityReference.EVENT_TARGET);
		SpellTrigger trigger = new SpellTrigger(new GallywixTrigger(), copySpell);
		tradePriceGallywix.setSpellTrigger(trigger);
		return tradePriceGallywix;
	}

	private class GallywixTrigger extends SpellCastedTrigger {

		public GallywixTrigger() {
			super(TargetPlayer.OPPONENT);
		}

		@Override
		public boolean fire(GameEvent event, Entity host) {
			if (!super.fire(event, host)) {
				return false;
			}
			SpellCastedEvent spellCastedEvent = (SpellCastedEvent) event;
			return spellCastedEvent.getSourceCard().getTag(GameTag.UNIQUE_ENTITY) != UniqueEntity.GALLYWIX_COIN;

		}

	}

}
