package com.hiddenswitch.proto3;

import org.unitils.reflectionassert.ReflectionComparator;
import org.unitils.reflectionassert.ReflectionComparatorFactory;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import org.unitils.reflectionassert.difference.Difference;
import org.unitils.reflectionassert.report.impl.DefaultDifferenceReport;

public class Assert {
	public static void assertReflectionEquals(Object lhs, Object rhs) {
		ReflectionComparator reflectionComparator = ReflectionComparatorFactory.createRefectionComparator(ReflectionComparatorMode.IGNORE_DEFAULTS);
		Difference difference = reflectionComparator.getDifference(rhs, lhs);
		if (difference != null) {
			org.testng.Assert.fail(new DefaultDifferenceReport().createReport(difference));
		}
	}
}
