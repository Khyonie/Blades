package com.yukiemeralis.blogspot.blades;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings({"unused", "serial"})
public class PlayerAccount 
{
    private Player owner;

    // Player stats
    private int base_max_health = 20;
    private int max_health = 20;
    private double current_health = max_health;

    // RPG stats
    private double damage_modifier = 1.0;
    private double base_damage_modifier = 1.0;

    private double healing_modifier;
    private double base_healing_modifier = 1.0;

    private double defense_modifier = 1.0;
    private double base_defense_modifier = 1.0;

    // Other random stats
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

    // Level stats
    private int level = 1;
    private long exp = 0;
    private long exp_to_next;

    private Party party;

    // Combat data
    private int bladeAffinity = 0;
    private int bladeSpecialProgress = 0;

    /**
     * An account for a player, controls most aspects of driver combat.
     * @param player The player that owns this account
     */
    public PlayerAccount(Player player)
    {
        this.owner = player;
    }

    public Player getOwner()
    {
        return this.owner;
    }

    //
    // Virtual health
    //

    /**
     * Get this account's current virtual HP
     * @return Current virtual HP
     */
    public double getVirtualHP()
    {
        return current_health;
    }

    /**
     * Get this account's maximum virtual HP
     * @return Maximum virtual HP
     */
    public int getMaxVirtualHP()
    {
        return this.max_health;
    }

    /**
     * Sets this account's virtual HP. Will not go over maximum health.
     * @param value The value to set
     */
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

    /**
     * Adds the value to the player's virtual HP. Will not go over maximum health.
     * @param value The delta to add
     */
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

    /**
     * Removes virtual health from the player, harming them.
     * @param damage The damage to deal
     */
    public void dealVirtualHPDamage(double damage)
    {
        current_health -= damage;

        if (current_health <= 0.0)
        {
            owner.setHealth(0.0);
        } else {
            double display_hp = (current_health/max_health)*20.0;
            owner.setHealth(display_hp);
        }
    }

    /**
     * Sets this account's maximum virtual HP. Note that this doesn't change their current HP.
     * @param max The value to set
     */
    public void setMaxVirtualHP(int max)
    {
        this.max_health = max;
    }

    public int getBaseMaxHealth()
    {
        return this.base_max_health;
    }

    public void setBaseMaxHealth(int value)
    {
        this.base_max_health = value;
    }

    //
    // Player stats
    //

    /**
     * Check if a player is a driver or a blade.
     * @return True if the player is a driver, false if not.
     */
    public boolean isDriver()
    {
        return !this.isPlayerBlade;
    }

    /**
     * Get the account's level.
     * @return The account's level
     */
    public int getLevel()
    {
        return this.level;
    }

    /**
     * Add experience to the account.
     * @param value The EXP to add.
     */
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

    public long getExpToNext()
    {
        return this.exp_to_next - this.exp;
    }

    // Stat getters
    public double getStatAttack()
    {
        return this.damage_modifier;
    }

    public double getBaseStatAttack()
    {
        return this.base_damage_modifier;
    }

    public double getStatDefense()
    {
        return this.damage_modifier;
    }

    public double getBaseStatDefense()
    {
        return this.base_damage_modifier;
    }

    public double getStatHealing()
    {
        return this.damage_modifier;
    }

    public double getBaseStatHealing()
    {
        return this.base_damage_modifier;
    }

    // Stat setters
    public void setStatAttack(double value)
    {
        this.damage_modifier = value;
    }

    public void setStatDefense(double value)
    {
        this.defense_modifier = value;
    }

    public void setStatHealing(double value)
    {
        this.healing_modifier = value;
    }

    public void setBaseStatAttack(double value)
    {
        this.base_damage_modifier = value;
    }

    public void setBaseStatDefense(double value)
    {
        this.base_defense_modifier = value;
    }

    public void setBaseStatHealing(double value)
    {
        this.base_healing_modifier = value;
    }


    /**
     * Sets if the player should be marked as dead, so we don't duplicate blade weapons.
     * @param state True if the player is supposed to be dead, false if not
     */
    public void setIsDead(boolean state)
    {
        this.isDead = state;
    }

    /**
     * Get if the player is supposed to be dead.
     * @return The player's status as a citizen of Brazil
     */
    public boolean getIsDead()
    {
        return this.isDead;
    }

    //
    // Party
    //

