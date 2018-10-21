package hu.bme.szarch.ibdb.filter;

import org.hibernate.annotations.Filter;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;

@Filter(name = "OAuthFilter")
public class OAuthFilter extends OAuth2AuthenticationProcessingFilter {
}
