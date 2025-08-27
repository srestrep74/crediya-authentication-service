package co.com.crediya.api.config;

import co.com.crediya.api.exception.GlobalExceptionHandler;
import co.com.crediya.model.user.exception.EmailAlreadyExistsException;
import co.com.crediya.model.user.exception.InvalidUserDataException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;

import java.util.Map;

@TestConfiguration
public class ExceptionConfig {

    @Bean
    public Map<Class<? extends Exception>, HttpStatus> exceptionToStatusCode() {
        return Map.of(
                EmailAlreadyExistsException.class, HttpStatus.CONFLICT,
                InvalidUserDataException.class, HttpStatus.BAD_REQUEST,
                ConstraintViolationException.class, HttpStatus.BAD_REQUEST,
                IllegalArgumentException.class, HttpStatus.BAD_REQUEST
        );
    }

    @Bean
    public HttpStatus defaultStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Bean
    @Order(-2)
    public GlobalExceptionHandler globalExceptionHandler(
            WebProperties webProperties,
            ApplicationContext applicationContext,
            ServerCodecConfigurer configurer
    ) {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler(
                new DefaultErrorAttributes(),
                webProperties.getResources(),
                applicationContext,
                exceptionToStatusCode(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        globalExceptionHandler.setMessageWriters(configurer.getWriters());
        globalExceptionHandler.setMessageReaders(configurer.getReaders());

        return globalExceptionHandler;
    }
}
