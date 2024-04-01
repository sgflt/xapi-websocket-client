package eu.qwsome.xapi.stream.codes;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class ResponseCode {

  private final long code;


  public ResponseCode(final long code) {
    this.code = code;
  }
}
