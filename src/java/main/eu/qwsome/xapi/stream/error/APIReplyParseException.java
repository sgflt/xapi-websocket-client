package eu.qwsome.xapi.stream.error;

public class APIReplyParseException extends RuntimeException {


  /**
   * Creates a new instance of
   * <code>APIReplyParseException</code> without detail message.
   */
  public APIReplyParseException() {
  }


  /**
   * Constructs an instance of
   * <code>APIReplyParseException</code> with the specified detail message.
   *
   * @param msg the detail message.
   */
  public APIReplyParseException(final String msg) {
    super(msg);
  }
}
