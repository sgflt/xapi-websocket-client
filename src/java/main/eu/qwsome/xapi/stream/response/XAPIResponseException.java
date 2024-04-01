package eu.qwsome.xapi.stream.response;


import lombok.Getter;
import lombok.ToString;

import eu.qwsome.xapi.stream.codes.ErrorCode;

@Getter
@ToString
public class XAPIResponseException extends RuntimeException {

  private final ErrorCode code;
  private final String errDesc;
  private final String msg;


  /**
   * Constructs an instance of
   * <code>XAPIResponseException</code> with the specified detail message.
   *
   * @param msg the detail message.
   */
  public XAPIResponseException(final ErrorCode code, final String errDesc, final String msg) {
    super(msg);
    this.code = code;
    this.errDesc = errDesc;
    this.msg = msg;
  }


  @Override
  public String getMessage() {
    return "ErrorCode = "
           + this.code.getStringCode()
           + "ERR_DESC = "
           + this.errDesc
           + "\n"
           + this.msg
           + "\n"
           + super.getMessage();
  }
}
