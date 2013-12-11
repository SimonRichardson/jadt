package jadt;

import org.junit.Assert;
import org.junit.Test;

public class OptionTest {

	@Test
	public void test() {
		Assert.assertEquals("Option.of should equal Option.of with same value", Option.of(1), Option.of(1));
	}
}
