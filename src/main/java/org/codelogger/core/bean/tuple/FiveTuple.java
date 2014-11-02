package org.codelogger.core.bean.tuple;

public class FiveTuple <A, B, C, D, E> extends FourTuple<A, B, C, D> {

  public final E fifth;

  public FiveTuple(final A first, final B second, final C third, final D fourth, final E fifth) {

    super(first, second, third, fourth);
    this.fifth = fifth;
  }

  public E getFifth() {

    return fifth;
  }
}
