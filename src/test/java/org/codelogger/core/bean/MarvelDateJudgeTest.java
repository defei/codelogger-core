package org.codelogger.core.bean;

import static org.codelogger.core.lang.DatePolicyType.DATE_OF_MONTH;
import static org.codelogger.core.lang.DatePolicyType.DATE_OF_WEEK;
import static org.codelogger.core.lang.DatePolicyType.HOUR_OF_DAY;
import static org.codelogger.core.lang.DatePolicyType.MINUTE_OF_HOUR;
import static org.codelogger.utils.DateUtils.getDateFromString;
import static org.codelogger.utils.DateUtils.getDateOfDaysBack;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.codelogger.core.test.TestBase;
import org.codelogger.utils.DateUtils;
import org.junit.Test;

public class MarvelDateJudgeTest extends TestBase {

  @Test(expected = IllegalArgumentException.class)
  public void isValid_fromIsNull_exceptIllegalArgumentException() {

    MarvelDateJudge marvelDateJudge = new MarvelDateJudge();
    marvelDateJudge.isValid(new Date());
  }

  @Test(expected = IllegalArgumentException.class)
  public void isValid_datePolicyTypeIsNull_exceptIllegalArgumentException() {

    MarvelDateJudge marvelDateJudge = new MarvelDateJudge();
    marvelDateJudge.setFrom(new Date());
    marvelDateJudge.isValid(new Date());
  }

  @Test(expected = IllegalArgumentException.class)
  public void isValid_fromAfterToIsNull_exceptIllegalArgumentException() {

    MarvelDateJudge marvelDateJudge = new MarvelDateJudge();
    marvelDateJudge.setTo(new Date());
    marvelDateJudge.setFrom(new Date());
    marvelDateJudge.isValid(new Date());
  }

  @Test(expected = IllegalArgumentException.class)
  public void isValid_datePolicyTypeIsNotDATE_RANGEAndDatesIsEmpty_exceptIllegalArgumentException() {

    MarvelDateJudge marvelDateJudge = new MarvelDateJudge();
    marvelDateJudge.setFrom(new Date());
    marvelDateJudge.setTo(new Date());
    marvelDateJudge.setDatePolicyType(MINUTE_OF_HOUR);
    marvelDateJudge.isValid(new Date());
  }

  @Test
  public void isValid_dateEqualsFromAndTo_returnTrue() {

    Date from = getDateFromString("2012-12-21", "yyyy-MM-dd");
    MarvelDateJudge marvelDateJudge = new MarvelDateJudge(from, from);
    assertTrue(marvelDateJudge.isValid(from));
  }

  @Test
  public void isValid_dateEqualsFromAndLessThanTo_returnTrue() {

    Date from = getDateFromString("2012-12-21", "yyyy-MM-dd");
    Date to = getDateFromString("2012-12-22", "yyyy-MM-dd");
    MarvelDateJudge marvelDateJudge = new MarvelDateJudge(from, to);
    assertTrue(marvelDateJudge.isValid(from));
  }

  @Test
  public void isValid_dateGreaterThanFromAndLessThanTo_returnTrue() {

    Date from = getDateFromString("2012-12-21", "yyyy-MM-dd");
    Date date = getDateFromString("2012-12-22", "yyyy-MM-dd");
    Date to = getDateFromString("2012-12-23", "yyyy-MM-dd");
    MarvelDateJudge marvelDateJudge = new MarvelDateJudge(from, to);
    assertTrue(marvelDateJudge.isValid(date));
  }

  @Test
  public void isValid_dateGreaterThanFromAndEqualsTo_returnTrue() {

    Date from = getDateFromString("2012-12-21", "yyyy-MM-dd");
    Date date = getDateFromString("2012-12-22", "yyyy-MM-dd");
    MarvelDateJudge marvelDateJudge = new MarvelDateJudge(from, date);
    assertTrue(marvelDateJudge.isValid(date));
  }

  @Test
  public void isValid_datelessThanFrom_returnFalse() {

    Date date = getDateFromString("2012-12-21", "yyyy-MM-dd");
    Date from = getDateFromString("2012-12-22", "yyyy-MM-dd");
    MarvelDateJudge marvelDateJudge = new MarvelDateJudge(from, from);
    assertFalse(marvelDateJudge.isValid(date));
  }

