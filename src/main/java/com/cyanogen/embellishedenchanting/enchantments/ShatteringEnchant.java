package com.cyanogen.embellishedenchanting.enchantments;

import com.cyanogen.embellishedenchanting.config.Options;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.Random;

public class ShatteringEnchant extends Enchantment{

    protected ShatteringEnchant(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    public static boolean isEnabled =  Options.COMMON.Shattering.get();

    @Override
    public Component getFullname(int pLevel) {
        MutableComponent mutablecomponent = new TranslatableComponent(this.getDescriptionId());
        mutablecomponent.withStyle(ChatFormatting.DARK_PURPLE);


        if (pLevel != 1 || this.getMaxLevel() != 1) {
            mutablecomponent.append(" ").append(new TranslatableComponent("enchantment.level." + pLevel));
        }

        return mutablecomponent;
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
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {

        ItemStack heldItem = pAttacker.getMainHandItem();
        float attackDamage = 0f;

        if(heldItem.getItem() instanceof SwordItem sword){
            attackDamage = sword.getDamage() + 1;
        }
        else if(heldItem.getItem() instanceof AxeItem axe){
            attackDamage = axe.getAttackDamage() + 1;
        }

        float extraDamage = attackDamage * pLevel / 3;

        int durability = heldItem.getMaxDamage();
        int damage = (durability / 300) * pLevel;

        if(pAttacker instanceof Player player && !pAttacker.level.isClientSide) {
            float swing = player.getAttackAnim(2.0f);

            if (Math.random() <= (0.05 * pLevel + 0.05) && swing == 0.0f) {
                pTarget.hurt(DamageSource.mobAttack(pAttacker), extraDamage);

                if (durability - heldItem.getDamageValue() >= damage) {
                    heldItem.hurtAndBreak(damage, pAttacker, LivingEntity::stopUsingItem);
                } else {
                    heldItem.hurt(damage, new Random(), null);
                }

            }
        }

        //occasionally deals increased damage at the cost of a portion of durability
        //higher levels increase damage dealt, increase frequency, but increase item damage

        //lv1 - 10% chance, deals 33% more damage, 0.33% of durability
        //lvl2 - 15% chance, deals 66% more damage, 0.66% of durability
        //lvl3 - 20% chance, deals 100% more damage, 1% of durability

    }

}