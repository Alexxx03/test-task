package org.job.interview.roombookingservice.security;

import lombok.extern.slf4j.Slf4j;
import org.job.interview.roombookingservice.security.jwt.JwtAuthentication;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
public class AuthenticatedUserDetailsArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return AuthenticatedUserDetails.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new IllegalStateException("Current request is not in secured context");
        }
        AuthenticatedUserDetails userDetails = null;
        if (authentication instanceof JwtAuthentication) {
            userDetails = ((JwtAuthentication) authentication).getAuthenticatedUserDetails();
        } else {
            log.warn("User principals has not {} type, but uses as argument in secured context",
                    AuthenticatedUserDetails.class.getSimpleName());
        }
        return userDetails;
    }
}
