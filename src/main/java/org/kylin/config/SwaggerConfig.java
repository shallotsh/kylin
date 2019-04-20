package org.kylin.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
@ComponentScan(basePackages = {"org.kylin.web", "org.kylin.api"})
@EnableWebMvc
public class SwaggerConfig extends WebMvcConfigurationSupport {

    @Bean
    public Docket customDocket() {
        //
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("我要发", "https://wyfa.top", "shallotsh@gmail.com");
        return new ApiInfo("我要发API接口",//大标题 title
                "Swagger测试demo",//小标题
                "0.0.1",//版本
                "wyfa.top",//termsOfServiceUrl
                contact,//作者
                "Wyf",//链接显示文字
                "https://wyfa.top"//网站链接
        );
    }


}
