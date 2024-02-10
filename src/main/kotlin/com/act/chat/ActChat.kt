package com.act.chat

import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class ActChat: JavaPlugin(), Listener {
    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)

        val chat = getCommand("chat")!!
        val cmd = ChatCommand()

        chat.setExecutor(cmd)
        chat.tabCompleter = cmd
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onAsyncChat(event: AsyncChatEvent) {
        val player = event.player
        val enabled = ChatManager.enabled

        if (!enabled && !player.hasPermission("act.admin")) {
            player.sendMessage("<red><bold>채팅은 현재 금지되어 있습니다".mini)
            event.isCancelled = true
        }
    }

    private val String.mini: Component
        get() = MiniMessage.miniMessage().deserialize(this)
}