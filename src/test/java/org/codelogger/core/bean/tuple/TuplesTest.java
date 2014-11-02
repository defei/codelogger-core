package org.codelogger.core.bean.tuple;

import static net.ftng.commons.lang.utils.PrintUtils.println;
import static org.codelogger.core.bean.tuple.Tuples.newTuple;
import static org.codelogger.core.bean.tuple.Tuples.newTwoTupleList;

import net.ftng.commons.lang.utils.MathUtils;
import net.ftng.commons.lang.utils.StringUtils;
import org.junit.Test;

import java.util.List;

public class TuplesTest {

  @Test
  public void newTuples() {

    TwoTuple<String, Integer> info = newTuple("Age", 18);
    println(info);
    ThreeTuple<String, String, String> message = newTuple("Hello", "Mr", "Li");
    println(message);
    FourTuple<Boolean, Long, String, Float> anyThing = newTuple(true, 21L, "Year", 1F / 3);
    println(anyThing);
  }

  @Test
  public void newTupleList() {

    List<TwoTuple<String, Integer>> testTupleList = newTwoTupleList();
    for (int i = 0; i < 10; i++) {

      TwoTuple<String, Integer> value =
          newTuple(StringUtils.getRandomString(2), MathUtils.randomInt(9));
      testTupleList.add(value);
    }
    println(testTupleList);
  }
}
