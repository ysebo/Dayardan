package kg.alatoo.midterm_project.controller.api;

import java.util.List;
import kg.alatoo.midterm_project.controller.api.documentation.IpBlacklistControllerDocumentation;
import kg.alatoo.midterm_project.service.impl.IpBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IpBlacklistController implements IpBlacklistControllerDocumentation {

  private final IpBlacklistService ipBlacklistService;
  public ResponseEntity<?> blockIp(@RequestParam String ipAddress,
      @RequestParam(required = false) Boolean temporary) {
    if (temporary != null && temporary) {
      ipBlacklistService.blacklistIpTemporarily(ipAddress);
    } else {
      ipBlacklistService.blacklistIpPermanently(ipAddress);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  public ResponseEntity<?> unblockIp(@RequestParam String ipAddress) {
    ipBlacklistService.removeFromBlacklist(ipAddress);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }


  public ResponseEntity<List<String>> getBlacklistedIps() {
    return new ResponseEntity<>(ipBlacklistService.getBlacklistedIps(), HttpStatus.OK);
  }
}
