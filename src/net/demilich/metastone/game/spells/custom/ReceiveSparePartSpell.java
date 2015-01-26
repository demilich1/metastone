package net.demilich.metastone.game.spells.custom;

import net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.spareparts.ArmorPlating;
import net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.spareparts.EmergencyCoolant;
import net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.spareparts.FinickyCloakfield;
import net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.spareparts.ReversingSwitch;
import net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.spareparts.RustyHorn;
import net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.spareparts.TimeRewinder;
import net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.spareparts.WhirlingBlades;
import net.demilich.metastone.game.spells.ReceiveRandomCardSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class ReceiveSparePartSpell extends ReceiveRandomCardSpell {

	public static SpellDesc create(TargetPlayer targetPlayer) {
		SpellDesc desc = ReceiveRandomCardSpell.create(targetPlayer, new ArmorPlating(), new EmergencyCoolant(), new FinickyCloakfield(),
				new ReversingSwitch(), new RustyHorn(), new TimeRewinder(), new WhirlingBlades());
		return desc;
	}

}
