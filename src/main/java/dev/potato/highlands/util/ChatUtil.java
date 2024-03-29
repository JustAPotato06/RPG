package dev.potato.highlands.util;

import org.bukkit.entity.Player;

public class ChatUtil {

    private static final String successPrefix = "&2&lSUCCESSFUL ";
    private static final String failPrefix = "&4&lFAILED ";

    public static void sendLegacyMessage(Player player, MessageType type, String msg) {
        switch (type) {
            case SUCCESSFUL -> player.sendMessage(TextUtil.translateLegacy(successPrefix +"&r&a"+ msg));
            case FAILED -> player.sendMessage(TextUtil.translateLegacy(failPrefix +"&r&c"+ msg));
        }
    }

}
