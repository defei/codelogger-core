package org.codelogger.core.service.cache.identifier;

import static com.google.common.collect.Sets.newHashSet;

import net.ftng.commons.lang.utils.ArrayUtils;
import net.ftng.commons.lang.utils.JudgeUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

import java.io.Serializable;
import java.util.Set;

public class GenericRepositoryCacheIdentifier <I extends Serializable> {

  private I id;

  private Set<I> ids;

  private PageRequest pageable;

  public GenericRepositoryCacheIdentifier(I id) {

    this.id = id;
  }

  public GenericRepositoryCacheIdentifier(I... ids) {

    this.ids = ArrayUtils.toSet(ids);
  }

  public GenericRepositoryCacheIdentifier(Iterable<I> ids) {

    this.ids = newHashSet(ids);
  }

  public GenericRepositoryCacheIdentifier(Integer page, Integer size, Direction direction,
      String property) {

    pageable = new PageRequest(Math.max(0, page - 1), size, direction, property);
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    if (id != null) {
      result = prime * result + id.hashCode();
    } else if (pageable != null) {
      result = prime * result + pageable.hashCode();
    } else {
      result = prime * result + ((ids == null) ? 0 : ids.hashCode());
    }
    return result;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    @SuppressWarnings("rawtypes") GenericRepositoryCacheIdentifier other =
        (GenericRepositoryCacheIdentifier) obj;
    if (id != null) {
      return JudgeUtils.equals(id, other.getId());
    } else if (pageable != null) {
      return JudgeUtils.equals(pageable, other.getPageable());
    } else {
      return JudgeUtils.equals(ids, other.getIds());
    }
  }

  @Override
  public String toString() {

    return String
        .format("GenericRepositoryCacheIdentifier [id=%s, ids=%s, pageable=%s]", id, ids, pageable);
  }

  public I getId() {

    return id;
  }

  public Set<I> getIds() {

    return ids;
  }

  public PageRequest getPageable() {

    return pageable;
  }

}
