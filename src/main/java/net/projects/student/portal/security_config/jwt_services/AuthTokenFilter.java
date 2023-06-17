package net.projects.student.portal.security_config.jwt_services;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.projects.student.portal.security_config.services.UserDetailsServiceImpl;

public class AuthTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String jwtString = parseJwt(request);
			if (jwtString != null && jwtUtils.validateJwtToken(jwtString)) {
				String username = jwtUtils.getUserNameFromJwtToken(jwtString);

				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Cannot set user authentication: {}", e);
		}
		
		filterChain.doFilter(request, response);
	}

	private String parseJwt(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String jwt = jwtUtils.getJwtFromCookies(request);
		return jwt;
	}

}
