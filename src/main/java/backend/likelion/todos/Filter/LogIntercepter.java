package backend.likelion.todos.Filter;

import java.util.UUID;
import java.util.logging.Handler;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogIntercepter implements HandlerInterceptor {
	public static String LOG_ID = "logID";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		String requestURI = request.getRequestURI();
		String uuid = UUID.randomUUID().toString();

		request.setAttribute(LOG_ID, uuid);

		if(handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
		}

		log.info("REQUEST [{}][{}][{}]", uuid, requestURI, handler);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {
		log.info("postHandle [{}]", request.getAttribute(LOG_ID));
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
		Exception ex) throws Exception {
		String requestURI = request.getRequestURI();
		String logID = (String)request.getAttribute(LOG_ID);

		log.info("RESPONSE [{}][{}][{}]", logID, requestURI, handler);
		if(ex != null) {
			log.error("afterCompletion error!!", ex);
		}
	}
}
