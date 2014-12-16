package org.codelogger.core.service.cache.loader;

import org.codelogger.core.domain.AbstractDomainObject;
import org.codelogger.core.repository.GenericRepository;

import com.google.common.cache.CacheLoader;

public class GenericRepositoryCacheLoader<V extends AbstractDomainObject> extends
  CacheLoader<Long, V> {

  private GenericRepository<V, Long> genericRepository;

  @Override
  public V load(final Long id) throws Exception {

    return genericRepository.findOne(id);
  }

  public void setGenericRepository(final GenericRepository<V, Long> genericRepository) {

    this.genericRepository = genericRepository;
  }
}
