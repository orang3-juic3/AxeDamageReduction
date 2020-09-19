package axedamage.me.black.Me;

import org.bukkit.plugin.java.JavaPlugin;

public final class black extends JavaPlugin {

    @Override
    public void onEnable() {
        DamageImpl damageImpl = new DamageImpl(this);
        this.getCommand("axedamagetoggle").setExecutor(damageImpl);
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(damageImpl, this);
    }

    @Override
    public void onDisable() {
    }
}
