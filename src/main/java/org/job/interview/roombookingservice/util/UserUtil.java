package org.job.interview.roombookingservice.util;

import org.job.interview.roombookingservice.exceptions.InvalidAuthenticationType;
import org.job.interview.roombookingservice.exceptions.UnauthenticatedRequest;
import org.job.interview.roombookingservice.security.AuthenticatedUserDetails;
import org.job.interview.roombookingservice.security.jwt.JwtAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {

    public static AuthenticatedUserDetails getLoggedInUser() {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication == null) {
            throw new UnauthenticatedRequest(HttpStatus.UNAUTHORIZED, "Unauthenticated request");
        }
        return (AuthenticatedUserDetails) authentication.getPrincipal();
//        if (authentication instanceof JwtAuthentication) {
//            return ((JwtAuthentication) authentication).getAuthenticatedUserDetails();
//        } else {
//            throw new InvalidAuthenticationType(HttpStatus.BAD_REQUEST, "Invalid authentication type");
//        }
    }
}
