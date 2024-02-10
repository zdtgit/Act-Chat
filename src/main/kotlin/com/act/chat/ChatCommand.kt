package com.act.chat

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor

class ChatCommand: TabExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (!sender.hasPermission("act.admin")) return true

        if (args.isEmpty()) {
            sender.sendUsage()
            return true
        }

        val enabled = ChatManager.enabled

        if (args[0] == "켜기") {
            if (enabled) {
                sender.sendMessage("<blue>채팅<gray>이 이미 <green>활성화<gray>되어있습니다".mini)
            } else {
                ChatManager.enabled = true

                Bukkit.broadcast("<blue>채팅<gray>이 <green>활성화<gray>되었습니다".mini)
            }
        } else if (args[0] == "끄기") {
            if (enabled) {
                ChatManager.enabled = false

                Bukkit.broadcast("<blue>채팅<gray>이 <red>비활성화<gray>되었습니다".mini)
            } else {
                sender.sendMessage("<blue>채팅<gray>이 이미 <red>비활성화<gray>되어있습니다".mini)
            }
        } else {
            sender.sendUsage()
            return true
        }

        return true
    }

    private fun CommandSender.sendUsage() {
        this.sendMessage("<red>사용법: <gray>/채팅 [켜기/끄기]".mini)
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<String>
    ): List<String> {
        if (args.size == 1) {
            return listOf("켜기", "끄기")
        }

        return emptyList()
    }

    private val String.mini: Component
        get() = MiniMessage.miniMessage().deserialize(this)
}