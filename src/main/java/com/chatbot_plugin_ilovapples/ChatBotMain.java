package com.chatbot_plugin_ilovapples;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ChatBotMain extends JavaPlugin {
    @Override
    public void onEnable() {
        Logger chat_bot_logger = Bukkit.getLogger();
        // get messages
        String[] messages = new String[15];
        try {
            File messages_file = new File("chat_bot_messages.txt");
            Scanner myReader = new Scanner(messages_file);
            @SuppressWarnings("ReassignedVariable") int msg_list_index = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                messages[msg_list_index] = data;
                msg_list_index++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            chat_bot_logger.log(Level.INFO, "The 'chat_bot_messages.txt' file was not found.");
            e.printStackTrace();
        }

        final long seconds = 20L;
        final long minutes = 60L*seconds;
        final long hours = 60L*minutes;

        Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
            public void run() {
                Random rand = new Random();
                int index = rand.nextInt(15);
                String message = messages[index];
                for (Player player_to_send_message_to : Bukkit.getOnlinePlayers()) {
                    if (player_to_send_message_to != null) {
                        chat_bot_logger.log(Level.INFO, "Message sent: '" + message + "'");
                        player_to_send_message_to.sendMessage(message);
                    }
                }
            }
        }, 0l, 5*minutes);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
