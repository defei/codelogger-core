package org.codelogger.core.bean.tuple;

import java.util.ArrayList;

public class Tuples {

  public static <A, B> TwoTuple<A, B> newTuple(final A first, final B second) {

    return new TwoTuple<A, B>(first, second);
  }

  public static <A, B, C> ThreeTuple<A, B, C> newTuple(final A first, final B second,
      final C third) {

    return new ThreeTuple<A, B, C>(first, second, third);
  }

  public static <A, B, C, D> FourTuple<A, B, C, D> newTuple(final A first, final B second,
      final C third, final D fourth) {

    return new FourTuple<A, B, C, D>(first, second, third, fourth);
  }

  public static <A, B, C, D, E> FiveTuple<A, B, C, D, E> newTuple(final A first, final B second,
      final C third, final D fourth, final E fifth) {

    return new FiveTuple<A, B, C, D, E>(first, second, third, fourth, fifth);
  }

  public static <A, B> ArrayList<TwoTuple<A, B>> newTwoTupleList() {

    return new TwoTupleList<A, B>();
  }

  public static <A, B, C> ArrayList<ThreeTuple<A, B, C>> newThreeTupleList() {

    return new ThreeTupleList<A, B, C>();
  }

  public static <A, B, C, D> ArrayList<FourTuple<A, B, C, D>> newFourTupleList() {

    return new FourTupleList<A, B, C, D>();
  }

  public static <A, B, C, D, E> ArrayList<FiveTuple<A, B, C, D, E>> newFiveTupleList() {

    return new FiveTupleList<A, B, C, D, E>();
  }

}
