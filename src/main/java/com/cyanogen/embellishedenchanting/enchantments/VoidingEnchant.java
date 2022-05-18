package com.cyanogen.embellishedenchanting.enchantments;

import com.cyanogen.embellishedenchanting.config.Options;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.world.BlockEvent;

import java.util.ArrayList;

public class VoidingEnchant extends Enchantment {

    protected VoidingEnchant(Rarity p_44676_, EnchantmentCategory p_44677_, EquipmentSlot[] p_44678_) {
        super(p_44676_, p_44677_, p_44678_);
    }

    public static boolean isEnabled = Options.COMMON.Voiding.get();

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return isEnabled;
    }

    @Override
    public boolean isDiscoverable() {
        return isEnabled;
    }

    @Override
    public boolean isTreasureOnly() {
        return false;
    }

    @Override
    public boolean isTradeable() { return true; }

    @Override
    public boolean canEnchant(ItemStack pStack) {
        return canApplyAtEnchantingTable(pStack);
    }


    public static void onBreakBlock(BlockEvent.BreakEvent event){

        BlockPos pos = event.getPos();
        Player player = event.getPlayer();

        Level level = event.getPlayer().level;
        BlockState targetBlock = event.getState();

        ItemStack item = player.getItemInHand(player.getUsedItemHand());
        int enchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(_RegisterEnchants.VOIDING.get(), item);


        ArrayList<Block> whitelist = new ArrayList<>();
        whitelist.add(Blocks.STONE);
        whitelist.add(Blocks.DIRT);
        whitelist.add(Blocks.GRASS_BLOCK);
        whitelist.add(Blocks.ANDESITE);
        whitelist.add(Blocks.GRANITE);
        whitelist.add(Blocks.DIORITE);
        whitelist.add(Blocks.DEEPSLATE);
        whitelist.add(Blocks.NETHERRACK);
        whitelist.add(Blocks.END_STONE);
        whitelist.add(Blocks.SAND);
        whitelist.add(Blocks.SANDSTONE);
        whitelist.add(Blocks.GRAVEL);
        whitelist.add(Blocks.CALCITE);
        whitelist.add(Blocks.TUFF);
        whitelist.add(Blocks.BLACKSTONE);
        whitelist.add(Blocks.BASALT);


        for(Block b : whitelist){

            if(targetBlock.getBlock().equals(b) && enchantmentLevel > 0){
                event.setCanceled(true);
                level.removeBlock(pos, true);

            }

        }

    }
}
