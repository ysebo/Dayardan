package kg.alatoo.midterm_project.payload.request;

public record UserRequest(
    String username,
    String email,
    String password
) {

}