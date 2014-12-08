package org.codelogger.core.service;

import static java.lang.String.format;
import static org.codelogger.utils.JudgeUtils.isNotNull;

import org.codelogger.core.domain.AbstractDomainObject;
import org.codelogger.core.exception.ResourceNotFoundExcception;
import org.codelogger.core.repository.GenericRepository;
import org.codelogger.core.service.cache.GenericRepositoryCache;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;

public abstract class GenericService <T extends AbstractDomainObject> implements InitializingBean {

  protected GenericRepositoryCache<T> genericRepositoryCache;

  protected abstract GenericRepository<T, Long> getRepository();

  @Override
  public void afterPropertiesSet() throws Exception {

    genericRepositoryCache = new GenericRepositoryCache<T>(getRepository(), getCachemaximumSize(),
        getCacheExpireMinutes());
  }

  protected Integer getCachemaximumSize() {

    return 100;
  }

  protected Integer getCacheExpireMinutes() {

    return 30;
  }

  public T save(T t) {

    if (isNotNull(t.getId())) {
      t.setLatestUpdateTime(System.currentTimeMillis());
    }
    T savedItem = getRepository().save(t);
    genericRepositoryCache.invalidateAll();
    return savedItem;
  }

  public T findById(Long id) throws ResourceNotFoundExcception {

    T data = genericRepositoryCache.findOne(id);
    if (data == null) {
      throw new ResourceNotFoundExcception(format("No element found with id:'%s'", id));
    }
    return data;
  }

  public List<T> findByIds(Iterable<Long> ids) {

    return genericRepositoryCache.findAll(ids);
  }

  public List<T> findAll() {

    return genericRepositoryCache.findAll();
  }

  public Page<T> findAll(Integer page, Integer size, Direction direction, String property) {

    return genericRepositoryCache.findAll(page, size, direction, property);
  }

  public Long count() {

    return getRepository().count();
  }

  public Boolean exist(Long id) {

    return getRepository().exists(id);
  }

  public void delete(Long id) {

    getRepository().delete(id);
    genericRepositoryCache.invalidateAll();
  }
}
