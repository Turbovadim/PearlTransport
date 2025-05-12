package ru.turbovadim.pearltransport

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.ListenerPriority
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketEvent
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileLaunchEvent
import org.bukkit.plugin.java.JavaPlugin

class EnderPearlListener : Listener {
    private val plugin = JavaPlugin.getPlugin(Plugin::class.java)

    @EventHandler
    fun onProjectileLaunch(event: ProjectileLaunchEvent) {
        val projectile = event.entity
        if (projectile.type == EntityType.ENDER_PEARL && event.entity.shooter is Player) {
            val player = event.entity.shooter as Player
            if (player.vehicle?.type != EntityType.ENDER_PEARL) {
                projectile.setPassenger(player)
            } else {
                event.isCancelled = true
            }
        }
    }
}

class DismountPacketAdapter(plugin: Plugin) : PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.STEER_VEHICLE) {

    override fun onPacketReceiving(event: PacketEvent) {
        val player = event.player
        if (player.vehicle?.type == EntityType.ENDER_PEARL) {
            event.isCancelled = true
        }
    }
}

class ThrowEnderPearl2(plugin: Plugin) : PacketAdapter(
    plugin,
    ListenerPriority.HIGH,
    PacketType.Play.Client.USE_ITEM
) {
    override fun onPacketReceiving(event: PacketEvent) {
        val player = event.player
        if (player.vehicle?.type == EntityType.ENDER_PEARL) {
            event.isCancelled = true
        }
    }
}
