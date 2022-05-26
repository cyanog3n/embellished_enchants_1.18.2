package com.cyanogen.embellishedenchanting.enchantments;

import com.cyanogen.embellishedenchanting.config.Options;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class HexEnchant extends Enchantment{

    protected HexEnchant(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    public static boolean isEnabled =  Options.COMMON.Hex.get();

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
        return 4;
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
    protected boolean checkCompatibility(Enchantment pOther) {

        return pOther != _RegisterEnchants.ANNIHILATION.get() && pOther != Enchantments.SHARPNESS && pOther != this;

    }

    public static void onAttack(LivingAttackEvent event){

        Entity attacker = event.getSource().getEntity();
        LivingEntity target = event.getEntityLiving();

        if(attacker instanceof Player player && !attacker.level.isClientSide){

            float swing = player.getAttackAnim(2.0f);

            ItemStack heldItem = player.getMainHandItem();
            int enchLevel = EnchantmentHelper.getItemEnchantmentLevel(_RegisterEnchants.HEX.get(), heldItem);

            if(swing == 0.0f && enchLevel > 0){

                float health = target.getMaxHealth();
                float hexDamage = 0.008f * health * enchLevel;

                if(hexDamage <= 20){
                    target.hurt(DamageSource.MAGIC, hexDamage);
                }
                else{
                    target.hurt(DamageSource.MAGIC.setMagic(), 20);
                }
                target.invulnerableTime = 0;

            }
        }
    }
}
