package smtm.rollbackresurrection.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SudoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2)
            return false;

        Player jogador = Bukkit.getPlayer(args[0]);
        if (jogador == null) {
            sender.sendMessage("Jogador não encontrado!");
            return false;
        }
        StringBuilder comando = new StringBuilder(args[1]);

        for (int i = 2; i < args.length; i++)
            comando.append(" ").append(args[i]);

        jogador.performCommand(comando.toString());
        sender.sendMessage("Comando executado com sucesso!");
        return true;
    }
}
