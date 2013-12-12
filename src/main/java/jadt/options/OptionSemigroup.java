package jadt.options;

import jadt.Function0;
import jadt.Function1;
import jadt.Semigroup;

public abstract class OptionSemigroup<T extends Semigroup<?>> extends
		Semigroup<T> {

	private OptionSemigroup() {
	}

	public static final <T extends Semigroup<?>> OptionSemigroup<T> of(final T x) {
		return new Some<T>(Option.of(x));
	}

	public static final <T extends Semigroup<?>> OptionSemigroup<T> empty() {
		return new None<T>();
	}

	public static final <T extends Semigroup<?>> OptionSemigroup<T> from(
			final Option<T> x) {
		return x.fold(new Function1<T, OptionSemigroup<T>>() {
			@Override
			public OptionSemigroup<T> apply(T a) {
				return OptionSemigroup.of(a);
			}
		}, new Function0<OptionSemigroup<T>>() {
			@Override
			public OptionSemigroup<T> apply() {
				return OptionSemigroup.empty();
			}
		});
	}

	protected abstract <B> B fold(Function1<T, B> f, Function0<B> g);

	private static final class Some<T extends Semigroup<?>> extends
			OptionSemigroup<T> {

		private final Option<T> x;

		public Some(final Option<T> x) {
			this.x = x;
		}

		@Override
		public Semigroup<T> concat(final Semigroup<T> x) {
			if (x instanceof OptionSemigroup) {
				return this.x.fold(new Function1<T, Semigroup<T>>() {
					@Override
					public Semigroup<T> apply(final T a) {
						final OptionSemigroup<T> y = (OptionSemigroup<T>) x;
						return y.fold(new Function1<T, Semigroup<T>>() {
							@Override
							public Semigroup<T> apply(final T b) {
								//T xx = ((Semigroup<?>) a).concat((Semigroup<?>) b);
								return OptionSemigroup.of((T) a);
							}
						}, new Function0<Semigroup<T>>() {
							@Override
							public Semigroup<T> apply() {
								return OptionSemigroup.empty();
							}
						});
					}
				}, new Function0<Semigroup<T>>() {
					@Override
					public Semigroup<T> apply() {
						return OptionSemigroup.empty();
					}
				});
			} else {
				return OptionSemigroup.empty();
			}
		}

		@Override
		protected <B> B fold(final Function1<T, B> f, final Function0<B> g) {
			return this.x.fold(f, g);
		}

		@Override
		@SuppressWarnings("unchecked")
		public <B> B to() {
			return (B) this.x;
		}
	}

	private static final class None<T extends Semigroup<?>> extends
			OptionSemigroup<T> {

		private final Option<T> x;

		public None() {
			this.x = Option.empty();
		}

		@Override
		public Semigroup<T> concat(final Semigroup<T> x) {
			return new None<T>();
		}

		@Override
		protected <B> B fold(final Function1<T, B> f, final Function0<B> g) {
			return this.x.fold(f, g);
		}

		@Override
		@SuppressWarnings("unchecked")
		public <B> B to() {
			return (B) this.x;
		}
	}
}
