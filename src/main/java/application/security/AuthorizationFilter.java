package application.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Value("${rest.auth.enabled}")
    private boolean enabled;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        logger.debug("request.getRequestURI() :[" + path + "]");
        if (path.contains("/hmac")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!enabled){
            logger.info("Without authorization");
            filterChain.doFilter(request, response);
        }
        else{
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        ContentCachingRequestWrapper cachingRequestWrapper = new ContentCachingRequestWrapper(request);
        if (authHeader == null || authHeader.isEmpty()){
            logger.info(HttpServletResponse.SC_UNAUTHORIZED);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        else if (!checkAuthorization(authHeader,cachingRequestWrapper)){
            logger.info(HttpServletResponse.SC_FORBIDDEN);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        else
            filterChain.doFilter(cachingRequestWrapper, response);
        }
    }

    private boolean checkAuthorization(String auth, ContentCachingRequestWrapper cachingRequestWrapper) throws IOException {
        if (!auth.startsWith("Bearer "))
            return false;
        String token = auth.substring(7);
        /*ContentCachingRequestWrapper cachingRequestWrapper = new ContentCachingRequestWrapper(request);*/
        //String body = "hmac:#" + request.hashCode();
        String body = cachingRequestWrapper.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        /*RequestWrapper requestWrapper = new RequestWrapper(request);
        String body = requestWrapper.getBody();*/
        return tokenService.validateToken(token,body);
    }
}