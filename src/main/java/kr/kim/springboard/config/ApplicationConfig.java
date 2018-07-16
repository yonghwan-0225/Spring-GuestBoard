package kr.kim.springboard.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages= {"kr.kim.springboard.dao", "kr.kim.springboard.service"})
@Import({DBConfig.class})
public class ApplicationConfig {

}
