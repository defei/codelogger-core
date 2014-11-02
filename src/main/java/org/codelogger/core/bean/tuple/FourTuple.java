package org.codelogger.core.bean.tuple;

public class FourTuple <A, B, C, D> extends ThreeTuple<A, B, C> {

  public final D fourth;

  public FourTuple(final A first, final B second, final C third, final D fourth) {

    super(first, second, third);
    this.fourth = fourth;
  }

  public D getFourth() {

    return fourth;
  }
}
