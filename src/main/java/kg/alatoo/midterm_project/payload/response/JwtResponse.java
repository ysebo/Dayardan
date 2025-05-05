package kg.alatoo.midterm_project.payload.response;

public record JwtResponse(
    String accessToken,
    String refreshToken
) {

}
