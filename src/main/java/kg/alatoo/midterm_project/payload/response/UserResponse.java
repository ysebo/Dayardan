package kg.alatoo.midterm_project.payload.response;


public record UserResponse(
    Long id,
    String username,
    String email
) {

}