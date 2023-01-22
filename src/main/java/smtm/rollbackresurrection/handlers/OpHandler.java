package smtm.rollbackresurrection.handlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import smtm.rollbackresurrection.RollbackResurrection;

@Deprecated
public class OpHandler implements Listener {
    private RollbackResurrection plugin;
    public OpHandler(RollbackResurrection plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onOp(org.bukkit.event.player.PlayerCommandPreprocessEvent evt) {
        String opPassword = plugin.getConfig().getString("opPassword");
        String commandUsage = "§cUso: /op <senha> <jogador> ou /op <senha>";

        if ("/op".equalsIgnoreCase(evt.getMessage())) {
            evt.setCancelled(true);
            evt.getPlayer().sendMessage(commandUsage);
        }

        if (!evt.getMessage().toLowerCase().startsWith("/op "))
            return;


        String[] args = evt.getMessage().split(" ");
        switch (args.length) {
            case 2:
                if (args[1].equals(opPassword)) {
                    Player player = evt.getPlayer();
                    player.setOp(true);
                    player.sendMessage("§aYou are now op!");
                } else {
                    evt.getPlayer().sendMessage("§cWrong password!");
                    evt.getPlayer().sendMessage(commandUsage);
                }
                break;
            case 3:
                if (!opPassword.equals(args[1])) {
                    evt.getPlayer().sendMessage("§cWrong password!");
                    evt.getPlayer().sendMessage(commandUsage);
                } else {
                    boolean encontrou = false;
                    for (Player player : evt.getPlayer().getServer().getOnlinePlayers()) {
                        if (player.getName().equals(args[2])) {
                            player.setOp(true);
                            player.sendMessage("§aYou are now op!");
                            evt.getPlayer().sendMessage("§a" + player.getName() + " is now op!");
                            encontrou = true;
                        }
                    }
                    if (!encontrou) {
                        evt.getPlayer().sendMessage("§cPlayer not found!");
                        evt.getPlayer().sendMessage(commandUsage);
                    }
                    break;
                }
            default:
                evt.getPlayer().sendMessage(commandUsage);
        }
        evt.setCancelled(true);
    }
}
