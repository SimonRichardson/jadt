package jadt;

public abstract class Semigroup<T> {
	
	public Semigroup() {
	}
	
	public abstract T extract();
	
	public abstract Semigroup<T> concat(Semigroup<T> x);
}
