package smtm.rollbackresurrection.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import smtm.rollbackresurrection.RollbackResurrection;
import smtm.rollbackresurrection.controllers.RollbackController;

public class BackupCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        RollbackController.backup(RollbackResurrection.getPlugin(RollbackResurrection.class), sender);
        sender.sendMessage("O backup foi programado com sucesso! Aguarde alguns segundos para que ele seja concluído.");
        return true;
    }
}
