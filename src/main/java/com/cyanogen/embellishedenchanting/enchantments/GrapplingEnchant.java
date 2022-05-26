package com.cyanogen.embellishedenchanting.enchantments;

import com.cyanogen.embellishedenchanting.config.Options;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;


public class GrapplingEnchant extends Enchantment {

    public GrapplingEnchant(Rarity p_44676_, EnchantmentCategory p_44677_, EquipmentSlot[] p_44678_) {
        super(p_44676_, p_44677_, p_44678_);
    }

    public static boolean isEnabled =  Options.COMMON.Grappling.get();

    @Override
    public boolean isAllowedOnBooks() {
        return isEnabled;
    }

    @Override
    public boolean isDiscoverable() {
        return isEnabled;
    }

    @Override
    public boolean isTradeable() { return isEnabled; }

    @Override
    public boolean canEnchant(ItemStack stack) {

        return isEnabled && this.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {

        if(stack.getItem() instanceof TridentItem){
            return true;
        }
        else{
            return super.canApplyAtEnchantingTable(stack);
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

    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {

        DamageSource source = null;

        if(pTarget instanceof LivingEntity living){
            source = living.getLastDamageSource();
        }

        BlockPos attackerPos = pAttacker.blockPosition();
        BlockPos targetPos = pTarget.blockPosition();

        double x = attackerPos.getX() - targetPos.getX();
        double y = attackerPos.getY() - targetPos.getY();
        double z = attackerPos.getZ() - targetPos.getZ();

        double d = Math.sqrt(Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2)); //straight line distance

        double a = 1.1;
        double b = 14;
        double c = 1.3;
        double p = 0.48;
        double q = 9.7;
        double w = 0.013;

        double factor = b / (w*d + 1 + Math.pow(c , -a * (p * d - q)));
        //this is a sigmoid function that tends toward 0 for very large distances

        double t = factor * x / d;

        double u;
        if(y <= 1 && y>= -1){
            u = 0;
        }
        else{
            u = factor * y / (Math.pow(d,1.3) + 1) ; //additional factor to lower magnitude of y acceleration
        }
        double v = factor * z / d;


        if(!pTarget.getLevel().isClientSide && source != null){
            if(source.isProjectile()){
                pTarget.setDeltaMovement(0, 0, 0);
                pTarget.setDeltaMovement(t, u, v);
            }
        }



    }

}
