package org.codelogger.core.bean;

import org.codelogger.utils.lang.Direction;

public class Pageable {

  public Integer page = 0;

  public Integer pageSize = 10;

  public Pageable() {

  }

  public Pageable(final Integer page, final Integer pageSize) {

    this.page = page;
    this.pageSize = pageSize;
  }

  public Pageable(final Integer fixedPage, final Integer countPerPage, final Direction desc,
    final String... fields) {

  }

}
