package axedamage.me.black.Me;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageImpl implements CommandExecutor, Listener {
    private final black plugin;

    public DamageImpl(black plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        int damagestate = plugin.getConfig().getInt("toggle");
        if (sender instanceof ConsoleCommandSender) {
            if (damagestate == 1) {
                plugin.getLogger().info("Turned axe damage reduction off!");
                plugin.getConfig().set("toggle", 0);
            } else if (damagestate == 0) {
                plugin.getLogger().info("Turned axe damage reduction on!");
                plugin.getConfig().set("toggle", 1);
            } else {
                plugin.getLogger().info("Did nothing because the value toggle in the config was not 1 or 0!");
            }
        } else if (sender instanceof Player && sender.hasPermission("axedamagereduction.toggle")) {
            Player p = (Player) sender;
            if (damagestate == 1) {
                p.sendMessage(ChatColor.RED + "Turned axe damage reduction off!");
                plugin.getConfig().set("toggle", 0);
            } else if (damagestate == 0) {
                p.sendMessage(ChatColor.GREEN + "Turned axe damage reduction on!");
                plugin.getConfig().set("toggle", 1);
            } else {
                p.sendMessage(ChatColor.YELLOW + "Did nothing because the value toggle in the config was not 1 or 0!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
        }
        return true;
    }
    @EventHandler
    public void onEntityDamageEntityEvent (EntityDamageByEntityEvent e) {
        if (plugin.getConfig().getInt("toggle") == 1) {
            if (e.getDamager() instanceof Player) {
                Player p = (Player) e.getDamager();
                switch (p.getInventory().getItemInMainHand().getType()) {
                    case WOODEN_AXE:
                        e.setDamage(DamageReducer(1, e.getDamage()));
                        break;
                    case STONE_AXE:
                        e.setDamage(DamageReducer(2, e.getDamage()));
                        break;
                    case IRON_AXE:
                        e.setDamage(DamageReducer(3, e.getDamage()));
                        break;
                    case GOLDEN_AXE:
                        e.setDamage(DamageReducer(4, e.getDamage()));
                        break;
                    case DIAMOND_AXE:
                        e.setDamage(DamageReducer(5, e.getDamage()));
                        break;
                    case NETHERITE_AXE:
                        e.setDamage(DamageReducer(6, e.getDamage()));
                        break;
                }
            }
        }
    }
    public Double DamageReducer(int i, double damage) {
        double PercentageDecrease;
        switch (i) {
            case 1:
                PercentageDecrease = plugin.getConfig().getDouble("Axes.woodenaxe");
                break;
            case 2:
                PercentageDecrease = plugin.getConfig().getDouble("Axes.stoneaxe");
                break;
            case 3:
                PercentageDecrease = plugin.getConfig().getDouble("Axes.ironaxe");
                break;
            case 4:
                PercentageDecrease = plugin.getConfig().getDouble("Axes.goldaxe");
                break;
            case 5:
                PercentageDecrease = plugin.getConfig().getDouble("Axes.diamondaxe");
                break;
            case 6:
                PercentageDecrease = plugin.getConfig().getDouble("Axes.netheriteaxe");
                break;
            default:
                PercentageDecrease = 0;
        }
        double NewDamageReduction = PercentageDecrease * damage / 100;
        return damage - NewDamageReduction;
    }
}
