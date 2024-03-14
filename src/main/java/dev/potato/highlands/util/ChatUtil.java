package dev.potato.highlands.util;

import org.bukkit.entity.Player;

public class ChatUtil {

    private static final String successPrefix = "&a&l[SUCCESSFUL] ";
    private static final String failPrefix = "&c&l[FAILED] ";
    private static final String errorPrefix = "&4&l[ERROR] ";

    public static void sendLegacyMessage(Player player, MessageType type, String msg) {
        switch (type) {
            case SUCCESSFUL -> player.sendMessage(TextUtil.translateLegacy(successPrefix +"&r&a"+ msg));
            case FAILED -> player.sendMessage(TextUtil.translateLegacy(failPrefix +"&r&b"+ msg));
            case ERROR -> player.sendMessage(TextUtil.translateLegacy(errorPrefix +"&r&4"+ msg));
        }
    }

}
