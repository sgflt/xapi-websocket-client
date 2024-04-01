package eu.qwsome.xapi.sync.command;

import java.util.Objects;

import org.json.JSONObject;

/**
 * Abstract base class for each Command.
 */
public abstract class BaseCommand {

  protected String commandName;
  protected JSONObject arguments;


  /**
   * Constructor for base command.
   * @param arguments Command arguments (see xAPI Documentation)
   */
  protected BaseCommand(final JSONObject arguments) {
    this.commandName = Objects.requireNonNull(getCommandName());
    this.arguments = Objects.requireNonNull(arguments);
  }


  /**
   * API inter-command timeout (if same command is executed more frequently than this number of milliseconds, RR-Socket disconnects)
   * @return inter-command timeout
   */
  public Long getTimeoutMillis() {
    return 250L;
  }


  /**
   * Returns command name
   * @return command name
   */
  public abstract String getCommandName();


  /**
   * Creates a JSON String from this object
   * @return
   */
  public String toJSONString() {
    final var obj = new JSONObject();
    obj.put("command", this.commandName);
    obj.put("prettyPrint", false);
    obj.put("arguments", this.arguments);
    return obj.toString();
  }


  @Override
  public String toString() {
    return "BaseCommand [commandName=" + this.commandName + ", arguments="
           + this.arguments + "]";
  }
}
