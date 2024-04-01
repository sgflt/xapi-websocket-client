package eu.qwsome.xapi.stream.subscription;

import eu.qwsome.xapi.stream.records.response.SBalanceRecord;
import eu.qwsome.xapi.stream.records.response.SCandleRecord;
import eu.qwsome.xapi.stream.records.response.SKeepAliveRecord;
import eu.qwsome.xapi.stream.records.response.SNewsRecord;
import eu.qwsome.xapi.stream.records.response.SProfitRecord;
import eu.qwsome.xapi.stream.records.response.STickRecord;
import eu.qwsome.xapi.stream.records.response.STradeRecord;
import eu.qwsome.xapi.stream.records.response.STradeStatusRecord;

public class DefaultXAPIListener implements XAPIListener {

  @Override
  public void receiveTradeRecord(final STradeRecord tradeRecord) {
    throw new UnsupportedOperationException("listener method not implemented for receiveTradeRecord(tradeRecord)");
  }


  @Override
  public void receiveTickRecord(final STickRecord tickRecord) {
    throw new UnsupportedOperationException("listener method not implemented for receiveTickRecord(tickRecord)");
  }


  @Override
  public void receiveBalanceRecord(final SBalanceRecord balanceRecord) {
    throw new UnsupportedOperationException("listener method not implemented for receiveBalanceRecord(balanceRecord)");
  }


  @Override
  public void receiveNewsRecord(final SNewsRecord newsRecord) {
    throw new UnsupportedOperationException("listener method not implemented for receiveNewsRecord(newsRecord)");
  }


  @Override
  public void receiveKeepAliveRecord(final SKeepAliveRecord keepAliveRecord) {
    throw new UnsupportedOperationException(
        "listener method not implemented for receiveKeepAliveRecord(keepAliveRecord)");
  }


  @Override
  public void receiveCandleRecord(final SCandleRecord candleRecord) {
    throw new UnsupportedOperationException("listener method not implemented for receiveCandleRecord(candleRecord)");
  }


  @Override
  public void receiveTradeStatusRecord(final STradeStatusRecord tradeStatusRecord) {
    throw new UnsupportedOperationException(
        "listener method not implemented for receiveTradeStatusRecord(tradeStatusRecord)");
  }


  @Override
  public void receiveProfitRecord(final SProfitRecord profitRecord) {
    throw new UnsupportedOperationException("listener method not implemented for receiveProfitRecord(profitRecord)");
  }
}
