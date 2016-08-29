package org.codelogger.core.bean;

import java.util.List;

public class Page<T> {

  public Integer page;

  public Integer pageSize;

  public Long count;

  public Integer totalPages;

  List<T> content;

  public Page(final Integer page, final Integer pageSize, final Long count,
    final Integer totalPage, final List<T> content) {

    super();
    this.page = page;
    this.pageSize = pageSize;
    this.count = count;
    this.totalPages = totalPage;
    this.content = content;
  }

  public Integer getPage() {

    return page;
  }

  public Integer getPageSize() {

    return pageSize;
  }

  public Long getCount() {

    return count;
  }

  public List<T> getContent() {

    return content;
  }

  public Boolean hasPreviousPage() {

    return page > 0;
  }

  public Integer getTotalPages() {

    return totalPages;
  }

}
