package kg.alatoo.midterm_project.controller.api.documentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import kg.alatoo.midterm_project.payload.request.QuestionRequest;
import kg.alatoo.midterm_project.payload.response.QuestionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/questions")
@Tag(name = "Question Controller", description = "APIs for managing questions")
public interface QuestionControllerDocumentation {

  @GetMapping("/get-all")
  @Operation(summary = "Get all questions", description = "Get all questions from the database")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved all questions"),
      @ApiResponse(responseCode = "500", description = "Internal server error"),
      @ApiResponse(responseCode = "403", description = "Forbidden")
  })
  ResponseEntity<List<QuestionResponse>> getAllQuestions();

  @GetMapping("/get/{id}")
  @Operation(summary = "Get question by ID", description = "Get a question by its ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved the question"),
      @ApiResponse(responseCode = "404", description = "Question not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  ResponseEntity<QuestionResponse> getQuestionById(@PathVariable Long id);

  @PostMapping("/create")
  @Operation(summary = "Create question", description = "Create a new question")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Successfully created the question"),
      @ApiResponse(responseCode = "400", description = "Invalid input"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  ResponseEntity<QuestionResponse> createQuestion(@Valid @RequestBody QuestionRequest questionRequest);

  @PutMapping("/update/{id}")
  @Operation(summary = "Update question", description = "Update a question by its ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully updated the question"),
      @ApiResponse(responseCode = "404", description = "Question not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  ResponseEntity<QuestionResponse> updateQuestion(@PathVariable Long id, @Valid @RequestBody QuestionRequest questionRequest);

  @DeleteMapping("/delete/{id}")
  @Operation(summary = "Delete question", description = "Delete a question by its ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Successfully deleted the question"),
      @ApiResponse(responseCode = "404", description = "Question not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  ResponseEntity<?> deleteQuestion(@PathVariable Long id);
}