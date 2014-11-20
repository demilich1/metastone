package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.spareparts.ArmorPlating;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.spareparts.EmergencyCoolant;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.spareparts.FinickyCloakfield;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.spareparts.ReversingSwitch;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.spareparts.RustyHorn;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.spareparts.TimeRewinder;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.spareparts.WhirlingBlades;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ReceiveRandomCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class ReceiveSparePartSpell extends ReceiveRandomCardSpell {

	public static SpellDesc create(TargetPlayer targetPlayer) {
		SpellDesc desc = ReceiveRandomCardSpell.create(targetPlayer, new ArmorPlating(), new EmergencyCoolant(), new FinickyCloakfield(),
				new ReversingSwitch(), new RustyHorn(), new TimeRewinder(), new WhirlingBlades());
		return desc;
	}

}
