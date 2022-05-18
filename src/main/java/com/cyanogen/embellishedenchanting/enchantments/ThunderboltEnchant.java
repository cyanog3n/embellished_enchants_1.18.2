package com.cyanogen.embellishedenchanting.enchantments;

import com.cyanogen.embellishedenchanting.config.Options;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;


public class ThunderboltEnchant extends Enchantment {

    public ThunderboltEnchant(Rarity p_44676_, EnchantmentCategory p_44677_, EquipmentSlot[] p_44678_) {
        super(p_44676_, p_44677_, p_44678_);
    }

    public static boolean isEnabled =  Options.COMMON.Thunderbolt.get();

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
        return true;
    }

    @Override
    public boolean isTradeable() { return false; }

    @Override
    public boolean canEnchant(ItemStack pStack) {
        if(pStack.getItem() instanceof CrossbowItem){
            return true;
        }
        else{
            return canApplyAtEnchantingTable(pStack);
        }
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {

        DamageSource source = null;

        if(pTarget instanceof LivingEntity living){
            source = living.getLastDamageSource();
        }

        BlockPos posT = pTarget.getOnPos();

        if(!pTarget.level.isClientSide && source != null){

            if(source.isProjectile()){
                EntityType.LIGHTNING_BOLT.spawn((ServerLevel) pTarget.level,
                        null,
                        null,
                        posT,
                        MobSpawnType.TRIGGERED,
                        true,
                        true);
            }

        }

    }

}
