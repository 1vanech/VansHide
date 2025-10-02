package me.yourname.vanshide;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class InteractionBlocker implements Listener {

    private static boolean hasBypass(Player player) {
        return player != null && player.hasPermission(VansHide.BYPASS_PERMISSION);
    }

    private static boolean allowBetween(Player actor, Player target) {
        // Only allow if BOTH have bypass
        return hasBypass(actor) && hasBypass(target);
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity victim = event.getEntity();
        if (!(victim instanceof Player)) {
            return;
        }

        Player targetPlayer = (Player) victim;
        Player attackerPlayer = null;

        Entity damager = event.getDamager();
        if (damager instanceof Player) {
            attackerPlayer = (Player) damager;
        } else if (damager instanceof Projectile) {
            Projectile projectile = (Projectile) damager;
            if (projectile.getShooter() instanceof Player) {
                attackerPlayer = (Player) projectile.getShooter();
            }
        }

        if (attackerPlayer == null) {
            return;
        }

        if (!allowBetween(attackerPlayer, targetPlayer)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Entity clicked = event.getRightClicked();
        if (!(clicked instanceof Player)) {
            return;
        }
        Player actor = event.getPlayer();
        Player target = (Player) clicked;
        if (!allowBetween(actor, target)) {
            event.setCancelled(true);
        }
    }
}


