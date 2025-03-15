package kg.alatoo.midterm_project.payload.response;

import java.time.LocalDateTime;

public record InterviewSessionResponse(
    Long id,
    Long userId,
    LocalDateTime startedAt,
    LocalDateTime endedAt) {

}
