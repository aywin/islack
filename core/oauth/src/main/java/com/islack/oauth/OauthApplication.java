package com.islack.oauth;

import com.islack.oauth.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.ManagementServerProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.web.filter.ForwardedHeaderFilter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.security.KeyPair;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Thibaud Leprêtre
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableWebSecurity
public class OauthApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(OauthApplication.class, args);
    }

    @Bean
    FilterRegistrationBean forwardedHeaderFilter() {
        FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
        filterRegBean.setFilter(new ForwardedHeaderFilter());
        filterRegBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filterRegBean;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/oauth/confirm_access").setViewName("authorize");
    }

    @Order(ManagementServerProperties.ACCESS_OVERRIDE_ORDER)
    @Configuration
    protected static class LoginConfiguration extends WebSecurityConfigurerAdapter {
        @Autowired
        @Qualifier("userDetailsServiceImpl")
        private UserDetailsService userDetailsService;

        @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        /*@Bean(name = "passEncoder")
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }*/

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.formLogin().loginPage("/login").permitAll().and().authorizeRequests()
                    .anyRequest().authenticated();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            //auth.inMemoryAuthentication()
            //        .withUser("user").password("password").roles("USER")
            //        .and()
            //        .withUser("admin").password("admin").roles("ADMIN");
            //auth.parentAuthenticationManager(authenticationManager);
            auth.userDetailsService(userDetailsService);//.passwordEncoder(passwordEncoder());
        }
    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

       /* @Autowired
        @Qualifier("passEncoder")
        private PasswordEncoder passwordEncoder;*/

        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter converter = new CustomTokenEnhancer();
            KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource("keystore.jks"), "foobar".toCharArray())
                    .getKeyPair("test");
            converter.setKeyPair(keyPair);
            return converter;
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory()
                    .withClient("webapp")
                    .secret("webapp")
                    .authorizedGrantTypes("authorization_code", "refresh_token","password", "client_credentials")
                    .scopes("openid");
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.authenticationManager(authenticationManager).accessTokenConverter(jwtAccessTokenConverter());
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
            oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
        }

        protected static class CustomTokenEnhancer extends JwtAccessTokenConverter {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                Account account = (Account) authentication.getPrincipal();

                Map<String, Object> info = new LinkedHashMap<>(accessToken.getAdditionalInformation());

                info.put("email", account.getEmail());
                info.put("id", account.getId());

                DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);

                // Get the authorities from the account
                Set<GrantedAuthority> authoritiesSet = new HashSet<>(authentication.getAuthorities());

                // Generate String array
                String[] authorities = new String[authoritiesSet.size()];

                int i = 0;
                for (GrantedAuthority authority : authoritiesSet)
                    authorities[i++] = authority.getAuthority();

                info.put("authorities", authorities);
                customAccessToken.setAdditionalInformation(info);

                return super.enhance(customAccessToken, authentication);
            }
        }

        /*
         * Setup the refresh_token functionality to work with the custom
         * UserDetailsService
         */
        @Configuration
        protected static class GlobalAuthenticationManagerConfiguration extends GlobalAuthenticationConfigurerAdapter {

            @Autowired
            @Qualifier("userDetailsServiceImpl")
            private UserDetailsService userDetailsService;

            /*@Autowired
            @Qualifier("passEncoder")
            private PasswordEncoder passwordEncoder;*/

            @Override
            public void init(AuthenticationManagerBuilder auth) throws Exception {

                auth.userDetailsService(userDetailsService);
            }
        }

    }
}
