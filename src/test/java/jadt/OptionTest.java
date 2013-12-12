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

	@Test
	public void semigroup() {
		Option<Semigroup<Integer>> a = Option
				.of((Semigroup<Integer>) new Num(1));
		Option<Semigroup<Integer>> b = Option
				.of((Semigroup<Integer>) new Num(1));
		Assert.assertEquals(
				"Option.of with semigroup concat should be correct value",
				Option.semigroup(a).concat(Option.semigroup(b)).extract(),
				Option.of(new Num(2)));
	}
}

class Num extends Semigroup<Integer> {

	private Integer x;

	public Num(Integer x) {
		this.x = x;
	}

	@Override
	public Integer extract() {
		return this.x;
	}

	@Override
	public Semigroup<Integer> concat(Semigroup<Integer> x) {
		return new Num(this.x + ((Num) x).x);
	}

	@Override
	public int hashCode() {
		return this.x.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		else if (obj == null)
			return false;
		else if (getClass() != obj.getClass())
			return false;
		else {
			Num other = (Num) obj;
			if (x == null && other.x != null)
				return false;
			else if (!x.equals(other.x))
				return false;
			else
				return true;
		}
	}
}
