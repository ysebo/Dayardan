package kg.alatoo.midterm_project.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class IpBlacklistService {

  private final Set<String> blacklistedIps = ConcurrentHashMap.newKeySet();
  private final Map<String, Long> temporaryBlocks = new ConcurrentHashMap<>();
  private static final long BLOCK_DURATION_MS = 15 * 60 * 1000;

  public boolean isIpBlacklisted(String ipAddress) {
    if (blacklistedIps.contains(ipAddress)) {
      return true;
    }

    Long unblockTime = temporaryBlocks.get(ipAddress);
    if (unblockTime != null) {
      if (System.currentTimeMillis() < unblockTime) {
        return true;
      } else {
        temporaryBlocks.remove(ipAddress);
        return false;
      }
    }
    return false;
  }

  public void blacklistIpPermanently(String ipAddress) {
    blacklistedIps.add(ipAddress);
  }

  public void blacklistIpTemporarily(String ipAddress) {
    temporaryBlocks.put(ipAddress, System.currentTimeMillis() + BLOCK_DURATION_MS);
  }

  public void removeFromBlacklist(String ipAddress) {
    blacklistedIps.remove(ipAddress);
    temporaryBlocks.remove(ipAddress);
  }

  public List<String> getBlacklistedIps() {
    List<String> allBlocked = new ArrayList<>(blacklistedIps);

    temporaryBlocks.forEach((ip, time) -> {
      if (System.currentTimeMillis() < time) {
        allBlocked.add(ip + " (temporary)");
      }
    });

    return allBlocked;
  }
}