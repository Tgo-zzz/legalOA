package cn.tgozzz.legal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.CacheControl;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableWebFlux
public class LegalOaApplication implements WebFluxConfigurer {

	// 开启跨域
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("*")
				.maxAge(3500);
	}

	// 配置静态资源目录
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler( "/static/**")
				.addResourceLocations("classpath:dist/static/")
				.setCacheControl(CacheControl.noCache());
		registry.addResourceHandler( "/admin/**")
				.addResourceLocations("classpath:admin/")
				.setCacheControl(CacheControl.noCache());
		registry.addResourceHandler( "/apidoc/*")
				.addResourceLocations("classpath:apidoc/")
				.setCacheControl(CacheControl.noCache());
		registry.addResourceHandler("/signss/**")
				.addResourceLocations("classpath:signss/")
				.setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
		registry.addResourceHandler("/resources/**")
				.addResourceLocations("classpath:static/", "/srv/public/legalOA")
				.setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
	}

	public static void main(String[] args) {
		SpringApplication.run(LegalOaApplication.class, args);
	}
}
