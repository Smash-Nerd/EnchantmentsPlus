package de.geolykt.enchantments_plus.compatibility.enchantmentgetters;

import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import de.geolykt.enchantments_plus.CustomEnchantment;


/**
 * The Enchantment gatherer used by <a href="https://github.com/Geolykt/NMSless-Zenchantments">
 *  Geolykt's NMSless-Enchantments_plus</a>, the implementation uses Persistent Data to store
 *  it's data modified for various reasons.<br>
 *  For performance reasons it's not recommended to use it,
 *  however it may be used when stability is a bigger concern as it doesn't use string manipulation. <br>
 *  Only functional for 1.16+
 *  @see BasicLoreGetter
 *  @see LeightweightPDCGetter
 *  @since 2.0.0
 */
public class PersistentDataGetter extends LeightweightPDCGetter {

    private final EnumSet<Material> getterAllowlist;

    /**
     *  If true the {@link #getterAllowlist} allowlist will be used as a denylist, false if it should be kept a allowlist
     * @since 2.0.0
     */
    private final boolean isGetterDenylist;

    /**
     * Constructor
     * @param allowlist The allowlist that should be used (items not in the allowlist will always return an empty enchantment list)
     * @param denylistToggle If true the allowlist will be used as a denylist, false if it should be kept a allowlist
     * @since 2.0.0
     */
    public PersistentDataGetter(EnumSet<Material> allowlist, boolean denylistToggle) {
        getterAllowlist = allowlist;
        isGetterDenylist = denylistToggle;
    }

    @Override
    public LinkedHashMap<CustomEnchantment, Integer> getEnchants(@Nullable ItemStack stk, boolean acceptBooks, World world,
            List<String> outExtraLore) {
        if (stk != null) {
            if (isGetterDenylist) {
                // if item is not in the allowlist, then return nothing
                if (getterAllowlist.contains(stk.getType())) {
                    return new LinkedHashMap<>();
                }
            } else {
                // if item is in the denylist, then return nothing
                if (!getterAllowlist.contains(stk.getType())) {
                    return new LinkedHashMap<>();
                }
            }
            return super.getEnchants(stk, acceptBooks, world, outExtraLore);
        } else {
            return new LinkedHashMap<>(0);
        }
    }
}
