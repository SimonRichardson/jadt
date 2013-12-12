package jadt;

public abstract class Semigroup<T> {
		
	public abstract Semigroup<T> concat(final Semigroup<T> x);
	
	public abstract <B> B to();
}
