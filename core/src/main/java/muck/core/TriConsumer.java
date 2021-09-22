package muck.core;

import java.util.Objects;

/* https://stackoverflow.com/a/19649473
 * Allows for consumers accepting 3 arguments (java.util only exposes upto biConsumers
 */

@FunctionalInterface
public interface TriConsumer<T, U, V> {
  public void accept(T t, U u, V v);

  public default TriConsumer<T, U, V> andThen(TriConsumer<? super T, ? super U, ? super V> after) {
    Objects.requireNonNull(after);
    return (a, b, c) -> {
      accept(a, b, c);
      after.accept(a, b, c);
    };
  }
}
