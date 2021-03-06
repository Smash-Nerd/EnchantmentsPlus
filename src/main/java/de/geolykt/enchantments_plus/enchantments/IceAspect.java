package de.geolykt.enchantments_plus.enchantments;

import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import de.geolykt.enchantments_plus.CustomEnchantment;
import de.geolykt.enchantments_plus.compatibility.CompatibilityAdapter;
import de.geolykt.enchantments_plus.enums.BaseEnchantments;
import de.geolykt.enchantments_plus.enums.Hand;
import de.geolykt.enchantments_plus.util.Tool;
import de.geolykt.enchantments_plus.util.Utilities;

import static org.bukkit.potion.PotionEffectType.SLOW;

public class IceAspect extends CustomEnchantment {

    public static final int ID = 29;

    @Override
    public Builder<IceAspect> defaults() {
        return new Builder<>(IceAspect::new, ID)
            .all(BaseEnchantments.ICE_ASPECT,
                    "Temporarily freezes the target",
                    new Tool[]{Tool.SWORD},
                    "Ice Aspect",
                    2, // MAX LVL
                    Hand.LEFT);
    }

    @Override
    public boolean onEntityHit(EntityDamageByEntityEvent evt, int level, boolean usedHand) {
        Utilities.addPotion((LivingEntity) evt.getEntity(), SLOW,
            (int) Math.round(40 + level * power * 40), (int) Math.round(power * level * 2));
        CompatibilityAdapter.display(Utilities.getCenter(evt.getEntity().getLocation()), Particle.CLOUD, 10, .1f, 1f, 2f, 1f);
        return true;
    }
}
