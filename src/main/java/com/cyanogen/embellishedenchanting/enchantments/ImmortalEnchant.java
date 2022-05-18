package com.cyanogen.embellishedenchanting.enchantments;

import com.cyanogen.embellishedenchanting.config.Options;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;


public class ImmortalEnchant extends Enchantment {

    protected ImmortalEnchant(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
        super(pRarity, pCategory , pApplicableSlots);
    }

    public static boolean isEnabled =  Options.COMMON.Immortal.get();

    @Override
    public boolean isTreasureOnly() {
        return true;
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
    public boolean isTradeable() { return false; }

    @Override
    public boolean canEnchant(ItemStack pStack) {
        if(pStack.getItem().canBeDepleted() && !(pStack.getItem() instanceof ArmorItem) && !(pStack.getItem() instanceof ElytraItem)){
            return true;
        }
        else{
            return canApplyAtEnchantingTable(pStack);
        }
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    public static void apportTome(LivingDamageEvent event) {

        //this is so dumb but it works

        LivingEntity living = event.getEntityLiving();
        Level level = living.level;
        BlockPos pos = living.blockPosition();

        ItemStack mainhand = living.getItemBySlot(EquipmentSlot.MAINHAND);
        ItemStack offhand = living.getItemBySlot(EquipmentSlot.OFFHAND);

        if(living instanceof Player && event.getAmount() >= living.getHealth() && Options.COMMON.Immortal.get()){
            if(mainhand.is(Items.TOTEM_OF_UNDYING) || offhand.is(Items.TOTEM_OF_UNDYING)){

                ItemStack stack = new ItemStack(Items.ENCHANTED_BOOK,1);
                stack.enchant(_RegisterEnchants.IMMORTAL.get(), 1);

                ServerLevel server = (ServerLevel) level;
                if(Math.random() <= 0.35d){
                    server.addFreshEntity(new ItemEntity(server, pos.getX(), pos.getY(), pos.getZ(), stack, 0,0,0));
                }

            }
        }

    }

    public static void itemDestroyed(PlayerDestroyItemEvent event){

        ItemStack i = event.getOriginal();
        InteractionHand hand = event.getHand();

        int level = EnchantmentHelper.getItemEnchantmentLevel(_RegisterEnchants.IMMORTAL.get(),i);
        if(level == 1){
            i.setDamageValue(0);
            event.getPlayer().getEnderChestInventory().addItem(i);
        }
    }

}
