package org.codelogger.core.utils;

import static java.lang.String.format;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.codelogger.core.bean.Request;
import org.codelogger.core.exception.ResourceNotFoundExcception;
import org.codelogger.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class RequestLogger {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private LoadingCache<Object, Request> loadingCache;

  public RequestLogger(final int cachemaximumSize, final Long duration, final TimeUnit unit) {

    CacheLoader<Object, Request> cacheLoader = new CacheLoader<Object, Request>() {

      @Override
      public Request load(final Object key) throws Exception {

        logger.debug("Build a new Request for key:{}", key);
        return new Request();
      }
    };
    loadingCache = CacheBuilder.newBuilder().maximumSize(cachemaximumSize)
      .expireAfterWrite(duration, unit).build(cacheLoader);
    logger.info("build a {} chace with cachemaximumSize:{},duration:{},TimeUnit:{}", this
      .getClass().getSimpleName(), cachemaximumSize, duration, unit);
  }

  public Boolean isLatestRequestTimeOutof(final Object key, final TimeUnit timeUnit,
    final Integer timesOfTimeUnit) {

    Boolean outOfTime = true;
    Request request = getRequestFromCache(key);
    Date latestRequestTime = CollectionUtils.getLastElement(request.getRequestTimes());
    Date currentTime = new Date();
    if (latestRequestTime != null) {
      outOfTime = currentTime.getTime() - latestRequestTime.getTime() > timeUnit
        .toMillis(timesOfTimeUnit);
    }
    if (outOfTime) {
      request.getRequestTimes().add(currentTime);
    }
    return outOfTime;
  }

  private Request getRequestFromCache(final Object key) {

    try {
      return loadingCache.get(key);
    } catch (ExecutionException e) {
      throw new ResourceNotFoundExcception(format("Request not found with:%s", key), e);
    }
  }
}
