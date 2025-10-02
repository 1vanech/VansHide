package me.yourname.vanshide;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class VisibilityManager {

    private final Plugin plugin;

    public VisibilityManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void updateForAllPlayers() {
        for (Player viewer : Bukkit.getOnlinePlayers()) {
            updateVisibilityFor(viewer);
        }
    }

    public void updateVisibilityFor(Player viewer) {
        boolean viewerBypass = viewer.hasPermission(VansHide.BYPASS_PERMISSION);
        for (Player target : Bukkit.getOnlinePlayers()) {
            if (viewer.equals(target)) {
                continue;
            }
            if (viewerBypass) {
                viewer.showPlayer(plugin, target);
            } else {
                viewer.hidePlayer(plugin, target);
            }
        }
    }
}


