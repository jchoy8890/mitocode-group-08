package com.mitocode.microservices.user_service.config;

import com.mitocode.microservices.common_models.model.entity.UserEntity;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RestConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.setBasePath("/api/mitocode");
        config.exposeIdsFor(UserEntity.class);
        config.getExposureConfiguration().forDomainType(UserEntity.class).withCollectionExposure(((metdata, httpMethods) ->
                httpMethods.enable(HttpMethod.GET, HttpMethod.PATCH)
        ));
//        config.getExposureConfiguration().forDomainType(UserEntity.class).withItemExposure(((metdata, httpMethods) ->
//                httpMethods.disable(HttpMethod.DELETE)
//        ));

        config.useHalAsDefaultJsonMediaType(false);
        config.setDefaultMediaType(MediaType.APPLICATION_JSON);
    }
}
