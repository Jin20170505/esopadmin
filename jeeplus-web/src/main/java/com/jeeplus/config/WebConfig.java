/**
 *
 */
package com.jeeplus.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.google.common.collect.Lists;
import com.jeeplus.config.shiro.ShiroConfig;
import com.jeeplus.config.web.LogInterceptor;
import com.jeeplus.core.mapper.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * Created by jeeplus on 2017/9/28.
 */
@ConditionalOnBean(ShiroConfig.class)
@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${adminPath}")
    protected String adminPath;
    @Autowired
    private DispatcherServlet dispatcherServlet;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/act/**").addResourceLocations("classpath:/act/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS","HEAD")
                .maxAge(3600);
    }

    //
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**").excludePathPatterns("/static/**");
    }





    @Bean
    public LogInterceptor logInterceptor(){
        return new LogInterceptor();
    }
    @Bean
    public ServletRegistrationBean apiServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(dispatcherServlet);
        //????????????????????????????????????ServletRegistrationBean
        bean.addUrlMappings("/service/*");
        bean.setName("ModelRestServlet");
        return bean;
    }
    @Bean
    public ServletRegistrationBean restServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(dispatcherServlet);
        //????????????????????????????????????ServletRegistrationBean
        bean.addUrlMappings("/rest/*");
        bean.setName("RestServlet");
        return bean;
    }
//    @Bean
//    public ViewResolver getJspViewResolver(){
//        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
//        internalResourceViewResolver.setPrefix("/views/");
//        internalResourceViewResolver.setSuffix(".jsp");
//        internalResourceViewResolver.setOrder(1);
//        return internalResourceViewResolver;
//    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        List<MediaType> supportedMediaTypes = Lists.newArrayList();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);

        FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
        //formHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
        converters.add(formHttpMessageConverter);


        StringHttpMessageConverter stringHttpMessageConverter =new StringHttpMessageConverter();
        stringHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
        converters.add(stringHttpMessageConverter);

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setPrettyPrint(false);
        converter.setSupportedMediaTypes(supportedMediaTypes);
        converter.setObjectMapper(new JsonMapper());
        converters.add(converter);

    }


    /**
     * druidServlet??????
     */
    @Bean
    public ServletRegistrationBean druidServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new StatViewServlet());
        registration.addUrlMappings("/druid/*");
        return registration;
    }

//    @Bean
//    public ServletRegistrationBean validateCodeServletRegistration() {
//        ServletRegistrationBean registration = new ServletRegistrationBean(new ValidateCodeServlet());
//        registration.addUrlMappings("/servlet/validateCodeServlet");
//        return registration;
//    }

//    @Bean
//    public ServletRegistrationBean userfilesDownloadServletRegistration() {
//        ServletRegistrationBean registration = new ServletRegistrationBean(new UserfilesDownloadServlet());
//        registration.addUrlMappings("/userfiles/*");
//        return registration;
//    }

//    @Bean
//    public ServletRegistrationBean springServletRegistration() {
//        ServletRegistrationBean registration = new ServletRegistrationBean(new DispatcherServlet());
//        registration.addUrlMappings("/");
////        registration.addInitParameter("contextConfigLocation", "classpath:/spring/spring-mvc*.xml");
//        return registration;
//    }

//    @Bean
//    public ServletRegistrationBean modelerServletRegistration() {
//        ServletRegistrationBean registration = new ServletRegistrationBean(new DispatcherServlet());
//        registration.addUrlMappings("/service/*");
//        registration.addInitParameter("contextConfigLocation", "/act/rest/spring-mvc-modeler.xml");
//        return registration;
//    }
////
//    @Bean
//    public ServletRegistrationBean restServletRegistration() {
//        ServletRegistrationBean registration = new ServletRegistrationBean(new DispatcherServlet());
//        registration.addUrlMappings("/rest/*");
//        registration.addInitParameter("contextConfigLocation", "/act/rest/spring-mvc-rest.xml");
//        return registration;
//    }
    /**
     * druid?????? ??????URI????????????
     * @return
     */
//    @Bean
//    public FilterRegistrationBean druidStatFilter(){
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
//        //??????????????????.
//        filterRegistrationBean.addUrlPatterns("/*");
//        //????????????????????????????????????.
//        filterRegistrationBean.addInitParameter(
//                "exclusions","/static/*,*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid,/druid/*");
//        //??????session?????????????????????????????? ????????????????????????username?????????session???
//        filterRegistrationBean.addInitParameter("principalSessionName","username");
//        return filterRegistrationBean;
//    }

    /**
     * druid????????????????????????
     */
//    @Bean
//    public DruidStatInterceptor druidStatInterceptor() {
//        return new DruidStatInterceptor();
//    }
//
//
//    @Bean
//    public JdkRegexpMethodPointcut druidStatPointcut(){
//        JdkRegexpMethodPointcut druidStatPointcut = new JdkRegexpMethodPointcut();
//        String patterns = "com.jeeplus.modules.*.service.*";
//        //??????set??????
//        druidStatPointcut.setPatterns(patterns);
//        return druidStatPointcut;
//    }
//
//    /**
//     * druid????????????????????????
//     */
//    @Bean
//    public BeanTypeAutoProxyCreator beanTypeAutoProxyCreator() {
//        BeanTypeAutoProxyCreator beanTypeAutoProxyCreator = new BeanTypeAutoProxyCreator();
//        beanTypeAutoProxyCreator.setTargetBeanType(DruidDataSource.class);
//        beanTypeAutoProxyCreator.setInterceptorNames("druidStatInterceptor");
//        return beanTypeAutoProxyCreator;
//    }
//
//    /**
//     * druid ???druidStatPointcut????????????
//     * @return
//     */
//    @Bean
//    public Advisor druidStatAdvisor() {
//        return new DefaultPointcutAdvisor(druidStatPointcut(), druidStatInterceptor());
//    }
//    /**
//     * RequestContextListener??????
//     */
//    @Bean
//    public ServletListenerRegistrationBean<RequestContextListener> requestContextListenerRegistration() {
//        return new ServletListenerRegistrationBean<>(new RequestContextListener());
//    }
//
//
//    @Override
//    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//    }


//    @Bean(initMethod="init",destroyMethod="destroy")
//    public PluginRegister getPluginRegister(){
//        return new PluginRegister();
//    }

}