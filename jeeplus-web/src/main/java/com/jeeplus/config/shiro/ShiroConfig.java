/**
 *
 */
package com.jeeplus.config.shiro;

import com.jeeplus.config.shiro.ehcache.EhCacheCacheManager;
import com.jeeplus.config.shiro.redis.RedisCacheManager;
import com.jeeplus.core.security.shiro.session.CacheSessionDAO;
import com.jeeplus.core.security.shiro.session.SessionManager;
import com.jeeplus.modules.sys.security.FormAuthenticationFilter;
import com.jeeplus.modules.sys.security.KickoutSessionControlFilter;
import com.jeeplus.modules.sys.security.SystemAuthorizingRealm;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.*;

/**
 * shiro的控制类
 * Created by jeelus
 */
@Configuration
public class ShiroConfig {

    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }



    /**
     * 并发登录控制
     * @return
     */
    @Bean
    public KickoutSessionControlFilter kickoutSessionControlFilter(SessionManager sessionManager,
                                                                   CacheManager cacheManager,
                                                                   @Value("${adminPath}") String adminPath){
        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
        //用于根据会话ID，获取会话进行踢出操作的；
        kickoutSessionControlFilter.setSessionManager(sessionManager);
        //使用cacheManager获取相应的cache来缓存用户登录的会话；用于保存用户—会话之间的关系的；
        kickoutSessionControlFilter.setCacheManager(cacheManager);
        //是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；
        kickoutSessionControlFilter.setKickoutAfter(false);
        //同一个用户最大的会话数，默认1；比如2的意思是同一个用户允许最多同时两个人登录；
        kickoutSessionControlFilter.setMaxSession(1);
        //被踢出后重定向到的地址；
        kickoutSessionControlFilter.setKickoutUrl(adminPath+"/login");
        return kickoutSessionControlFilter;
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(
            @Value("${adminPath}") String adminPath,
            KickoutSessionControlFilter kickoutSessionControlFilter,
            FormAuthenticationFilter formAuthenticationFilter,
            DefaultWebSecurityManager securityManager) {
        Map<String, Filter> filters = new HashMap<>();
        filters.put("kickout", kickoutSessionControlFilter);
        filters.put("authc", formAuthenticationFilter);
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setFilters(filters);
        bean.setSecurityManager(securityManager);
        bean.setLoginUrl(adminPath + "/login");
        bean.setSuccessUrl(adminPath);
       // formAuthenticationFilter.setLoginUrl(adminPath + "/login");
       // formAuthenticationFilter.setSuccessUrl(adminPath + "?login");


        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put(adminPath+"/login","authc");
        filterChainDefinitionMap.put(adminPath+"/logout","anon");
        filterChainDefinitionMap.put(adminPath+"/kickout/1","anon");
        filterChainDefinitionMap.put(adminPath + "/**", "user,kickout");
        filterChainDefinitionMap.put("/ReportServer/**", "user");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }

    @Bean(name = "basicHttpAuthenticationFilter")
    public BasicHttpAuthenticationFilter casFilter(@Value("${adminPath}") String adminPath) {
        BasicHttpAuthenticationFilter basicHttpAuthenticationFilter = new BasicHttpAuthenticationFilter();
        basicHttpAuthenticationFilter.setLoginUrl(adminPath + "/login");
        return basicHttpAuthenticationFilter;
    }


    @ConditionalOnProperty(name = "spring.cache.type", havingValue = "ehcache")
    @Bean
    public CacheManager shiroCacheManager(org.springframework.cache.ehcache.EhCacheCacheManager ehCacheCacheManager){
        EhCacheCacheManager shiroCacheManager = new EhCacheCacheManager();
        shiroCacheManager.setCacheManager(ehCacheCacheManager);
        return shiroCacheManager;
    }

    @ConditionalOnProperty(name = "spring.cache.type", havingValue = "redis")
    @Bean
    public CacheManager shiroRedisCacheManager(){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        return redisCacheManager;
    }




    @Bean(name = "sessionManager")
    public SessionManager sessionManager(CacheSessionDAO dao) {
        SessionManager sessionManager = new SessionManager();
        sessionManager.setSessionDAO(dao);
        sessionManager.setGlobalSessionTimeout(86400000);
        sessionManager.setSessionValidationInterval(1800000);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdCookie(new SimpleCookie("wolfking.jeeplus.session.id"));
        sessionManager.setSessionIdCookieEnabled(true);
        return sessionManager;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(
            SystemAuthorizingRealm systemAuthorizingRealm,
            SessionManager sessionManager,
            CacheManager cacheManager) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();

        defaultWebSecurityManager.setSessionManager(sessionManager);
        defaultWebSecurityManager.setCacheManager(cacheManager);
        Collection<Realm> typeRealms = new ArrayList<>();
        typeRealms.add(systemAuthorizingRealm);
        defaultWebSecurityManager.setRealms(typeRealms);
        return defaultWebSecurityManager;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setEnabled(true);
        filterRegistration.setOrder(2);
        filterRegistration.addUrlPatterns("/*");
        return filterRegistration;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

}
