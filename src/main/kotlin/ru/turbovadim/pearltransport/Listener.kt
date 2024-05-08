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
            // Check if the player is pressing the dismount key (sneak)
            event.isCancelled = true // Cancel the dismount packet
        }
    }
}

class ThrowEnderPearl2(plugin: Plugin) : PacketAdapter(
    plugin,
    ListenerPriority.HIGH, // Use a higher priority to ensure this runs before the packet is processed
    PacketType.Play.Client.USE_ITEM
) {
    override fun onPacketReceiving(event: PacketEvent) {
        val player = event.player
        // Check if the player is riding an ender pearl
        if (player.vehicle?.type == EntityType.ENDER_PEARL) {
            // Cancel the packet to prevent the player from throwing an ender pearl while riding one
            event.isCancelled = true
        }
    }
}