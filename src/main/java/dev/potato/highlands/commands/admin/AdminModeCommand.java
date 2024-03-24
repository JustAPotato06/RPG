package dev.potato.highlands.commands.admin;

import dev.potato.highlands.commands.main.SubCommand;
import dev.potato.highlands.core.admin.AdminState;
import dev.potato.highlands.util.ChatUtil;
import dev.potato.highlands.util.MessageType;
import dev.potato.highlands.util.MessageUtil;
import dev.potato.highlands.util.menu.ItemUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AdminModeCommand extends SubCommand {

    public AdminModeCommand(JavaPlugin plugin) {
        super(plugin);
        setName("mode"); // /admin mode build_mode
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if(args.length != 2) return;
        String mode = args[1];
        try {
            AdminState.State state = AdminState.State.valueOf(mode);
            if(state == AdminState.State.DEFAULT){
                ChatUtil.sendLegacyMessage(player, MessageType.FAILED, "Illegal Mode");
                AdminState.setState(player, null);
                return;
            }

            if(AdminState.getState(player) == state) {
                AdminState.setState(player, null);
                ChatUtil.sendLegacyMessage(player, MessageType.SUCCESSFUL, "Toggled off " + state.humanName);
                return;
            }

            AdminState.setState(player, state);
            ChatUtil.sendLegacyMessage(player, MessageType.SUCCESSFUL, "Toggled on " + state.humanName);
            if(state == AdminState.State.ENTITY_EDITOR_MODE) {
                player.getInventory().addItem(ItemUtil.editorItem);
                for(Component component : MessageUtil.entityManipulationTutorial()) player.sendMessage(component);
            }
        } catch (IllegalArgumentException e) {
            ChatUtil.sendLegacyMessage(player, MessageType.FAILED, "Illegal Mode");
        }

    }

    @Override
    public List<String> onTabComplete(Player player, String[] args) {
        if(args.length != 2) return null;
        return Arrays.stream(AdminState.State.values())
                .filter((state -> state != AdminState.State.DEFAULT))
                .map((Enum::name))
                .filter((name -> args[1] != null && name.startsWith(args[1])))
                .collect(Collectors.toList());
    }
}
