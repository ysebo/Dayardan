package kg.alatoo.midterm_project.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import kg.alatoo.midterm_project.service.impl.IpBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class IpBlacklistFilter extends OncePerRequestFilter {

  private final IpBlacklistService ipBlacklistService;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {

    String ipAddress = request.getRemoteAddr();

    if (ipBlacklistService.isIpBlacklisted(ipAddress)) {
      response.setStatus(HttpStatus.FORBIDDEN.value());
      response.getWriter().write("Your IP address has been blocked");
      return;
    }

    filterChain.doFilter(request, response);
  }
}