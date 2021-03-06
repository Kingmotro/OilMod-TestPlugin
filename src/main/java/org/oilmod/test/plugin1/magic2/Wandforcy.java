package org.oilmod.test.plugin1.magic2;

import org.oilmod.api.userinterface.UIPanel;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 27.06.2016 for OilMod-TestPlugin.
 */
public interface Wandforcy {
    boolean onWandLeftClick(Wand wand, Player player, Action action);

    void onWandUse(Wand wand, Player player, Action action);

    void onWandUseOnBlock(Wand wand, Player player, Action action, Block blockClicked, BlockFace blockFace);

    boolean onWandLeftClickOnBlock(Wand wand, Player player, Action action, Block blockClicked, BlockFace blockFace);

    UIPanel getUIPanel();

    ItemStack asItemStack();

    String getCurrentDisplayName();

    String getSpellName();
}
