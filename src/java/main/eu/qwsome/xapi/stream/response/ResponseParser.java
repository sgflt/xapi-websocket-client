package eu.qwsome.xapi.stream.response;

import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import eu.qwsome.xapi.stream.records.response.BaseResponseRecord;
import eu.qwsome.xapi.stream.records.response.SBalanceRecord;
import eu.qwsome.xapi.stream.records.response.SCandleRecord;
import eu.qwsome.xapi.stream.records.response.SKeepAliveRecord;
import eu.qwsome.xapi.stream.records.response.SNewsRecord;
import eu.qwsome.xapi.stream.records.response.SProfitRecord;
import eu.qwsome.xapi.stream.records.response.STickRecord;
import eu.qwsome.xapi.stream.records.response.STradeRecord;
import eu.qwsome.xapi.stream.records.response.STradeStatusRecord;

public class ResponseParser {

  public BaseResponseRecord parse(final String command, final JSONObject json) {

    final var object = getObject(command);
    if (object != null) {
      object.setFieldsFromJSONObject(json);
    }
    return object;
  }


  @Nullable
  private static BaseResponseRecord getObject(final String command) {
    BaseResponseRecord result = null;
    if (command != null) {
      if (command.equals("tickPrices")) {
        result = new STickRecord();
      } else if (command.equals("trade")) {
        result = new STradeRecord();
      } else if (command.equals("balance")) {
        result = new SBalanceRecord();
      } else if (command.equals("tradeStatus")) {
        result = new STradeStatusRecord();
      } else if (command.equals("profit")) {
        result = new SProfitRecord();
      } else if (command.equals("news")) {
        result = new SNewsRecord();
      } else if (command.equals("keepAlive")) {
        result = new SKeepAliveRecord();
      } else if (command.equals("candle")) {
        result = new SCandleRecord();
      }
    }
    return result;
  }


  public LoginResponse parseLogin(String body) {
    return new LoginResponse(body);
  }


  public TradeTransactionResponse parseTradeTransaction(String body) {
    return new TradeTransactionResponse(body);
  }


  public AllSymbolsResponse parseAllSymbols(final String text) {
    return new AllSymbolsResponse(text);
  }


  public SymbolResponse parseGetSymbol(final String text) {
    return new SymbolResponse(text);
  }


  public TradesResponse parseGetTrades(final String text) {
    return new TradesResponse(text);
  }
}
