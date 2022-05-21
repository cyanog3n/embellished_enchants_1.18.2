package com.cyanogen.embellishedenchanting.enchantments;

import com.cyanogen.embellishedenchanting.config.Options;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class AnnihilationEnchant extends Enchantment{

    protected AnnihilationEnchant(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    public static boolean isEnabled =  Options.COMMON.Annihilation.get();

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
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    protected boolean checkCompatibility(Enchantment pOther) {

        return pOther != _RegisterEnchants.HEX.get() && pOther != this;

    }

    @Override
    public float getDamageBonus(int p_44682_, MobType p_44683_) {
        return super.getDamageBonus(p_44682_, p_44683_);
    }

    public static final DamageSource ANNIHILATION = new DamageSource("annihilation");

    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {

        if(pAttacker instanceof Player player && !player.level.isClientSide){

            float swing = player.getAttackAnim(2.0f);

            if(pTarget instanceof LivingEntity target && swing == 0.0f){

                float health = target.getMaxHealth();
                double n = Math.random();

                if(n <= 0.01 + 0.01 * pLevel){

                    if(health >= 100 || target instanceof Player){
                        target.hurt(ANNIHILATION, 50);
                    }
                    else{
                        target.hurt(ANNIHILATION.bypassMagic().bypassArmor(), 2147483647);
                    }
                }
            }

        }

    }
}