    /**
     * Get the party the player is in. Is null if the player isn't in a party.
     * @return The party the player is in.
     */
    public Party getParty()
    {
        return this.party;
    }

    /**
     * Sets the player's party.
     * NOTE This method is not intended for use, to join a party use {@link Party#join(Player)}.
     * @param p The party to set
     */
    public void setParty(Party p)
    {
        this.party = p;
    }

    //
    // Blade
    //

    /**
     * Get this account's equipped blade. Returns null if no blade is equipped.
     * @return The player's blade
     */
    public Blade getCurrentBlade()
    {
        return current_blade;
    }

    /**
     * Get the player's blade special progress between 0 and 400.
     * @return Player's blade special progress
     */
    public int getBladeSpecialProgress()
    {
        return bladeSpecialProgress;
    }

    /**
     * Adds blade special to the bar.
     * @param delta The amount to add
     */
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

    /**
     * Sets the player's blade special progress.
     * @param value The value to set
     */
    public void setBladeSpecialProgress(int value)
    {
        this.bladeSpecialProgress = value;
    }

    /**
     * Add's a blade to a player's stock.
     * @param blade The blade to add
     */
    public void resonateBlade(Blade blade)
    {
        blade_stock.add(blade);

        blade.setDriver(owner);
        blade.getAffinityChart().setOwner(blade);
    }

    /**
     * Returns a list of engaged blades from 1-3. 1 (Index 0) is equipped.
     * @return Engaged blades
     */
    public ArrayList<Blade> getEngagedBlades()
    {
        return engaged_blades;
    }

    /**
     * Gets a list of the blades in the player's stock.
     * @return Stocked blades
     */
    public ArrayList<Blade> getBladeStock()
    {
        return blade_stock;
    }

    /**
     * Sets the player's equipped blade. The engage GUI handles shifting blades around.
     * @param blade The blade to set
     */
    public void setEquippedBlade(Blade blade)
    {
        try {
            current_blade.despawn();
            BladeData.blade_entities.remove(current_blade.getAvatar()); // Cleanup
        } catch (NullPointerException error) {}
        
        BladeData.removeBladeWeapon(owner.getInventory());
        BladeData.addBladeWeapon(blade);

        this.current_blade = blade;

        current_blade.setMinecraftProperties();
        current_blade.spawn();

        if (current_blade.isHumanoid())
        {
            current_blade.linkNPC();
        }   
        
        current_blade.getAvatar().link(current_blade); // Link the avatar to the blade
        BladeData.blade_entities.add(current_blade.getAvatar()); // Mark the entity for cleanup
    }

    //
    // Player blade methods
    //

    /**
     * Sets the player's blade account to the value.
     * @param blade_account The blade account to use
     */
    public void setPlayerBladeAccount(PlayerBladeAccount blade_account)
    {
        this.blade_account = blade_account;
    }

    /**
     * Sets the player as a blade.
     * Blades are invincible and cannot deal damage normally.
     * Driver inventory is kept seperate from the blade inventory.
     */
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

        // Clean null entries
        blade_stock.removeIf(blade_tmp -> blade_tmp == null);

        // Remove duplicates
        blade_stock.removeIf(blade_tmp -> {
            return Collections.frequency(blade_stock, blade_tmp) > 1;
        });
    }

    /**
     * Sets the player as a driver.
     * Returns the inventory that was saved in blade form.
     */
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

    /**
     * Get this account's blade account.
     * @return Player blade account
     */
    public PlayerBladeAccount getPlayerBladeAccount() 
    {
        return this.blade_account;
    }

    //
    // In-battle methods
    //

    /**
     * Get the driver-blade affinity in battle. Ranges from 0-2.
     * @return In-battle affinity
     */
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

    /**
     * Resets driver-blade affinity.
     */
    public void resetAffinity()
    {
        bladeAffinity = 0;
    }

    /**
     * Marks the driver as using a special or not.
     * @param state Special state
     */
    public void setSpecialState(boolean state)
    {
        this.isUsingSpecial = state;
    }

    /**
     * Get the player's special state.
     * @return Special state
     */
    public boolean getSpecialState()
    {
        return this.isUsingSpecial;
    }

    /**
     * Get a percentage of the player's max HP.
     * @param value The percent to check by (decimal, not integer, I.E 0.03, not 3%)
     * @return The percent of the player's max HP
     */
    public double getPercentHp(double value)
    {
        return this.max_health * value;
    }
}
