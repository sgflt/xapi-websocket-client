package eu.qwsome.xapi.sync.command;

import eu.qwsome.xapi.sync.Credentials;

public class LoginCommand extends BaseCommand {

  public LoginCommand(final Credentials arguments) {
    super(arguments.toJson());
  }


  @Override
  public String getCommandName() {
    return "login";
  }
}
