package net.pferdimanzug.hearthstone.analyzer;

import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.DeckProxy;
import net.pferdimanzug.hearthstone.analyzer.gui.dialog.DialogMediator;
import net.pferdimanzug.hearthstone.analyzer.gui.main.ApplicationMediator;
import net.pferdimanzug.hearthstone.analyzer.statistics.StatisticsMediator;
import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class ApplicationStartupCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		getFacade().registerProxy(new DeckProxy());
		
		getFacade().registerMediator(new StatisticsMediator());
		getFacade().registerMediator(new ApplicationMediator());
		getFacade().registerMediator(new DialogMediator());
	}

}
