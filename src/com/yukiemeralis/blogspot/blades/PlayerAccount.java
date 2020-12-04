package com.yukiemeralis.blogspot.blades;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings({"unused", "serial"})
public class PlayerAccount 
{
    private Player owner;

    // Player stats
    private int max_health = 20;
    private double current_health = max_health;

    private boolean isDead = false;

    private Blade current_blade;

    private ArrayList<Blade> blade_stock = new ArrayList<>();

    // Initialize engaged blades list so we don't cause trouble
    private ArrayList<Blade> engaged_blades = new ArrayList<>() {{
        for (int i = 0; i < 3; i++) 
            add(null);
    }};

    private PlayerBladeAccount blade_account = null;
    private boolean isPlayerBlade = false;
    private boolean isUsingSpecial = false;

    // RPG stats
    private int level = 1;
    private long exp = 0;
    private long exp_to_next;

    private Party party;

    // Combat data
    private int bladeAffinity = 0;
    private int bladeSpecialProgress = 0;

    public PlayerAccount(Player player)
    {
        this.owner = player;
    }

    //
    // Virtual health
    //

    public double getVirtualHP()
    {
        return current_health;
    }

    public int getMaxVirtualHP()
    {
        return this.max_health;
    }

    public void setVirtualHP(double value)
    {
        if (value > max_health) {
            current_health = max_health;
        } else {
            current_health = value;
        }

        double display_hp = (current_health/max_health)*20.0;
        owner.setHealth(display_hp);
    }

    public void healVirtualHP(double value)
    {
        if (current_health + value > max_health)
        {
            current_health = max_health;
        } else {
            current_health += value;
        }

        double display_hp = (current_health/max_health)*20.0;
        owner.setHealth(display_hp);
    }

    public void dealVirtualHPDamage(double damage)
    {
        current_health -= damage;

        if (current_health < 0.0)
        {
            owner.setHealth(0.0);
        } else {
            double display_hp = (current_health/max_health)*20.0;
            owner.setHealth(display_hp);
        }
    }

    public void setMaxVirtualHP(int max)
    {
        this.max_health = max;
    }

    //
    // Player stats
    //

    public boolean isDriver()
    {
        return !this.isPlayerBlade;
    }

    public int getLevel()
    {
        return this.level;
    }

    public void addExperience(int value)
    {
        this.exp += value;

        if (exp >= exp_to_next)
            while (exp >= exp_to_next)
            {
                handleLevelUp();
            }
    }

    private void handleLevelUp()
    {
        owner.sendMessage("§bLeveled up! [§a" + level + " §b-> §a" + (level + 1) + "§b]");
        level++;

        long exp_buffer = exp_to_next - exp;

        exp_to_next = (level * level) * 100;
        exp = exp_buffer;
    }

    public void setIsDead(boolean state)
    {
        this.isDead = state;
    }

    public boolean getIsDead()
    {
        return this.isDead;
    }

    //
    // Party
    //

    public Party getParty()
    {
        return this.party;
    }

    public void setParty(Party p)
    {
        this.party = p;
    }

    //
    // Blade
    //

    public Blade getCurrentBlade()
    {
        return current_blade;
    }

    public int getBladeSpecialProgress()
    {
        return bladeSpecialProgress;
    }

    public void addBladeSpecialProgress(int delta)
    {
        // We can assume the driver is in combat, so calculate a delta multiplier based on blade affinity
        double multiplier = 1;

        switch (bladeAffinity)
        {
            case 1: multiplier = 1; break; // Base affinity
            case 2: multiplier = 1.25; break; // Good affinity
            case 3: multiplier = 1.75; break; // Max affinity
        }

        int newDelta = (int) Math.round(delta * multiplier);

        if (newDelta + bladeSpecialProgress > 400)
        {
            bladeSpecialProgress = 400;
        } else {
            bladeSpecialProgress += newDelta;
        }
    }

    public void setBladeSpecialProgress(int value)
    {
        this.bladeSpecialProgress = value;
    }

    public void resonateBlade(Blade blade)
    {
        blade_stock.add(blade);

        blade.setDriver(owner);
        blade.getAffinityChart().setOwner(blade);
    }

    public ArrayList<Blade> getEngagedBlades()
    {
        return engaged_blades;
    }

    public ArrayList<Blade> getBladeStock()
    {
        return blade_stock;
    }

    public void setEquippedBlade(Blade blade)
    {
        BladeData.removeBladeWeapon(owner.getInventory());
        BladeData.addBladeWeapon(blade);

        this.current_blade = blade;
    }

    //
    // Player blade methods
    //
    public void setPlayerBladeAccount(PlayerBladeAccount blade_account)
    {
        this.blade_account = blade_account;
    }

    public void becomeBlade()
    {
        this.isPlayerBlade = true;
        this.owner.setInvulnerable(true);

        blade_account.setInventory(owner.getInventory());

        owner.getInventory().clear();
        BladeData.getAccount(owner).getEngagedBlades().forEach(blade -> {
            BladeData.getAccount(owner).getBladeStock().add(blade);
        });

        setEquippedBlade(null);

        for (int i = 0; i < 3; i++)
        {
            if (engaged_blades.get(i) != null)
            {
                blade_stock.add(engaged_blades.get(i));
                engaged_blades.set(i, null);
            }
        }

        // Clean null entires
        blade_stock.removeIf(blade_tmp -> blade_tmp == null);

        // Remove duplicates
        blade_stock.removeIf(blade_tmp -> {
            return Collections.frequency(blade_stock, blade_tmp) > 1;
        });
    }

    public void becomeDriver()
    {
        this.isPlayerBlade = false;
        this.owner.setInvulnerable(false);

        // Fetch driver inventory
        for (ItemStack i : blade_account.getDriverInventory())
        {
            if (i != null)
                owner.getWorld().dropItem(owner.getLocation(), i);
        }
    }

    public PlayerBladeAccount getPlayerBladeAccount() 
    {
        return this.blade_account;
    }

    //
    // In-battle methods
    //

    public int getAffinity()
    {
        return this.bladeAffinity;
    }

    public void raiseAffinity()
    {
        if (bladeAffinity != 2)
        {
            bladeAffinity++;

            if (bladeAffinity == 2)
            {
                BladeData.getAccount(owner).getCurrentBlade().raiseTrust(2);
            } else {
                BladeData.getAccount(owner).getCurrentBlade().raiseTrust(1);
            }
        }
    }

    public void resetAffinity()
    {
        bladeAffinity = 0;
    }

    public void setSpecialState(boolean state)
    {
        this.isUsingSpecial = state;
    }

    public boolean getSpecialState()
    {
        return this.isUsingSpecial;
    }

    public double getPercentHp(double value)
    {
        return this.max_health * value;
    }
}
