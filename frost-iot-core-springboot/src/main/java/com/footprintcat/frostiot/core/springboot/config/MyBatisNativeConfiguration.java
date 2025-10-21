package com.footprintcat.frostiot.core.springboot.config;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.baomidou.mybatisplus.core.MybatisParameterHandler;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.handlers.CompositeEnumTypeHandler;
import com.baomidou.mybatisplus.core.handlers.MybatisEnumTypeHandler;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.baomidou.mybatisplus.extension.handlers.GsonTypeHandler;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.cache.decorators.FifoCache;
import org.apache.ibatis.cache.decorators.LruCache;
import org.apache.ibatis.cache.decorators.SoftCache;
import org.apache.ibatis.cache.decorators.WeakCache;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.javassist.util.proxy.ProxyFactory;
import org.apache.ibatis.javassist.util.proxy.RuntimeSupport;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.commons.JakartaCommonsLoggingImpl;
import org.apache.ibatis.logging.jdk14.Jdk14LoggingImpl;
import org.apache.ibatis.logging.log4j2.Log4j2Impl;
import org.apache.ibatis.logging.nologging.NoLoggingImpl;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.reflection.TypeParameterResolver;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.aot.BeanFactoryInitializationAotContribution;
import org.springframework.beans.factory.aot.BeanFactoryInitializationAotProcessor;
import org.springframework.beans.factory.aot.BeanRegistrationExcludeFilter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RegisteredBean;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.core.ResolvableType;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This configuration will move to mybatis-spring-native.
 */
@Configuration(proxyBeanMethods = false)
@ImportRuntimeHints(MyBatisNativeConfiguration.MyBaitsRuntimeHintsRegistrar.class)
public class MyBatisNativeConfiguration {

  @Bean
  MyBatisBeanFactoryInitializationAotProcessor myBatisBeanFactoryInitializationAotProcessor() {
    return new MyBatisBeanFactoryInitializationAotProcessor();
  }

  @Bean
  static MyBatisMapperFactoryBeanPostProcessor myBatisMapperFactoryBeanPostProcessor() {
    return new MyBatisMapperFactoryBeanPostProcessor();
  }

  static class MyBaitsRuntimeHintsRegistrar implements RuntimeHintsRegistrar {

    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
      Stream.of(RawLanguageDriver.class,
          // TODO 增加了MybatisXMLLanguageDriver.class
          XMLLanguageDriver.class, MybatisXMLLanguageDriver.class,
          RuntimeSupport.class,
          ProxyFactory.class,
          Slf4jImpl.class,
          Log.class,
          JakartaCommonsLoggingImpl.class,
          Log4j2Impl.class,
          Jdk14LoggingImpl.class,
          StdOutImpl.class,
          NoLoggingImpl.class,
          SqlSessionFactory.class,
          PerpetualCache.class,
          FifoCache.class,
          LruCache.class,
          SoftCache.class,
          WeakCache.class,
          //TODO 增加了MybatisSqlSessionFactoryBean.class
          SqlSessionFactoryBean.class, MybatisSqlSessionFactoryBean.class,
          ArrayList.class,
          HashMap.class,
          TreeSet.class,
          HashSet.class
      ).forEach(x -> hints.reflection().registerType(x, MemberCategory.values()));
      Stream.of(
          "org/apache/ibatis/builder/xml/*.dtd",
          "org/apache/ibatis/builder/xml/*.xsd"
      ).forEach(hints.resources()::registerPattern);

      hints.serialization().registerType(SerializedLambda.class);
      hints.serialization().registerType(SFunction.class);
      hints.serialization().registerType(java.lang.invoke.SerializedLambda.class);
      hints.reflection().registerType(SFunction.class);
      hints.reflection().registerType(SerializedLambda.class);
      hints.reflection().registerType(java.lang.invoke.SerializedLambda.class);

