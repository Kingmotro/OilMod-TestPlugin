package de.sirati97.oilmod.api.test.backpack;

import de.sirati97.oilmod.api.items.ItemRegistry;
import de.sirati97.oilmod.api.items.NMSItemStack;
import de.sirati97.oilmod.api.items.OilBukkitItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.items.crafting.ItemCraftingFactory;
import de.sirati97.oilmod.api.items.crafting.ModItemOilCraftingIngredient;
import de.sirati97.oilmod.api.items.crafting.OilCraftingRecipe;
import de.sirati97.oilmod.api.items.crafting.OilCraftingResult;
import de.sirati97.oilmod.api.items.crafting.OilItemOilCraftingResult;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 * Created by sirati97 on 12.02.2016.
 */
public class BackpackItem extends OilItemBase<BackpackItemStack> {
    private final int rows;

    public BackpackItem(int id, int rows) {
        super(Material.LEATHER, 0, id, 1, names[rows-1] + " Backpack"); //defines Backpack item
        this.rows = rows;
    }

    public int getRows() {
        return rows;
    }

    @Override
    public BackpackItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        return new BackpackItemStack(nmsItemStack, this); //Uses custom itemstack class (for handling nbt/the inventory)
    }


    private static BackpackItem[] backpacks;
    private static String[] names = {"Tiny", "Small", "Medium", "Big", "Huge", "Very Huge"};
    private static class BackpackIncreaseSizeCraftingResult implements OilCraftingResult {

        @Override
        public ItemStack preCraftResult(ItemStack[] itemStacks, boolean shaped, int width, int height) { //Here are all fast crafting processes that create the result
            BackpackItemStack backpack = null;
            for (ItemStack itemStack:itemStacks) {
                if (itemStack instanceof OilBukkitItemStack && ((OilBukkitItemStack) itemStack).getOilItemStack() instanceof BackpackItemStack) {
                    backpack = (BackpackItemStack) ((OilBukkitItemStack) itemStack).getOilItemStack();
                    break;
                }
            }
            if (backpack == null) {
                throw new IllegalStateException("Somehow no backpack is enlarged, because there is no backpack");
            }
            if (backpack.getRows() >= backpacks.length) {
                return null; //Maximum size reached.
            }

            BackpackItemStack newBackpack = (BackpackItemStack) ((OilBukkitItemStack)backpacks[backpack.getRows()].createItemStack(1)).getOilItemStack(); //Creates a backpack of the next size
            if (!backpack.createDisplayName().equals(backpack.getCurrentDisplayName())) {
                newBackpack.setDisplayName(backpack.getCurrentDisplayName(), true);//If old backpack was renamed. rename new backpack as well
            }
            return newBackpack.getNmsItemStack().asBukkitItemStack();
        }

        @Override
        public void craftResult(ItemStack result, ItemStack[] itemStacks, boolean shaped, int width, int height) { //Here are all expensive processes that doesn't change the appearance of the result item.
            BackpackItemStack backpack = null;
            for (ItemStack itemStack:itemStacks) {
                if (itemStack instanceof OilBukkitItemStack && ((OilBukkitItemStack) itemStack).getOilItemStack() instanceof BackpackItemStack) {
                    backpack = (BackpackItemStack) ((OilBukkitItemStack) itemStack).getOilItemStack();
                    break;
                }
            }
            if (backpack == null) {
                throw new IllegalStateException("Somehow no backpack is enlarged, because there is no backpack");
            }

            BackpackItemStack newBackpack = (BackpackItemStack) ((OilBukkitItemStack)result).getOilItemStack(); //Gets backpack itemstack class
            backpack.copyTo(newBackpack); //copy inventory
        }
    }

    private static class BackpackIncreaseSizeCraftingIngredient extends ModItemOilCraftingIngredient {

        public BackpackIncreaseSizeCraftingIngredient() {
            super(BackpackItem.class);
        }

        @Override
        public ItemStack getRandomExample(Random rnd) {
            ItemStack[] result = backpacks[rnd.nextInt(backpacks.length-1)].getNaturalExamples(); //Will ensure that all examples are valid as the biggest backpack cannot be enlarged
            return result[rnd.nextInt(result.length)];
        }
    }

    public static void registerBackpacks(ItemRegistry registry, int startId) {
        //Register Items
        BackpackShoulderStrapsItem shoulderStrapsItem=new BackpackShoulderStrapsItem(startId++); //creates item with free id
        BackpackSackItem sackItem=new BackpackSackItem(startId++);
        registry.register(shoulderStrapsItem); //registers the,
        registry.register(sackItem);
        backpacks = new BackpackItem[names.length];
        for (int idOffset=0;idOffset<names.length;idOffset++) {
            BackpackItem item = new BackpackItem(startId+idOffset, 1+idOffset); //creates all the different backpacks
            registry.register(item);
            backpacks[idOffset] = item;
        }
        //Add crafting recipes
        OilCraftingRecipe recipe;
        recipe = ItemCraftingFactory.createShapedRecipe(2,3, new OilItemOilCraftingResult(shoulderStrapsItem, 4), Material.STRING, Material.LEATHER, null, Material.LEATHER, Material.IRON_INGOT, Material.LEATHER);
        ItemCraftingFactory.registerGlobal(recipe);
        recipe = ItemCraftingFactory.createShapedRecipe(3,3, new OilItemOilCraftingResult(sackItem, 1), Material.PAPER, Material.PAPER, Material.PAPER, Material.LEATHER, Material.STICK, Material.LEATHER, Material.STRING, Material.LEATHER, Material.STRING);
        ItemCraftingFactory.registerGlobal(recipe);
        recipe = ItemCraftingFactory.createShapedRecipe(2,2, new OilItemOilCraftingResult(backpacks[0], 1), BackpackSackItem.class, BackpackShoulderStrapsItem.class, Material.SLIME_BALL, BackpackShoulderStrapsItem.class);
        ItemCraftingFactory.registerGlobal(recipe);
        recipe = ItemCraftingFactory.createShapelessRecipe(new BackpackIncreaseSizeCraftingResult(), new BackpackIncreaseSizeCraftingIngredient(), BackpackSackItem.class, Material.LEATHER, Material.PAPER);
        ItemCraftingFactory.registerGlobal(recipe);
    }

}
