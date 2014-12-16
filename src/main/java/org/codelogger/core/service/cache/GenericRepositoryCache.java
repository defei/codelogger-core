package org.codelogger.core.service.cache;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.codelogger.core.domain.AbstractDomainObject;
import org.codelogger.core.exception.MethodUnsupportedException;
import org.codelogger.core.repository.GenericRepository;
import org.codelogger.core.service.cache.loader.GenericRepositoryCacheLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;

public class GenericRepositoryCache<V extends AbstractDomainObject> {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final GenericRepository<V, Long> genericRepository;

  private final LoadingCache<Long, V> loadingCache;

  public GenericRepositoryCache(final GenericRepository<V, Long> genericRepository,
    final int cachemaximumSize, final int cacheExpireMinutes) {

    this.genericRepository = genericRepository;
    GenericRepositoryCacheLoader<V> genericCacheLoader = new GenericRepositoryCacheLoader<V>();
    genericCacheLoader.setGenericRepository(genericRepository);
    loadingCache = CacheBuilder.newBuilder().maximumSize(cachemaximumSize)
      .expireAfterWrite(cacheExpireMinutes, TimeUnit.MINUTES).build(genericCacheLoader);
  }

  public void invalidateAll() {

    loadingCache.invalidateAll();
  }

  public void evict(final Long id) {

    loadingCache.invalidate(id);
  }

  public V findOne(final Long id) {

    V value = null;
    try {
      value = loadingCache.get(id);
    } catch (Exception e) {
      logger.debug(e.getMessage());
    }
    return value;
  }

  public List<V> findAll() {

    ConcurrentMap<Long, V> cacheMap = loadingCache.asMap();
    if (cacheMap.isEmpty()) {
      List<V> items = genericRepository.findAll();
      for (V v : items) {
        cacheMap.put(v.getId(), v);
      }
    }
    return newArrayList(cacheMap.values());
  }

  public List<V> findAll(final Long... ids) {

    List<V> items = newArrayList();
    for (Long id : ids) {
      items.add(findOne(id));
    }
    return items;
  }

  public List<V> findAll(final Iterable<Long> ids) {

    List<V> items = newArrayList();
    for (Long id : ids) {
      items.add(findOne(id));
    }
    return items;
  }

  public Page<V> findAll(final Integer page, final Integer size, final Direction direction,
    final String property) throws MethodUnsupportedException {

    throw new MethodUnsupportedException();
  }
}
