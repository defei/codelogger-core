package org.codelogger.core.bean.tuple;

public class ThreeTuple <A, B, C> extends TwoTuple<A, B> {

  public final C third;

  public ThreeTuple(final A first, final B second, final C third) {

    super(first, second);
    this.third = third;
  }

  public C getThird() {

    return third;
  }

}
