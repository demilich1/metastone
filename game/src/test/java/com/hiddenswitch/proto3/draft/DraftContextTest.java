package com.hiddenswitch.proto3.draft;

import co.paralleluniverse.fibers.SuspendExecution;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardParseException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.testng.Assert.*;

/**
 * Created by bberman on 12/14/16.
 */
public class DraftContextTest {
	@Test
	public void testDraftComplete() throws InterruptedException, SuspendExecution, CardParseException, IOException, URISyntaxException {
		CardCatalogue.loadCardsFromPackage();
		DraftContext context = new DraftContext();
		context.accept(then -> {
		});
		assertEquals(context.getPublicState().selectedCards.size(), DraftLogic.DRAFTS);
		assertEquals(context.getPublicState().status, DraftStatus.COMPLETE);
	}
}