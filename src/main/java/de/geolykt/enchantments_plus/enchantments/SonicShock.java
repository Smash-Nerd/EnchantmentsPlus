package de.geolykt.enchantments_plus.enchantments;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import de.geolykt.enchantments_plus.CustomEnchantment;
import de.geolykt.enchantments_plus.enums.BaseEnchantments;
import de.geolykt.enchantments_plus.enums.Hand;
import de.geolykt.enchantments_plus.util.AreaOfEffectable;
import de.geolykt.enchantments_plus.util.Tool;

public class SonicShock extends CustomEnchantment implements AreaOfEffectable {

    public static final int ID = 75;

    @Override
    public Builder<SonicShock> defaults() {
        return new Builder<>(SonicShock::new, ID)
            .all(BaseEnchantments.SONIC_SHOCK,
                    "Damages mobs when flying past at high speed",
                    new Tool[]{Tool.WINGS},
                    "Sonic Shock",
                    3,
                    Hand.NONE);
    }

    @Override
    public boolean onFastScan(Player player, int level, boolean usedHand) {
        if (player.isGliding() && player.getVelocity().length() >= 1) {
            double radius = getAOESize(level);
            for (Entity e : player.getNearbyEntities(radius, radius, radius)) {
                double damage = player.getVelocity().length() * 1.5 * level * power;
                if (e instanceof Monster) {
                    ADAPTER.attackEntity((LivingEntity) e, player,  damage, false);
                }
            }
        }
        return true;
    }

    /**
     * The Area of effect multiplier used by this enchantment.
     * @since 2.1.6
     * @see AreaOfEffectable
     */
    private double aoe = 1.0;
    
    @Override
    public double getAOESize(int level) {
        return 2 + aoe + level * 2;
    }

    @Override
    public double getAOEMultiplier() {
        return aoe;
    }

    /**
     * Sets the multiplier used for the area of effect size calculation, the multiplier should have in most cases a linear impact,
     * however it's not guaranteed that the AOE Size is linear to the multiplier as some other effects may play a role.<br>
     * <br>
     * Impact formula: <b>2 + AOE + level*2</b>
     * @param newValue The new value of the multiplier
     * @since 2.1.6
     */
    @Override
    public void setAOEMultiplier(double newValue) {
        aoe = newValue;
    }

}
