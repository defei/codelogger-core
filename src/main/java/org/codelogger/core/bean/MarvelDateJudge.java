package org.codelogger.core.bean;

import static com.google.common.collect.Lists.newArrayList;
import static org.codelogger.utils.CollectionUtils.isEmpty;
import static org.codelogger.utils.DateUtils.getDayOfMonth;
import static org.codelogger.utils.DateUtils.getDayOfWeek;
import static org.codelogger.utils.DateUtils.getHourOfDay;
import static org.codelogger.utils.DateUtils.getMinuteOfHour;
import static org.codelogger.utils.JudgeUtils.greaterThanOrEquals;
import static org.codelogger.utils.JudgeUtils.lessThanOrEquals;

import java.util.Date;
import java.util.List;

import org.codelogger.core.lang.DatePolicyType;
import org.codelogger.utils.JudgeUtils;

public class MarvelDateJudge {

  private Date from;

  private Date to;

  private DatePolicyType datePolicyType;

  private List<Integer> dates;

  public MarvelDateJudge() {

  }

  public MarvelDateJudge(final Date from, final Date to) {

    this.from = from;
    this.to = to;
    datePolicyType = DatePolicyType.DATE_RANGE;
    validateFields();
  }

  public MarvelDateJudge(final Date from, final Date to, final DatePolicyType datePolicyType,
    final Integer... dates) {

    this.from = from;
    this.to = to;
    this.datePolicyType = datePolicyType;
    this.dates = newArrayList(dates);
    validateFields();
  }

  public Boolean isValid(final Date date) {

    validateFields();
    Boolean inDateRange = greaterThanOrEquals(date, from) && lessThanOrEquals(date, to);
    switch (datePolicyType) {
      case DATE_RANGE:
        return inDateRange;
      case DATE_OF_MONTH:
        return inDateRange && dates.contains(getDayOfMonth(date));
      case DATE_OF_WEEK:
        return inDateRange && dates.contains(getDayOfWeek(date));
      case HOUR_OF_DAY:
        return inDateRange && dates.contains(getHourOfDay(date));
      case MINUTE_OF_HOUR:
        return inDateRange && dates.contains(getMinuteOfHour(date));
      default:
        return false;
    }
  }

  private void validateFields() {

    if (from == null) {
      throw new IllegalArgumentException("From date can not be null.");
    }
    if (JudgeUtils.greaterThan(from, to)) {
      throw new IllegalArgumentException("From date can not greater than to date.");
    }
    if (datePolicyType == null) {
      throw new IllegalArgumentException("datePolicyType can not be null.");
    }
    if (datePolicyType != DatePolicyType.DATE_RANGE && isEmpty(dates)) {
      throw new IllegalArgumentException("dates can not be empty.");
    }
  }

  public Date getFrom() {

    return from;
  }

  public void setFrom(final Date from) {

    this.from = from;
  }

  public Date getTo() {

    return to;
  }

  public void setTo(final Date to) {

    this.to = to;
  }

  public DatePolicyType getDatePolicyType() {

    return datePolicyType;
  }

  public void setDatePolicyType(final DatePolicyType datePolicyType) {

    this.datePolicyType = datePolicyType;
  }

  public List<Integer> getDates() {

    return dates;
  }

  public void setDates(final List<Integer> dates) {

    this.dates = dates;
  }

}
