package org.cst8277.GanWei.userservice.security.jwt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.cst8277.GanWei.userservice.config.SecurityConfiguration;
import org.cst8277.GanWei.userservice.config.SecurityJwtConfiguration;
import org.cst8277.GanWei.userservice.config.WebConfigurer;
import org.cst8277.GanWei.userservice.management.SecurityMetersService;
import org.springframework.boot.test.context.SpringBootTest;
import tech.jhipster.config.JHipsterProperties;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(
    classes = {
        JHipsterProperties.class,
        WebConfigurer.class,
        SecurityConfiguration.class,
        SecurityJwtConfiguration.class,
        SecurityMetersService.class,
        JwtAuthenticationTestUtils.class,
    }
)
public @interface AuthenticationIntegrationTest {
}
