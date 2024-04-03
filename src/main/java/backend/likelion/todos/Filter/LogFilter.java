package backend.likelion.todos.Filter;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebFilter
@Component
@Order(1)
public class LogFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Filter.super.init(filterConfig);
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
		throws
		IOException,
		ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String requestURI = request.getRequestURI();
		String uuid = UUID.randomUUID().toString();
		log.info("logFilter1 requestURI : {}, uuid : {}", requestURI, uuid);
		filterChain.doFilter(servletRequest,servletResponse);
	}

	@Override
	public void destroy() {
		Filter.super.destroy();
	}
}
