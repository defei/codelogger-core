package org.codelogger.core.service.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import org.codelogger.core.domain.AbstractDomainObject;
import org.codelogger.core.repository.GenericRepository;
import org.codelogger.core.service.cache.identifier.GenericRepositoryCacheIdentifier;
import org.codelogger.core.service.cache.loader.GenericRepositoryCacheLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unchecked")
public class GenericRepositoryCache <V extends AbstractDomainObject> {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final LoadingCache<GenericRepositoryCacheIdentifier<Long>, Object> loadingCache;

  public GenericRepositoryCache(GenericRepository<V, Long> genericRepository, int cachemaximumSize,
      int cacheExpireMinutes) {

    GenericRepositoryCacheLoader<V> genericCacheLoader = new GenericRepositoryCacheLoader<V>();
    genericCacheLoader.setGenericRepository(genericRepository);
    loadingCache = CacheBuilder.newBuilder().maximumSize(cachemaximumSize)
        .expireAfterWrite(cacheExpireMinutes, TimeUnit.MINUTES).build(genericCacheLoader);
  }

  public void invalidateAll() {

    loadingCache.invalidateAll();
  }

  public void evict(Long id) {

    GenericRepositoryCacheIdentifier<Long> key = new GenericRepositoryCacheIdentifier<Long>(id);
    loadingCache.invalidate(key);
  }

  public V findOne(Long id) {

    GenericRepositoryCacheIdentifier<Long> key = new GenericRepositoryCacheIdentifier<Long>(id);
    V item = (V) loadDataFromCache(key);
    return item;
  }

  public List<V> findAll() {

    GenericRepositoryCacheIdentifier<Long> key = new GenericRepositoryCacheIdentifier<Long>();
    return (List<V>) loadDataFromCache(key);
  }

  public List<V> findAll(Long... ids) {

    GenericRepositoryCacheIdentifier<Long> key = new GenericRepositoryCacheIdentifier<Long>(ids);
    return (List<V>) loadDataFromCache(key);
  }

  public List<V> findAll(Iterable<Long> ids) {

    GenericRepositoryCacheIdentifier<Long> key = new GenericRepositoryCacheIdentifier<Long>(ids);
    return (List<V>) loadDataFromCache(key);
  }

  public Page<V> findAll(Integer page, Integer size, Direction direction, String property) {

    GenericRepositoryCacheIdentifier<Long> key =
        new GenericRepositoryCacheIdentifier<Long>(page, size, direction, property);
    return (Page<V>) loadDataFromCache(key);
  }

  private Object loadDataFromCache(GenericRepositoryCacheIdentifier<Long> key) {

    Object value = null;
    try {
      value = loadingCache.get(key);
    } catch (Exception e) {
      logger.debug(e.getMessage());
    }
    return value;
  }
}