      hints.proxies().registerJdkProxy(StatementHandler.class);
      hints.proxies().registerJdkProxy(Executor.class);
      hints.proxies().registerJdkProxy(ResultSetHandler.class);
      hints.proxies().registerJdkProxy(ParameterHandler.class);

//        hints.reflection().registerType(MybatisPlusInterceptor.class);
      hints.reflection().registerType(AbstractWrapper.class,MemberCategory.values());
      hints.reflection().registerType(LambdaQueryWrapper.class,MemberCategory.values());
      hints.reflection().registerType(LambdaUpdateWrapper.class,MemberCategory.values());
      hints.reflection().registerType(UpdateWrapper.class,MemberCategory.values());
      hints.reflection().registerType(QueryWrapper.class,MemberCategory.values());

      hints.reflection().registerType(BoundSql.class,MemberCategory.DECLARED_FIELDS);
      hints.reflection().registerType(RoutingStatementHandler.class,MemberCategory.DECLARED_FIELDS);
      hints.reflection().registerType(BaseStatementHandler.class,MemberCategory.DECLARED_FIELDS);
      hints.reflection().registerType(MybatisParameterHandler.class,MemberCategory.DECLARED_FIELDS);


      hints.reflection().registerType(IEnum.class,MemberCategory.INVOKE_PUBLIC_METHODS);
      // register typeHandler
      hints.reflection().registerType(CompositeEnumTypeHandler.class, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);
      hints.reflection().registerType(FastjsonTypeHandler.class, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);
      hints.reflection().registerType(GsonTypeHandler.class, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);
      hints.reflection().registerType(JacksonTypeHandler.class, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);
      hints.reflection().registerType(MybatisEnumTypeHandler.class, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);
    }
  }

  static class MyBatisBeanFactoryInitializationAotProcessor
      implements BeanFactoryInitializationAotProcessor, BeanRegistrationExcludeFilter {

    private final Set<Class<?>> excludeClasses = new HashSet<>();

    MyBatisBeanFactoryInitializationAotProcessor() {
      excludeClasses.add(MapperScannerConfigurer.class);
    }

    @Override public boolean isExcludedFromAotProcessing(RegisteredBean registeredBean) {
      return excludeClasses.contains(registeredBean.getBeanClass());
    }

    @Override
    public BeanFactoryInitializationAotContribution processAheadOfTime(ConfigurableListableBeanFactory beanFactory) {
      String[] beanNames = beanFactory.getBeanNamesForType(MapperFactoryBean.class);
      if (beanNames.length == 0) {
        return null;
      }
      return (context, code) -> {
        RuntimeHints hints = context.getRuntimeHints();
        for (String beanName : beanNames) {
          BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName.substring(1));
          PropertyValue mapperInterface = beanDefinition.getPropertyValues().getPropertyValue("mapperInterface");
          if (mapperInterface != null && mapperInterface.getValue() != null) {
            Class<?> mapperInterfaceType = (Class<?>) mapperInterface.getValue();
            if (mapperInterfaceType != null) {
              registerReflectionTypeIfNecessary(mapperInterfaceType, hints);
              hints.proxies().registerJdkProxy(mapperInterfaceType);
              hints.resources()
                  .registerPattern(mapperInterfaceType.getName().replace('.', '/').concat(".xml"));
              registerMapperRelationships(mapperInterfaceType, hints);
            }
          }
        }
      };
    }

    private void registerMapperRelationships(Class<?> mapperInterfaceType, RuntimeHints hints) {
      Method[] methods = ReflectionUtils.getAllDeclaredMethods(mapperInterfaceType);
      for (Method method : methods) {
        if (method.getDeclaringClass() != Object.class) {
          ReflectionUtils.makeAccessible(method);
          registerSqlProviderTypes(method, hints, SelectProvider.class, SelectProvider::value, SelectProvider::type);
          registerSqlProviderTypes(method, hints, InsertProvider.class, InsertProvider::value, InsertProvider::type);
          registerSqlProviderTypes(method, hints, UpdateProvider.class, UpdateProvider::value, UpdateProvider::type);
          registerSqlProviderTypes(method, hints, DeleteProvider.class, DeleteProvider::value, DeleteProvider::type);
          Class<?> returnType = MyBatisMapperTypeUtils.resolveReturnClass(mapperInterfaceType, method);
          registerReflectionTypeIfNecessary(returnType, hints);
          MyBatisMapperTypeUtils.resolveParameterClasses(mapperInterfaceType, method)
              .forEach(x -> registerReflectionTypeIfNecessary(x, hints));
        }
      }
    }

    @SafeVarargs
    private <T extends Annotation> void registerSqlProviderTypes(
        Method method, RuntimeHints hints, Class<T> annotationType, Function<T, Class<?>>... providerTypeResolvers) {
      for (T annotation : method.getAnnotationsByType(annotationType)) {
        for (Function<T, Class<?>> providerTypeResolver : providerTypeResolvers) {
          registerReflectionTypeIfNecessary(providerTypeResolver.apply(annotation), hints);
        }
      }
    }

    private void registerReflectionTypeIfNecessary(Class<?> type, RuntimeHints hints) {
      if (!type.isPrimitive() && !type.getName().startsWith("java")) {
        hints.reflection().registerType(type, MemberCategory.values());
      }
    }

  }

  static class MyBatisMapperTypeUtils {
    private MyBatisMapperTypeUtils() {
      // NOP
    }

    static Class<?> resolveReturnClass(Class<?> mapperInterface, Method method) {
      Type resolvedReturnType = TypeParameterResolver.resolveReturnType(method, mapperInterface);
      return typeToClass(resolvedReturnType, method.getReturnType());
    }

    static Set<Class<?>> resolveParameterClasses(Class<?> mapperInterface, Method method) {
      return Stream.of(TypeParameterResolver.resolveParamTypes(method, mapperInterface))
          .map(x -> typeToClass(x, x instanceof Class ? (Class<?>) x : Object.class)).collect(Collectors.toSet());
    }

    private static Class<?> typeToClass(Type src, Class<?> fallback) {
      Class<?> result = null;
      if (src instanceof Class<?>) {
        if (((Class<?>) src).isArray()) {
          result = ((Class<?>) src).getComponentType();
        } else {
          result = (Class<?>) src;
        }
      } else if (src instanceof ParameterizedType) {
        ParameterizedType parameterizedType = (ParameterizedType) src;
        int index = (parameterizedType.getRawType() instanceof Class
            && Map.class.isAssignableFrom((Class<?>) parameterizedType.getRawType())
            && parameterizedType.getActualTypeArguments().length > 1) ? 1 : 0;
        Type actualType = parameterizedType.getActualTypeArguments()[index];
        result = typeToClass(actualType, fallback);
      }
      if (result == null) {
        result = fallback;
      }
      return result;
    }

  }

  static class MyBatisMapperFactoryBeanPostProcessor implements MergedBeanDefinitionPostProcessor, BeanFactoryAware {

    private static final org.apache.commons.logging.Log LOG = LogFactory.getLog(
        MyBatisMapperFactoryBeanPostProcessor.class);

    private static final String MAPPER_FACTORY_BEAN = "org.mybatis.spring.mapper.MapperFactoryBean";

    private ConfigurableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
      this.beanFactory = (ConfigurableBeanFactory) beanFactory;
    }

    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
      if (ClassUtils.isPresent(MAPPER_FACTORY_BEAN, this.beanFactory.getBeanClassLoader())) {
        resolveMapperFactoryBeanTypeIfNecessary(beanDefinition);
      }
    }

    private void resolveMapperFactoryBeanTypeIfNecessary(RootBeanDefinition beanDefinition) {
      if (!beanDefinition.hasBeanClass() || !MapperFactoryBean.class.isAssignableFrom(beanDefinition.getBeanClass())) {
        return;
      }
      if (beanDefinition.getResolvableType().hasUnresolvableGenerics()) {
        Class<?> mapperInterface = getMapperInterface(beanDefinition);
        if (mapperInterface != null) {
          // Exposes a generic type information to context for prevent early initializing
          ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
          constructorArgumentValues.addGenericArgumentValue(mapperInterface);
          beanDefinition.setConstructorArgumentValues(constructorArgumentValues);
          beanDefinition.setTargetType(ResolvableType.forClassWithGenerics(beanDefinition.getBeanClass(), mapperInterface));
        }
      }
    }

    private Class<?> getMapperInterface(RootBeanDefinition beanDefinition) {
      try {
        return (Class<?>) beanDefinition.getPropertyValues().get("mapperInterface");
      }
      catch (Exception e) {
        LOG.debug("Fail getting mapper interface type.", e);
        return null;
      }
    }

  }
}