  @Test
  public void isValid_dateGreaterThanTo_returnTrue() {

    Date from = getDateFromString("2012-12-21", "yyyy-MM-dd");
    Date date = getDateFromString("2012-12-22", "yyyy-MM-dd");
    MarvelDateJudge marvelDateJudge = new MarvelDateJudge(from, from);
    assertFalse(marvelDateJudge.isValid(date));
  }

  @Test
  public void isValid_dayOfMonthInDates_returnTrue() {

    Date date = getDateFromString("2014-12-17", "yyyy-MM-dd");
    Date from = getDateOfDaysBack(1, date);
    Date to = getDateOfDaysBack(-1, date);
    MarvelDateJudge marvelDateJudge = new MarvelDateJudge(from, to, DATE_OF_MONTH, 17);
    assertTrue(marvelDateJudge.isValid(date));
  }

  @Test
  public void isValid_dayOfMonthNotInDates_returnFalse() {

    Date date = getDateFromString("2014-12-17", "yyyy-MM-dd");
    Date from = getDateOfDaysBack(1, date);
    Date to = getDateOfDaysBack(-1, date);
    MarvelDateJudge marvelDateJudge = new MarvelDateJudge(from, to, DATE_OF_MONTH, 16);
    assertFalse(marvelDateJudge.isValid(date));
  }

  @Test
  public void isValid_wednesdayInDates_returnTrue() {

    Date date = getDateFromString("2014-12-17", "yyyy-MM-dd");
    Date from = getDateOfDaysBack(1, date);
    Date to = getDateOfDaysBack(-1, date);
    MarvelDateJudge marvelDateJudge = new MarvelDateJudge(from, to, DATE_OF_WEEK,
      DateUtils.WEDNESDAY);
    assertTrue(marvelDateJudge.isValid(date));
  }

  @Test
  public void isValid_wednesdayNotInDates_returnFalse() {

    Date date = getDateFromString("2014-12-17", "yyyy-MM-dd");
    Date from = getDateOfDaysBack(1, date);
    Date to = getDateOfDaysBack(-1, date);
    MarvelDateJudge marvelDateJudge = new MarvelDateJudge(from, to, DATE_OF_WEEK, DateUtils.FRIDAY);
    assertFalse(marvelDateJudge.isValid(date));
  }

  @Test
  public void isValid_hourOfDayInDates_returnTrue() {

    Date date = getDateFromString("2014-12-17 12:12:21");
    Date from = getDateOfDaysBack(1, date);
    Date to = getDateOfDaysBack(-1, date);
    MarvelDateJudge marvelDateJudge = new MarvelDateJudge(from, to, HOUR_OF_DAY, 12);
    assertTrue(marvelDateJudge.isValid(date));
  }

  @Test
  public void isValid_hourOfDayNotInDates_returnFalse() {

    Date date = getDateFromString("2014-12-17 12:12:21");
    Date from = getDateOfDaysBack(1, date);
    Date to = getDateOfDaysBack(-1, date);
    MarvelDateJudge marvelDateJudge = new MarvelDateJudge(from, to, HOUR_OF_DAY, 11);
    assertFalse(marvelDateJudge.isValid(date));
  }

  @Test
  public void isValid_minuteOfHourInDates_returnTrue() {

    Date date = getDateFromString("2014-12-17 12:12:21");
    Date from = getDateOfDaysBack(1, date);
    Date to = getDateOfDaysBack(-1, date);
    MarvelDateJudge marvelDateJudge = new MarvelDateJudge(from, to, MINUTE_OF_HOUR, 12);
    assertTrue(marvelDateJudge.isValid(date));
  }

  @Test
  public void isValid_minuteOfHourNotInDates_returnFalse() {

    Date date = getDateFromString("2014-12-17 12:12:21");
    Date from = getDateOfDaysBack(1, date);
    Date to = getDateOfDaysBack(-1, date);
    MarvelDateJudge marvelDateJudge = new MarvelDateJudge(from, to, MINUTE_OF_HOUR, 11);
    assertFalse(marvelDateJudge.isValid(date));
  }

}
