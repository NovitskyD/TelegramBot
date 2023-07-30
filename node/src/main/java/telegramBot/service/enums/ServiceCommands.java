package telegramBot.service.enums;

import java.util.HashMap;
import java.util.Map;

public enum ServiceCommands {
    HELP("/help"),
    REGISTRATION("/registration"),
    CANCEL("/cancel"),
    START("/start");
    private final String cmd;
    private static final Map<String, ServiceCommands> cmdMap = new HashMap<>();

    static {
        for (ServiceCommands command : ServiceCommands.values()){
            cmdMap.put(command.cmd, command);
        }
    }

    ServiceCommands(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString(){
        return cmd;
    }

    public static ServiceCommands fromCmd(String cmd){
        return cmdMap.get(cmd);
    }
}
