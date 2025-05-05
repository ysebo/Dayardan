package kg.alatoo.midterm_project.service.impl.oauth2;

import java.util.Map;
import kg.alatoo.midterm_project.service.OAuth2UserInfo;

public class GithubOAuth2UserInfo implements OAuth2UserInfo {
  private Map<String, Object> attributes;

  public GithubOAuth2UserInfo(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  @Override
  public String getId() {
    return (String) attributes.get("sub");
  }

  @Override
  public String getName() {
    return (String) attributes.get("name");
  }

  @Override
  public String getEmail() {
    return (String) attributes.get("email");
  }

  @Override
  public String getImageUrl() {
    return (String) attributes.get("image");
  }
}
