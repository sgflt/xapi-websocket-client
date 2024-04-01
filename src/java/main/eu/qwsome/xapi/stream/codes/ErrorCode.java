package eu.qwsome.xapi.stream.codes;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ErrorCode {

  private final String stringCode;


  public ErrorCode(final String code) {
    this.stringCode = code;
  }
}
