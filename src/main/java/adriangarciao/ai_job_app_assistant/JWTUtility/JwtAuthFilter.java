package adriangarciao.ai_job_app_assistant.JWTUtility;

import adriangarciao.ai_job_app_assistant.repository.UserRepository;
import adriangarciao.ai_job_app_assistant.security.AppPrincipal;
import adriangarciao.ai_job_app_assistant.service.JwtService;
import io.jsonwebtoken.JwtException;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        // 1) Let CORS preflight pass
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            chain.doFilter(req, res);
            return;
        }

        // 2) Skip auth endpoints (they’re permitAll and don’t need JWT auth)
        String uri = req.getRequestURI();
        if (uri.startsWith("/api/auth/")) {
            chain.doFilter(req, res);
            return;
        }

        // 3) If already authenticated, continue
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            chain.doFilter(req, res);
            return;
        }

        // 4) Expect Bearer token
        String auth = req.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            chain.doFilter(req, res);
            return;
        }

        String token = auth.substring(7);
        try {
            var jws = jwtService.parse(token);
            var body = jws.getBody();

            String email = body.getSubject();
            Number uidNum = body.get("uid", Number.class);
            Long uid = (uidNum != null) ? uidNum.longValue() : null;  // should be present, but be defensive
            String role = body.get("role", String.class);
            if (email == null || uid == null || role == null) {
                // malformed token → skip auth
                chain.doFilter(req, res);
                return;
            }

            var principal = new AppPrincipal(uid, email, role); // your record: (id, email, role)
            var authorities = java.util.List.<GrantedAuthority>of(new SimpleGrantedAuthority("ROLE_" + role));

            var authToken = new UsernamePasswordAuthenticationToken(principal, null, authorities);
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (io.jsonwebtoken.JwtException e) {
            // invalid/expired → leave unauthenticated
            SecurityContextHolder.clearContext();
        }

        chain.doFilter(req, res);
    }
}