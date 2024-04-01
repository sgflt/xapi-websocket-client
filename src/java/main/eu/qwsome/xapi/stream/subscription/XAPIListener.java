package eu.qwsome.xapi.stream.subscription;


import eu.qwsome.xapi.stream.records.response.SBalanceRecord;
import eu.qwsome.xapi.stream.records.response.SCandleRecord;
import eu.qwsome.xapi.stream.records.response.SKeepAliveRecord;
import eu.qwsome.xapi.stream.records.response.SNewsRecord;
import eu.qwsome.xapi.stream.records.response.SProfitRecord;
import eu.qwsome.xapi.stream.records.response.STickRecord;
import eu.qwsome.xapi.stream.records.response.STradeRecord;
import eu.qwsome.xapi.stream.records.response.STradeStatusRecord;

public interface XAPIListener {
  void receiveTradeRecord(STradeRecord tradeRecord);

  void receiveTickRecord(STickRecord tickRecord);

  void receiveBalanceRecord(SBalanceRecord balanceRecord);

  void receiveNewsRecord(SNewsRecord newsRecord);

  void receiveTradeStatusRecord(STradeStatusRecord tradeStatusRecord);

  void receiveProfitRecord(SProfitRecord profitRecord);

  void receiveKeepAliveRecord(SKeepAliveRecord keepAliveRecord);

  void receiveCandleRecord(SCandleRecord candleRecord);
}
