package ru.turbovadim.pearltransport

import com.comphenix.protocol.ProtocolLibrary
import org.bukkit.plugin.java.JavaPlugin

class Plugin : JavaPlugin() {
    override fun onEnable() {
        server.pluginManager.registerEvents(EnderPearlListener(), this)
        ProtocolLibrary.getProtocolManager().addPacketListener(DismountPacketAdapter(this))
        logger.info("EnderPearlRide plugin enabled!")
    }

    override fun onDisable() {
        ProtocolLibrary.getProtocolManager().removePacketListeners(this)
        logger.info("EnderPearlRide plugin disabled.")
    }
}