package com.cyanogen.embellishedenchanting.enchantments;

import com.cyanogen.embellishedenchanting.config.Options;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

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
    protected boolean checkCompatibility(Enchantment enchant) {
        return super.checkCompatibility(enchant) && !enchant.equals(Enchantments.MENDING);
    }

    @Override
    public boolean canEnchant(ItemStack stack) {

        return isEnabled && this.canApplyAtEnchantingTable(stack) && stack.isDamageableItem();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {

        if(stack.getItem() instanceof AxeItem || stack.getItem() instanceof TridentItem){
            return true;
        }
        else{
            return super.canApplyAtEnchantingTable(stack);
        }
    }

    public static void onAttack(LivingAttackEvent event){

        DamageSource source = event.getSource();
        Entity attacker = source.getEntity();
        LivingEntity target = event.getEntityLiving();

        if(attacker instanceof Player player && source.getMsgId().equals("player") && !player.level.isClientSide){
            float swing = player.getAttackAnim(2.0f);

            ItemStack heldItem = player.getMainHandItem();
            int enchLevel = EnchantmentHelper.getItemEnchantmentLevel(_RegisterEnchants.SHATTERING.get(), heldItem);

            if(enchLevel != 0 && Math.random() <= (0.05 * enchLevel + 0.05) && swing == 0.0f) {

                float attackDamage = (float)player.getAttributeValue(Attributes.ATTACK_DAMAGE);
                float extraDamage = attackDamage * enchLevel / 3;

                int durability = heldItem.getMaxDamage();
                int itemDamage = (durability / 240) * enchLevel;

                target.hurt(DamageSource.playerAttack(player), extraDamage);
                target.invulnerableTime = 0;

                heldItem.hurtAndBreak(itemDamage, player, LivingEntity::stopUsingItem);

                //type, xpos, ypos, zpos, count, xoffset, yoffset, zoffset, speed
                ServerLevel level = (ServerLevel) player.level;

                level.sendParticles(
                        ParticleTypes.SCRAPE,
                        target.getX(), target.getY() + 1, target.getZ(), 16,
                        0, 0, 0, 10);
            }
        }

    }

    //occasionally deals increased damage at the cost of a portion of durability
    //higher levels increase damage dealt, increase frequency, but increase item damage

    //lv1 - 10% chance, deals 33% more damage, 0.4% of durability
    //lvl2 - 15% chance, deals 66% more damage, 0.83% of durability
    //lvl3 - 20% chance, deals 100% more damage, 1.2% of durability

}