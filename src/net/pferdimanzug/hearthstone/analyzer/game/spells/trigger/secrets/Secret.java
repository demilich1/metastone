package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.secrets;

import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TriggerLayer;

public class Secret extends SpellTrigger {

	private final Card source;

	public Secret(SecretTrigger trigger, Spell spell, Card source) {
		super(trigger, spell, true);
		spell.setApplySpellpower(true);
		this.source = source;
		setLayer(TriggerLayer.SECRET);
	}

	public Card getSource() {
		return source;
	}

	@Override
	protected void onFire(int ownerId, Spell spell, GameEvent event) {
		super.onFire(ownerId, spell, event);
		Player owner = event.getGameContext().getPlayer(ownerId);
		event.getGameContext().getLogic().secretTriggered(owner, this);
	}

}
