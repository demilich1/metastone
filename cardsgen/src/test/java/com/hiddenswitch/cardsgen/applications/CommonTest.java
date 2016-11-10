package com.hiddenswitch.cardsgen.applications;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CommonTest {
	@Test
	public void testGetDefaultDecks() throws Exception {
		assertTrue(Common.getDefaultDecks().size() > 0);
	}

}