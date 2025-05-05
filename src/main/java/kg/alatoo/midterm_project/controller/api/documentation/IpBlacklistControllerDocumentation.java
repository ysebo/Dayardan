package kg.alatoo.midterm_project.controller.api.documentation;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/admin/ip-blacklist")
public interface IpBlacklistControllerDocumentation {
  @PostMapping("/block")
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<?> blockIp(@RequestParam String ipAddress,
      @RequestParam(required = false) Boolean temporary);

  @PostMapping("/unblock")
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<?> unblockIp(@RequestParam String ipAddress);

  @GetMapping("/list")
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<List<String>> getBlacklistedIps();
}
