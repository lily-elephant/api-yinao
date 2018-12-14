package yinao.qualityLife;

import javax.servlet.MultipartConfigElement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
@SpringBootApplication
@MapperScan(value = {"yinao.qualityLife.dao"})
public class QualityLifeApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(QualityLifeApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(QualityLifeApplication.class, args);
	}
	
	/**
     * 文件上传配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大
        factory.setMaxFileSize("1048576000KB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("1048576000KB");
        return factory.createMultipartConfig();
    }
}
