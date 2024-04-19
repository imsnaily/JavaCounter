package snaily.plugins.javacounter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import snaily.plugins.javacounter.counter.Manager;

import java.util.UUID;
import java.util.HashMap;
import java.util.Map;

public final class JavaCounter extends JavaPlugin implements Listener {
    private final Map<Player, Long> lastMove = new HashMap<>();
    private Manager manager;

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.manager = new Manager();

        Bukkit.getScheduler().runTaskTimer(this, this::checkInactivity, 5L, 5L);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        getLogger().info("Player " + player.getName() + " joined.");

        if (!manager.doesCounterExist(uuid)) {
            manager.addCounter(uuid);
            getLogger().info("Counter " + uuid + " added.");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        getLogger().info("Player " + player.getName() + " quitted.");

        if (manager.doesCounterExist(uuid)) {
            manager.removeCounter(uuid);
            getLogger().info("Counter " + uuid + " removed.");
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        lastMove.put(player, System.currentTimeMillis());
        getLogger().info("Player " + player.getName() + " moved.");

        if (manager.doesCounterExist(uuid)) {
            manager.getCounter(uuid).increase();
        }
    }

    private void checkInactivity() {
        long currTime = System.currentTimeMillis();
        long inactiveThreshold = 1000; /* 1 second */

        for (Player player : this.getServer().getOnlinePlayers()) {
            long lastTime = lastMove.getOrDefault(player, 0L);
            long inactiveTime = currTime - lastTime;

            if (inactiveTime >= inactiveThreshold) {
                manager.getCounter(player.getUniqueId()).decrease();
            }
        }
    }
}
