package de.geolykt.enchantments_plus.enchantments;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import de.geolykt.enchantments_plus.CustomEnchantment;
import de.geolykt.enchantments_plus.compatibility.CompatibilityAdapter;
import de.geolykt.enchantments_plus.enums.BaseEnchantments;
import de.geolykt.enchantments_plus.enums.Hand;
import de.geolykt.enchantments_plus.util.Tool;

import static org.bukkit.Material.*;

import java.util.concurrent.ThreadLocalRandom;

public class Variety extends CustomEnchantment {

    public static final int ID = 65;

    @Override
    public Builder<Variety> defaults() {
        return new Builder<>(Variety::new, ID)
            .all(BaseEnchantments.VARIETY,
                    "Drops random types of wood or leaves",
                    new Tool[]{Tool.AXE},
                    "Variety",
                    1,
                    Hand.LEFT,
                    Fire.class);
    }

    @Override
    public boolean onBlockBreak(BlockBreakEvent evt, int level, boolean usedHand) {
        Material mat = evt.getBlock().getType();
        if (Tag.LOGS.isTagged(mat)) {
            evt.getBlock().setType(AIR);
            evt.getBlock().getWorld()
               .dropItemNaturally(evt.getBlock().getLocation(),
                   new ItemStack(Tag.LOGS.getValues().toArray(new Material[0])[ThreadLocalRandom.current().nextInt(Tag.LOGS.getValues().size())]));
            CompatibilityAdapter.damageTool(evt.getPlayer(), 1, usedHand);
        } else if (Tag.LEAVES.isTagged(mat)) {
            evt.getBlock().setType(AIR);
            evt.getBlock().getWorld()
               .dropItemNaturally(evt.getBlock().getLocation(),
                   new ItemStack(Tag.LEAVES.getValues().toArray(new Material[0])[ThreadLocalRandom.current().nextInt(Tag.LEAVES.getValues().size())]));
            CompatibilityAdapter.damageTool(evt.getPlayer(), 1, usedHand);
        }
        return true;
    }
}
