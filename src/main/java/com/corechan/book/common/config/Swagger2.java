package com.corechan.book.common.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class Swagger2 {
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.corechan.book"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("书蕴——基于书评的书籍推荐系统")
                .description("计算机设计大赛加油！<br/>迅猛发展的社交网络正深刻地改变着人们的学习、生活方式，其中网络书评对阅读选择也产生了不可忽视的影响。本项目拟利用网络书评的客观性，为读者推荐更符合个人偏好的书籍，而非依靠热门排行或基于用户行为进行推荐。本项目从书评文本内容出发，利用分类方法甄别有效评论、利用文本处理技术建立书籍的标签集、利用深度学习技术计算不同书籍的标签集之间的关联度，从而达到基于书评内容推荐书籍的目的。")
                .contact(new Contact("Core","http://corechan.cn","i@corechan.cn"))
                .version("0.1")
                .build();
    }
}
