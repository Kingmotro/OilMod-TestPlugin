package org.oilmod.test.plugin1;

import org.oilmod.api.inventory.ItemFilter;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 13.02.2016.
 */
public class BlockFilter implements ItemFilter {
    public final static BlockFilter INSTANCE = new BlockFilter();
    @Override
    public boolean allowed(ItemStack itemStack) {
        return itemStack.getType().isBlock();
    }
}
