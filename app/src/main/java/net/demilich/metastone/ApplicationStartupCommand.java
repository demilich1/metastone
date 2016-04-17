package net.demilich.metastone;

import net.demilich.nittygrittymvc.SimpleCommand;
import net.demilich.nittygrittymvc.interfaces.INotification;
import net.demilich.metastone.game.cards.CardProxy;
import net.demilich.metastone.gui.autoupdate.AutoUpdateMediator;
import net.demilich.metastone.gui.deckbuilder.DeckFormatProxy;
import net.demilich.metastone.gui.deckbuilder.DeckProxy;
import net.demilich.metastone.gui.dialog.DialogMediator;
import net.demilich.metastone.gui.main.ApplicationMediator;
import net.demilich.metastone.gui.playmode.animation.AnimationProxy;
import net.demilich.metastone.gui.sandboxmode.SandboxProxy;
import net.demilich.metastone.gui.trainingmode.TrainingProxy;

public class ApplicationStartupCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		getFacade().registerProxy(new CardProxy());
		getFacade().registerProxy(new DeckProxy());
		getFacade().registerProxy(new DeckFormatProxy());
		getFacade().registerProxy(new TrainingProxy());
		getFacade().registerProxy(new SandboxProxy());
		getFacade().registerProxy(new AnimationProxy());

		getFacade().registerMediator(new ApplicationMediator());
		getFacade().registerMediator(new DialogMediator());
		getFacade().registerMediator(new AutoUpdateMediator());
	}

}
