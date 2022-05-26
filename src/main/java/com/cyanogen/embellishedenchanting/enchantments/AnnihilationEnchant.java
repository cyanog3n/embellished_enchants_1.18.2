package com.cyanogen.embellishedenchanting.enchantments;

import com.cyanogen.embellishedenchanting.config.Options;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

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

    public static final DamageSource ANNIHILATION = new DamageSource("annihilation");

    public static void onAttack(LivingAttackEvent event){

        Entity attacker = event.getSource().getEntity();
        LivingEntity target = event.getEntityLiving();

        if(attacker instanceof Player player && !player.level.isClientSide){
            float swing = player.getAttackAnim(2.0f);

            ItemStack heldItem = player.getMainHandItem();
            int enchLevel = EnchantmentHelper.getItemEnchantmentLevel(_RegisterEnchants.ANNIHILATION.get(), heldItem);

            if(swing == 0.0f && enchLevel > 0){

                float health = target.getMaxHealth();

                if(Math.random() <= 0.01 * enchLevel) {

                    if (health >= 100 || target instanceof Player) {
                        target.hurt(ANNIHILATION.setMagic(), 50);
                        target.invulnerableTime = 0;

                    } else {
                        target.hurt(ANNIHILATION.bypassMagic().bypassArmor(), 2147483647);
                    }

                    ServerLevel level = (ServerLevel) attacker.level;
                    level.sendParticles(
                            ParticleTypes.SOUL_FIRE_FLAME,
                            target.getX(), target.getY() + 1, target.getZ(), 24,
                            0.2, 0.2, 0.2, 0.2);

                }
            }
        }
    }


}
