package net.demilich.metastone.game.cards.concrete.blackrockmountain;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.ConditionalEffectSpell;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

public class QuickShot extends SpellCard {

	public QuickShot() {
		super("Quick Shot", Rarity.COMMON, HeroClass.HUNTER, 2);
		setDescription("Deal 3 damage. If your hand is empty, draw a card.");

		SpellDesc damage = DamageSpell.create(3);
		SpellDesc drawCard = DrawCardSpell.create();
		SpellDesc quickShot = ConditionalEffectSpell.create(damage, drawCard, (context, player, entity) -> {
			return player.getHand().isEmpty();
		});
		setSpell(quickShot);

		setTargetRequirement(TargetSelection.ANY);
	}



	@Override
	public int getTypeId() {
		return 641;
	}
}
