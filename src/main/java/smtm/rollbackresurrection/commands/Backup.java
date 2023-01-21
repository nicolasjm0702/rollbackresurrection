package smtm.rollbackresurrection.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import smtm.rollbackresurrection.Rollbackresurrection;
import smtm.rollbackresurrection.controllers.RollbackController;

public class Backup implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        RollbackController.backup(Rollbackresurrection.getPlugin(Rollbackresurrection.class), false, sender);
        sender.sendMessage("O backup foi programado com sucesso! Aguarde alguns segundos para que ele seja concluído.");
        return true;
    }
}
