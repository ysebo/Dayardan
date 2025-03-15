package kg.alatoo.midterm_project.exceptions;

public class NotFoundException extends RuntimeException {

  public NotFoundException() {
    super("Not Found");
  }

  public NotFoundException(String message) {
    super(message);
  }
}

