package net.demilich.metastone.game.spells.desc.source;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardCollection;

public class DefaultSource extends CardSource {

	public DefaultSource(SourceDesc desc) {
		super(desc);
	}

	@Override
	protected CardCollection match(GameContext context, Player player) {
		return CardCatalogue.query(context.getDeckFormat());
	}

}
