package io.github.ma1uta.identity

import io.github.ma1uta.identity.key.KeyGenerateSelfCertificate
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

@Configuration
@EnableConfigurationProperties(value = [IdentityProperties::class])
class IdentityConfiguration {

    @Bean
    @ConditionalOnMissingBean
    fun defaultKeyGenerator(properties: IdentityProperties) = KeyGenerateSelfCertificate(properties)

    @Bean
    fun propertySourcesPlaceholderConfigurer() = PropertySourcesPlaceholderConfigurer().apply { setPlaceholderPrefix("%{") }
}
