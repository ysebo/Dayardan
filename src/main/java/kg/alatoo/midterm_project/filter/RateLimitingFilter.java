package kg.alatoo.midterm_project.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import kg.alatoo.midterm_project.service.impl.IpBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class RateLimitingFilter extends OncePerRequestFilter {

  private final IpBlacklistService ipBlacklistService;
  private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
  private final Map<String, AtomicInteger> requestCounts = new ConcurrentHashMap<>();
  private final Map<String, Integer> violationCounts = new ConcurrentHashMap<>();
  private static final int MAX_REQUESTS_PER_MINUTE = 100;
  private static final int MAX_VIOLATIONS_BEFORE_BAN = 5;
  private static final long RESET_INTERVAL_MS = 60 * 1000; // 1 minute

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

    AtomicInteger count = requestCounts.computeIfAbsent(ipAddress, k -> new AtomicInteger(0));
    int currentCount = count.incrementAndGet();

    response.setHeader("X-RateLimit-Limit", String.valueOf(MAX_REQUESTS_PER_MINUTE));
    response.setHeader("X-RateLimit-Remaining",
        String.valueOf(Math.max(0, MAX_REQUESTS_PER_MINUTE - currentCount)));

    if (currentCount > MAX_REQUESTS_PER_MINUTE) {
      int violations = violationCounts.merge(ipAddress, 1, Integer::sum);

      if (violations >= MAX_VIOLATIONS_BEFORE_BAN) {
        ipBlacklistService.blacklistIpTemporarily(ipAddress);
        violationCounts.remove(ipAddress);
      }

      response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
      response.getWriter().write("Rate limit exceeded");
      return;
    }

    if (currentCount == 1) {
      scheduler.schedule(() -> {
        requestCounts.remove(ipAddress);
        if (currentCount <= MAX_REQUESTS_PER_MINUTE) {
          violationCounts.remove(ipAddress);
        }
      }, RESET_INTERVAL_MS, TimeUnit.MILLISECONDS);
    }

    filterChain.doFilter(request, response);
  }
}