package com.yukiemeralis.blogspot.blades.affinity;

import java.util.Arrays;

import com.yukiemeralis.blogspot.blades.Blade;
import com.yukiemeralis.blogspot.blades.guis.GUIUtils;
import com.yukiemeralis.blogspot.blades.affinity.skills.*;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AffinityUtils 
{
    public static Inventory toInventory(AffinityChart chart)
    {
        Inventory inv = Bukkit.createInventory(null, 54, "Affinity chart");
        GUIUtils.paintBlack(inv);

        // Blade data
        inv.setItem(0, chart.getOwner().getIcon());
        inv.setItem(9, trustToItem(chart.getOwner()));

        fillRowData(chart, inv);
        fillDataRow(inv);
        fillAffinitySkills(chart, inv);

        return inv;
    }

    // Vertical strip showing what rows are unlocked
    private static void fillRowData(AffinityChart chart, Inventory inv)
    {
        ItemStack buffer;
        Material buffer_mat;
        ItemMeta buffer_meta;

        // Column 2 (index 1)
        for (int i = 0; i < 5; i++)
        {
            // TODO Load item from row data instead of generating it each time
            if (chart.getRow(i).isUnlocked())
            {
                buffer_mat = Material.LIGHT_BLUE_STAINED_GLASS_PANE;
            } else {
                buffer_mat = Material.RED_STAINED_GLASS_PANE;
            }

            // Prepare the itemstack
            buffer = new ItemStack(buffer_mat);
            buffer_meta = buffer.getItemMeta();

            buffer_meta.setDisplayName("§r§eTrust: " + trustToGrade(i));

            buffer.setItemMeta(buffer_meta);

            // Slot the itemstack in
            inv.setItem(((i + 1) * 9) + 1, buffer);
        }
    }

    private static final ItemStack field_header = new ItemStack(Material.WHITE_CONCRETE);
    private static final ItemStack battle_header = new ItemStack(Material.WHITE_CONCRETE);
    private static final ItemStack special_header = new ItemStack(Material.WHITE_CONCRETE);

    static {
        defineHeaderItem(field_header, "§a§lField skills");
        defineHeaderItem(battle_header, "§a§lBattle skills");
        defineHeaderItem(special_header, "§a§lSpecial skills");
    }

    // Horizontal strip detailing skills
    private static void fillDataRow(Inventory inv)
    {
        inv.setItem(2, field_header);

        inv.setItem(3, battle_header);
        inv.setItem(4, battle_header);
        inv.setItem(5, battle_header);

        inv.setItem(6, special_header);
        inv.setItem(7, special_header);
        inv.setItem(8, special_header);
    }

    // List affinity skills
    private static void fillAffinitySkills(AffinityChart chart, Inventory inv)
    {
        for (int i = 0; i < 5; i++)
            fillAffinityRow(chart.getRow(i), inv, i);
    }

    public static String trustToGrade(int trust)
    {
        String buffer = "N/A";

        switch (trust)
        {
            case 0: buffer = "F"; break;
            case 1: buffer = "D"; break;
            case 2: buffer = "C"; break;
            case 3: buffer = "B"; break;
            case 4: buffer = "A"; break;
        }

        if (trust > 4)
            buffer = "S";

        return buffer;
    }

    private static ItemStack trustToItem(Blade blade)
    {
        ItemStack buffer = new ItemStack(Material.BLAZE_POWDER);

        // Grade is equal to trust / (trust multiplier*100)
        String grade = trustToGrade(blade.getTrust()/(blade.getTrustMultiplier()*100));

        ItemMeta meta = buffer.getItemMeta();
        meta.setDisplayName("§eBlade trust: " + blade.getTrust());
        meta.setLore(Arrays.asList(new String[] {"§6Trust rating: " + grade}));
        buffer.setItemMeta(meta);

        return buffer;
    }

    private static void defineHeaderItem(ItemStack in, String name)
    {
        ItemMeta meta = in.getItemMeta();

        meta.setDisplayName(name);

        in.setItemMeta(meta);
    }

    private static void fillAffinityRow(AffinityRow data, Inventory inv, int row)
    {
        ItemStack buffer;
        ItemMeta buffer_meta;

        for (int i = 0; i < 7; i++)
        {
            try {   
                buffer = data.get(i).getIcon();

                if (data.isUnlocked())
                {
                    buffer.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
                    buffer_meta = buffer.getItemMeta();
                    buffer_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

                    buffer.setItemMeta(buffer_meta);
                }

                inv.setItem(((row + 1) * 9) + (2 + i), buffer);
            } catch (NullPointerException error) {
            } catch (IllegalArgumentException error) {}
        }
    }


    public static AffinityChart createAffinityChart(Blade blade, AffinityRow... input)
    {
        AffinityChart chart = new AffinityChart(blade, input);

        return chart;
    }

    public static AffinityRow createAffinityRow(AffinitySkill... skills)
    {
        return new AffinityRow(skills);
    }


    /**
     * Sample affinity charts
     */

     public static AffinityChart multi_color = createAffinityChart(null, 
        AffinityUtils.createAffinityRow(new Skill_SwiftStep(1), new Skill_BlazingSpirit(1), new Skill_BurningEdge(1), new Skill_AffinityBoost(1), null, null, null),
        AffinityUtils.createAffinityRow(null                  , new Skill_BlazingSpirit(2), new Skill_BurningEdge(2), null,                       null, null, null),
        AffinityUtils.createAffinityRow(new Skill_SwiftStep(2), new Skill_BlazingSpirit(3), new Skill_BurningEdge(3), new Skill_AffinityBoost(2), null, null, null),
        AffinityUtils.createAffinityRow(null                  , new Skill_BlazingSpirit(4), new Skill_BurningEdge(4), null,                       null, null, null),
        AffinityUtils.createAffinityRow(new Skill_SwiftStep(3), new Skill_BlazingSpirit(5), new Skill_BurningEdge(5), new Skill_AffinityBoost(3), null, null, null)
    );

    public static AffinityChart blank_chart = createAffinityChart(null,
        createAffinityRow(null, null, null, null, null, null, null),
        createAffinityRow(null, null, null, null, null, null, null),
        createAffinityRow(null, null, null, null, null, null, null),
        createAffinityRow(null, null, null, null, null, null, null),
        createAffinityRow(null, null, null, null, null, null, null)
    );
}
