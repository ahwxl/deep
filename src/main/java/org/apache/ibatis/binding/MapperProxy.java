/*
 *    Copyright 2009-2013 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.binding;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.hamcrest.core.IsInstanceOf;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.base.pagination.PageInfo;

public class MapperProxy<T> implements InvocationHandler, Serializable {

  private static final long serialVersionUID = -6424540398559729838L;
  private final SqlSession sqlSession;
  private final Class<T> mapperInterface;
  private final Map<Method, MapperMethod> methodCache;

  public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface, Map<Method, MapperMethod> methodCache) {
    this.sqlSession = sqlSession;
    this.mapperInterface = mapperInterface;
    this.methodCache = methodCache;
  }

  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    if (Object.class.equals(method.getDeclaringClass())) {
      return method.invoke(this, args);
    }
    Object tmpobj =args[0];
    final MapperMethod mapperMethod = cachedMapperMethod(method);
    Class returntype = method.getReturnType();
    if(tmpobj instanceof PageInfo && Page.class.equals(returntype)){
        PageInfo pageInfo =(PageInfo) args[0];
        Page<T> page = (Page<T>)mapperMethod.execute(sqlSession, args);
        page.setTotals(pageInfo.getTotalCount());
        page.setPageSize(pageInfo.getTotalCount());
        page.setPageNum(pageInfo.getPageNo());
        return page;
    }else{
      return  mapperMethod.execute(sqlSession, args);
    }
    
  }

  private MapperMethod cachedMapperMethod(Method method) {
    MapperMethod mapperMethod = methodCache.get(method);
    if (mapperMethod == null) {
      mapperMethod = new MapperMethod(mapperInterface, method, sqlSession.getConfiguration());
      methodCache.put(method, mapperMethod);
    }
    return mapperMethod;
  }

}