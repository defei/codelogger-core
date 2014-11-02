package org.codelogger.core.bean.tuple;

import org.codelogger.core.utils.JsonUtils;

public class TwoTuple <A, B> {

  public final A first;

  public final B second;

  public TwoTuple(final A first, final B second) {

    this.first = first;
    this.second = second;
  }

  public static <A, B> TwoTuple<A, B> newTwoTuple(A first, B second) {

    return new TwoTuple<A, B>(first, second);
  }

  public A getFirst() {

    return first;
  }

  public B getSecond() {

    return second;
  }

  @Override
  public String toString() {

    return JsonUtils.toJson(this);
  }

}
