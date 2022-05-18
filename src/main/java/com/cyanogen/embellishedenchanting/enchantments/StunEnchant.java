package com.cyanogen.embellishedenchanting.enchantments;

import com.cyanogen.embellishedenchanting.config.Options;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class StunEnchant extends Enchantment{

    protected StunEnchant(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    public static boolean isEnabled =  Options.COMMON.Stun.get();

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
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }


    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {

        if(isEnabled && stack.getItem() instanceof AxeItem){
            return true;
        }
        else if(isEnabled){
            return stack.canApplyAtEnchantingTable(this);
        }
        else{
            return false;
        }
    }


    @Override
    protected boolean checkCompatibility(Enchantment pOther) {

        return pOther != _RegisterEnchants.DECAY.get() && pOther != _RegisterEnchants.ANTIGRAVITY.get() && pOther != this;

    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {

        if(pTarget instanceof LivingEntity target){
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10 + 10 * pLevel, 255, false, false, false));
        }

    }
}