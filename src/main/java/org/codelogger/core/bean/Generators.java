package org.codelogger.core.bean;

import java.util.Collection;

public class Generators {

  public static <E> void fill(final Collection<E> collection, final Generator<E> generator,
      final Integer number) {

    for (int i = 0; i < number; i++) {
      collection.add(generator.next());
    }
  }
}
