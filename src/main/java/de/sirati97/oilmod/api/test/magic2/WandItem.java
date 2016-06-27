package de.sirati97.oilmod.api.test.magic2;

import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import org.bukkit.Material;

/**
 * Created by sirati97 on 26.06.2016 for OilMod-TestPlugin.
 */
public class WandItem extends OilItemBase<WandItemStack> {
    public WandItem() {
        super(Material.BLAZE_ROD, 0, "Wand", 1, "Wand");
    }

    @Override
    protected WandItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new WandItemStack(nmsItemStack, this);
    }
}