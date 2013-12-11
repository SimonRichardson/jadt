package jadt;

import jadt.Function0;
import jadt.Function1;
import jadt.Semigroup;

public abstract class Option<T> {
	
	private Option() {
	}
	
	public static <T> Option<T> of(final T x) {
		return new Some<T>(x);
	}
	
	public static <T> Option<T> empty() {
		return new None<T>();
	}
	
	public abstract <B> B fold(Function1<T, B> f, Function0<B> g);
	
	public Option<T> orElse(final Option<T> x) {
		return fold(
			new Function1<T, Option<T>>() {
				public Option<T> apply(T x) {
					return Option.of(x);
				}
			},
			new Function0<Option<T>>() {
				public Option<T> apply() {
					return x;
				}
			}
		);
	}
	
	public T getOrElse(final T x) {
		return fold(
			new Function1<T, T>() {
				public T apply(T x) {
					return x;
				}
			},
			new Function0<T>() {
				public T apply() {
					return x;
				}
			}
		);
	}
	
	public <B> Option<B> chain(final Function1<T, Option<B>> f) {
		return fold(
			new Function1<T, Option<B>>() {
				public Option<B> apply(T x) {
					return f.apply(x);
				}
			},
			new Function0<Option<B>>() {
				public Option<B> apply() {
					return Option.empty();
				}
			}
		);
	}
	
	public <B> Option<B> map(final Function1<T, B> f) {
		return chain(
			new Function1<T, Option<B>>() {
				public Option<B> apply(T x) {
					return Option.of(f.apply(x));
				}
			}
		);
	}
	
	public Option<T> concat(final Option<Semigroup<T>> a) {
		return a.chain(
			new Function1<Semigroup<T>, Option<T>>() {
				public Option<T> apply(final Semigroup<T> x) {
					return map(
						new Function1<T, T>() {
							public T apply(final T y) {
								return x.concat(y);
							}
						}
					);
				}
			}
		);
	}
	
	public <B> Option<B> ap(final Option<Function1<T, B>> a) {
		return a.chain(
				new Function1<Function1<T, B>, Option<B>>() {
					public Option<B> apply(final Function1<T, B> b) {
						return map(b);
					}
				}
			);
	}
	
	private static final class Some<T> extends Option<T> {
		
		private final T x;
		
		private Some(final T x) {
			this.x = x;
		}

		@Override
		public <B> B fold(final Function1<T, B> f, final Function0<B> g) {
			return f.apply(this.x);
		}
		
		@Override
	    public int hashCode() {
	       final int prime = 31;
	       int result = 1;
	       return prime * result + ((x == null) ? 0 : x.hashCode());
	    }

		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			else if (obj == null) return false;
			else if (getClass() != obj.getClass()) return false;
			else {
				Some<?> other = (Some<?>) obj;
				if (x == null && other.x != null) return false;
				else if (!x.equals(other.x)) return false;
				else return true;
			}
		}
	}
	
	private static final class None<T> extends Option<T> {
		
		private None() {
		}

		@Override
		public <B> B fold(final Function1<T, B> f, final Function0<B> g) {
			return g.apply();
		}
		
		@Override
		public int hashCode() {
			return 31;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			else if (obj == null) return false;
			else if (getClass() != obj.getClass()) return false;
			else return true;
		}
	}
}
