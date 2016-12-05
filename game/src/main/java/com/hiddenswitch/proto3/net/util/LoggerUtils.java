package com.hiddenswitch.proto3.net.util;

import net.demilich.metastone.game.GameContext;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by bberman on 12/3/16.
 */
public class LoggerUtils {
	public static void log(Object caller, GameContext context, Throwable e) {
		Logger logger = LoggerFactory.getLogger(caller.getClass());
		String fullStackTrace = ExceptionUtils.getStackTrace(e);
		logger.error("Could not request action. GameContext:");
		logger.error("{}", context.toLongString());
		logger.error("Debug Log:");
		context.getLogic().panicDump();
		logger.error("Exception:");
		logger.error(e.getMessage());
		logger.error(fullStackTrace);
	}
}
