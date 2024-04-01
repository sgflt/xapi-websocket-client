package eu.qwsome.xapi.stream.codes;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class RequestStatus extends ResponseCode {

  public static final RequestStatus ERROR = new RequestStatus(0L);
  public static final RequestStatus PENDING = new RequestStatus(1L);
  public static final RequestStatus ACCEPTED = new RequestStatus(3L);
  public static final RequestStatus REJECTED = new RequestStatus(4L);


  public RequestStatus(final long code) {
    super(code);
  }
}
