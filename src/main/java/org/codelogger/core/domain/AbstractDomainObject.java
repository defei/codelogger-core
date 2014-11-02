package org.codelogger.core.domain;

import org.codelogger.core.bean.Identifiable;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractDomainObject implements Identifiable<Long>, Serializable {

  private static final long serialVersionUID = 5832653249116053276L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected Long id;

  @Column
  protected Long createdTime;

  @Column
  protected Long latestUpdateTime;

  public AbstractDomainObject() {

    createdTime = System.currentTimeMillis();
    latestUpdateTime = createdTime;
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    AbstractDomainObject other = (AbstractDomainObject) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }

  @Override
  public Long getId() {

    return id;
  }

  @Override
  public void setId(final Long id) {

    this.id = id;
  }

  public Long getCreatedTime() {

    return createdTime;
  }

  public void setCreatedTime(Long createdTime) {

    this.createdTime = createdTime;
  }

  public Long getLatestUpdateTime() {

    return latestUpdateTime;
  }

  public void setLatestUpdateTime(Long latestUpdateTime) {

    this.latestUpdateTime = latestUpdateTime;
  }

}
