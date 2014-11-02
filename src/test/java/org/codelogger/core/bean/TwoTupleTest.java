package org.codelogger.core.bean;

import static net.ftng.commons.lang.utils.PrintUtils.println;

import org.codelogger.core.bean.tuple.TwoTuple;
import org.junit.Test;

public class TwoTupleTest {

  @Test
  public void normal() {

    TwoTuple<String, Long> twoTuple = getStringLongPair();
    println("Key:%s, Value:%s", twoTuple.getFirst(), twoTuple.getSecond());
    TwoTuple<Boolean, Long> twoTuple2 = getBooleanLongPair();
    println("Key:%s, Value:%s", twoTuple2.getFirst(), twoTuple2.getSecond());
  }

  private TwoTuple<String, Long> getStringLongPair() {

    return new TwoTuple<String, Long>("a", 1L);
  }

  private TwoTuple<Boolean, Long> getBooleanLongPair() {

    return new TwoTuple<Boolean, Long>(true, 1L);
  }

}
