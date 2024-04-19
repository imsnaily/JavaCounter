package snaily.plugins.javacounter.counter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Manager {
    private final Map<String, User> counters;

    public Manager() {
        counters = new HashMap<>();
    }

    public Boolean doesCounterExist(UUID uuid) {
        return counters.containsKey(uuid.toString());
    }

    public void addCounter(UUID uuid) {
        this.counters.put(uuid.toString(), new User(uuid));
    }

    public void removeCounter(UUID uuid) {
        this.counters.remove(uuid.toString());
    }

    public User getCounter(UUID uuid) {
        return this.counters.get(uuid.toString());
    }
}
