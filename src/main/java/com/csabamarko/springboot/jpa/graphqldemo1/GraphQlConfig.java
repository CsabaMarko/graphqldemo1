package com.csabamarko.springboot.jpa.graphqldemo1;

import graphql.scalars.ExtendedScalars;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQlConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return builder -> builder.scalar(ExtendedScalars.DateTime)
                .scalar(ExtendedScalars.PositiveInt).scalar(ExtendedScalars.GraphQLBigDecimal);
    }
}
