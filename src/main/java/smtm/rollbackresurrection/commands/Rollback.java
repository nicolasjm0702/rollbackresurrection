package smtm.rollbackresurrection.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import smtm.rollbackresurrection.Rollbackresurrection;
import smtm.rollbackresurrection.controllers.RollbackController;

public class Rollback implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("rollbackresurrection.rollback")) {
            try {
                RollbackController.rollback(Rollbackresurrection.getPlugin(Rollbackresurrection.class));
            } catch (Exception e) {
                sender.sendMessage(e.getMessage());
            }
            sender.sendMessage("Rollback realizado com sucesso!");
        } else {
            sender.sendMessage("Você não tem permissão para executar este comando!");
        }
        return true;
    }
}
