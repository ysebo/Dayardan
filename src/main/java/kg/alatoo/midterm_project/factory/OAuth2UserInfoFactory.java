package kg.alatoo.midterm_project.factory;

import java.util.Map;
import kg.alatoo.midterm_project.service.OAuth2UserInfo;
import kg.alatoo.midterm_project.service.impl.oauth2.FacebookOAuth2UserInfo;
import kg.alatoo.midterm_project.service.impl.oauth2.GithubOAuth2UserInfo;
import kg.alatoo.midterm_project.service.impl.oauth2.GoogleOAuth2UserInfo;

public class OAuth2UserInfoFactory {
  public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
    switch (registrationId.toLowerCase()) {
      case "google":
        return new GoogleOAuth2UserInfo(attributes);
      case "facebook":
        return new FacebookOAuth2UserInfo(attributes);
      case "github":
        return new GithubOAuth2UserInfo(attributes);
      default:
        throw new IllegalArgumentException("Unsupported OAuth2 provider");
    }
  }
}