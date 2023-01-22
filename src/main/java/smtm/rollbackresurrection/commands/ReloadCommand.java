package smtm.rollbackresurrection.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import smtm.rollbackresurrection.RollbackResurrection;
import smtm.rollbackresurrection.controllers.ConfigController;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ConfigController.recarregarConfiguracoes(RollbackResurrection.getPlugin(RollbackResurrection.class));
        sender.sendMessage("Rollbackresurrection reloaded!");
        return true;
    }
}
