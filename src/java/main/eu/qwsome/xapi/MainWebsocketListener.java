package eu.qwsome.xapi;

import java.util.concurrent.LinkedBlockingDeque;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.jetbrains.annotations.NotNull;

import eu.qwsome.xapi.stream.response.AllSymbolsResponse;
import eu.qwsome.xapi.stream.response.LoginResponse;
import eu.qwsome.xapi.stream.response.ResponseParser;
import eu.qwsome.xapi.stream.response.SymbolResponse;
import eu.qwsome.xapi.stream.response.TradeTransactionResponse;

/**
 * Allows only one command to be executed at the same time.
 *
 * @author Lukáš Kvídera
 */
@Slf4j
public class MainWebsocketListener extends WebSocketListener {

  private final PublishSubject<LoginResponse> loginSubject = PublishSubject.create();
  private final PublishSubject<TradeTransactionResponse> tradeTransactionSubject = PublishSubject.create();
  private final PublishSubject<AllSymbolsResponse> allSymbolsSubject = PublishSubject.create();
  private final PublishSubject<SymbolResponse> getSymbolSubject = PublishSubject.create();

  private final LinkedBlockingDeque<String> command = new LinkedBlockingDeque<>(1);


  @Override
  public synchronized void onMessage(@NotNull final WebSocket webSocket, @NotNull final String text) {
    log.debug("onMessage {}", text);

    final var lastCommand = this.command.pop();
    if ("login".equals(lastCommand)) {
      this.loginSubject.onNext(new ResponseParser().parseLogin(text));
    } else if ("buy".equals(lastCommand) || "closeBuy".equals(lastCommand)) {
      this.tradeTransactionSubject.onNext(new ResponseParser().parseTradeTransaction(text));
    } else if ("allSymbols".equals(lastCommand)) {
      this.allSymbolsSubject.onNext(new ResponseParser().parseAllSymbols(text));
    } else if ("getSymbol".equals(lastCommand)) {
      this.getSymbolSubject.onNext(new ResponseParser().parseGetSymbol(text));
    }
  }


  public void setCommand(final String command) {
    try {
      this.command.put(command);
    } catch (final InterruptedException e) {
      throw new RuntimeException(e);
    }
  }


  public Single<LoginResponse> createLoginStream() {
    return this.loginSubject.subscribeOn(Schedulers.io()).firstElement().toSingle();
  }


  public Single<TradeTransactionResponse> createTradeTransactionStream() {
    return this.tradeTransactionSubject.subscribeOn(Schedulers.io()).firstElement().toSingle();
  }


  public Single<AllSymbolsResponse> createAllSymbolsStream() {
    return this.allSymbolsSubject.subscribeOn(Schedulers.io()).firstElement().toSingle();
  }


  public Single<SymbolResponse> createGetSymbolsStream() {
    return this.getSymbolSubject.subscribeOn(Schedulers.io()).firstElement().toSingle();
  }
}
