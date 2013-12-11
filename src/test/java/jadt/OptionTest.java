package jadt;

import org.junit.Assert;
import org.junit.Test;

public class OptionTest {

	@Test
	public void creation_with_Option_of() {
		Assert.assertEquals("Option.of should equal Option.of with same value",
				Option.of(1), Option.of(1));
	}

	@Test
	public void map_and_increment() {
		Assert.assertEquals("Option.of with map should be correct value",
				Option.of(1).map(new Function1<Integer, Integer>() {
					public Integer apply(Integer x) {
						return x + 1;
					}
				}), Option.of(2));
	}
}
