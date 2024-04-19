package snaily.plugins.javacounter.counter;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class User {
    private int count;
    private UUID UUID;
    private final Player player;

    public User(UUID uniqueId) {
        int count = 0;
        this.player = Bukkit.getPlayer(uniqueId);
    }

    public void increase() {
        assert this.player != null;

        this.count++;
        this.player.getInventory().addItem(new ItemStack(Material.DIAMOND));
    }

    public void decrease() {
        assert this.player != null;

        this.count--;
        this.player.getInventory().remove(Material.DIAMOND);
    }

    public int get() {
        return this.count;
    }
}
