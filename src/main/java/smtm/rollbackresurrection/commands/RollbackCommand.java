package smtm.rollbackresurrection.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import smtm.rollbackresurrection.RollbackResurrection;
import smtm.rollbackresurrection.controllers.RollbackController;

public class RollbackCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            RollbackController.rollback(RollbackResurrection.getPlugin(RollbackResurrection.class));
        } catch (Exception e) {
            sender.sendMessage(e.getMessage());
            return false;
        }
        sender.sendMessage("Rollback realizado com sucesso!");
        return true;
    }
}
