package eu.qwsome.xapi.sync.chart;

import lombok.Getter;

/**
 * @author Lukáš Kvídera
 */
@Getter
public enum PeriodCode {
  M1(1L),
  M5(5L),
  M15(15L),
  M30(30L),
  H1(60L),
  H4(240L),
  D1(1440L),
  W1(10080L),
  MN1(43200L);

  public final long code;


  PeriodCode(final long code) {
    this.code = code;
  }
}
