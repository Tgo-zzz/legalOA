package cn.tgozzz.legal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.CacheControl;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.nio.charset.Charset;
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
				.maxAge(3600);
	}

	// 配置静态资源目录
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler( "/apidoc/*")
				.addResourceLocations("classpath:apidoc/")
				.setCacheControl(CacheControl.noCache());
		registry.addResourceHandler("/resources/**")
				.addResourceLocations("classpath:static/")
				.setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
	}

	public static void main(String[] args) {
		SpringApplication.run(LegalOaApplication.class, args);
	}
}
