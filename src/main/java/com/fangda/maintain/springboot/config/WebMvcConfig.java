package com.fangda.maintain.springboot.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangjian559
 */
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	//定义静态资源的location
    private static final String[] RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/"};

//    @Resource(name = "jacksonMessageConverter")
//    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    /**
     * 内容协商
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        //
        configurer.favorParameter(true);
        configurer.favorPathExtension(false);
        configurer.parameterName("mediaType");
        configurer.ignoreAcceptHeader(true);
        configurer.useJaf(false);
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
        //
        Map<String, MediaType> mediaTypes = new HashMap<>();
        // <entry key="json" value="application/json" />
        mediaTypes.put("json", MediaType.APPLICATION_JSON);
        // <entry key="xml" value="application/xml" />
        mediaTypes.put("xml", MediaType.APPLICATION_XML);
        // <entry key="html" value="text/html" />
        mediaTypes.put("html", MediaType.TEXT_HTML);
        // <entry key="atom" value="application/atom+xml" />
        mediaTypes.put("atom", MediaType.APPLICATION_ATOM_XML);
        configurer.mediaTypes(mediaTypes);

    }


    /**
     * {@inheritDoc}
     * <p>
     * This implementation is empty.
     */
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(jacksonMessageConverter);
        converters.add(new FormHttpMessageConverter());
    }

    /**
     * 静态资源的处理
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("classpath:/uploadfile/**").addResourceLocations("/app/fangda/uploadfile/");
        if (!registry.hasMappingForPattern("/webjars/**")) {
            registry.addResourceHandler("/webjars/**").addResourceLocations(
                    "classpath:/META-INF/resources/webjars/");
        }
        if (!registry.hasMappingForPattern("/**")) {
            registry.addResourceHandler("/**").addResourceLocations(RESOURCE_LOCATIONS);
        }
        super.addResourceHandlers(registry);
    }
    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
//    @Bean
//    public MultipartResolver multipartResolver(){
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//        multipartResolver.setMaxUploadSize(1000000);
//        return multipartResolver;
//    }

    /**
     * 文件上传路径
     */
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation("D:/app/fangda/uploadfile");
        return factory.createMultipartConfig();
    }
}
