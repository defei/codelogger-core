package org.codelogger.core.service.cache.loader;

import static net.ftng.commons.lang.utils.CollectionUtils.isNotEmpty;

import com.google.common.cache.CacheLoader;
import org.codelogger.core.domain.AbstractDomainObject;
import org.codelogger.core.repository.GenericRepository;
import org.codelogger.core.service.cache.identifier.GenericRepositoryCacheIdentifier;

public class GenericRepositoryCacheLoader <V extends AbstractDomainObject>
    extends CacheLoader<GenericRepositoryCacheIdentifier<Long>, Object> {

  private GenericRepository<V, Long> genericRepository;

  @Override
  public Object load(final GenericRepositoryCacheIdentifier<Long> key) throws Exception {

    if (key.getId() != null) {
      return genericRepository.findOne(key.getId());
    } else if (key.getPageable() != null) {
      return genericRepository.findAll(key.getPageable());
    } else if (isNotEmpty(key.getIds())) {
      return genericRepository.findAll(key.getIds());
    } else {
      return genericRepository.findAll();
    }
  }

  public void setGenericRepository(GenericRepository<V, Long> genericRepository) {

    this.genericRepository = genericRepository;
  }
}
