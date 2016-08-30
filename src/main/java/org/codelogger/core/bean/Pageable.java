package org.codelogger.core.bean;

import org.codelogger.utils.lang.Direction;

public class Pageable {

  public Integer page = 0;

  public Integer pageSize = 10;

  public Direction direction;

  public String[] fields;

  public Pageable() {

  }

  public Pageable(final Integer page, final Integer pageSize) {

    this.page = page;
    this.pageSize = pageSize;
  }

  public Pageable(final Integer page, final Integer pageSize, final Direction direction,
    final String... fields) {

    this(page, pageSize);
    this.direction = direction;
    this.fields = fields;
  }

}
