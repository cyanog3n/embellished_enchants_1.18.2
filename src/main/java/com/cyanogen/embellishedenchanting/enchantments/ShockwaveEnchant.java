package com.cyanogen.embellishedenchanting.enchantments;

import com.cyanogen.embellishedenchanting.config.Options;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ShockwaveEnchant extends Enchantment{

    public ShockwaveEnchant(Rarity p_44676_, EnchantmentCategory p_44677_, EquipmentSlot[] p_44678_) {
        super(p_44676_, p_44677_, p_44678_);
    }

    public static boolean isEnabled =  Options.COMMON.Shockwave.get();

    @Override
    public boolean isAllowedOnBooks() {
        return isEnabled;
    }

    @Override
    public boolean isDiscoverable() {
        return isEnabled;
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

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
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if(isEnabled){
            return stack.canApplyAtEnchantingTable(this);
        }
        else{
            return false;
        }
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {

        if(pTarget instanceof LivingEntity living){
            DamageSource source = living.getLastDamageSource();
            Level level = living.getLevel();
            double radius = 1.5 + 0.5 * pLevel;

            BlockPos posT = living.blockPosition();
            AABB area = new AABB(
                    posT.getX() - radius,
                    posT.getY() - 1,
                    posT.getZ() - radius,
                    posT.getX() + radius,
                    posT.getY() + 1,
                    posT.getZ() + radius);

            List<Entity> list = level.getEntities(pTarget, area);


            if(source != null && source.isProjectile()){

                for(Entity e : list){
                    if(e instanceof LivingEntity entity && e != pAttacker){
                        entity.hurt(DamageSource.GENERIC, 0.5f + 0.5f * pLevel);
                    }
                }
            }
        }


    }
}
