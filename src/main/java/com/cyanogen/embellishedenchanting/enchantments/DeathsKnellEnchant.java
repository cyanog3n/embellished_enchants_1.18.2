package com.cyanogen.embellishedenchanting.enchantments;

import com.cyanogen.embellishedenchanting.config.Options;
import com.mojang.datafixers.util.Either;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class DeathsKnellEnchant extends Enchantment {

    protected DeathsKnellEnchant(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return true;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }

    @Override
    public boolean isTreasureOnly() {
        return false;
    }

    @Override
    public boolean isTradeable() { return false; }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return false;
    }

    @Override
    protected boolean checkCompatibility(Enchantment p_44690_) {
        return super.checkCompatibility(p_44690_);
    }

    @Override
    public boolean canEnchant(ItemStack pStack) {

        return pStack.getItem() instanceof SwordItem || pStack.getItem() instanceof AxeItem;
    }

    public static void onAttack(LivingAttackEvent event) {

        Entity e = event.getSource().getEntity();
        LivingEntity living = event.getEntityLiving();

        if(e instanceof Player player){

            float swing = player.getAttackAnim(2.0f);

            ItemStack heldItem = player.getMainHandItem();

            int enchLevel = EnchantmentHelper.getItemEnchantmentLevel(_RegisterEnchants.DEATHS_KNELL.get(), heldItem);

            if(swing == 0.0f && enchLevel > 0){

                double maxHealth = living.getAttributeBaseValue(Attributes.MAX_HEALTH);
                if(maxHealth != 0){
                    living.getAttribute(Attributes.MAX_HEALTH).setBaseValue(maxHealth - 1.0d * enchLevel);
                }

            }
        }

    }

    @Override
    public Component getFullname(int pLevel) {
        MutableComponent mutablecomponent = new TranslatableComponent(this.getDescriptionId());
        mutablecomponent.withStyle(ChatFormatting.LIGHT_PURPLE);


        if (pLevel != 1 || this.getMaxLevel() != 1) {
            mutablecomponent.append(" ").append(new TranslatableComponent("enchantment.level." + pLevel));
        }

        return mutablecomponent;
    }

    public static void onTooltip(RenderTooltipEvent.GatherComponents event){

        ItemStack stack = event.getItemStack();
        int enchLevel = EnchantmentHelper.getItemEnchantmentLevel(_RegisterEnchants.DEATHS_KNELL.get(), stack);

        if(enchLevel != 0 && !stack.is(Items.ENCHANTED_BOOK)){

            FormattedText t = FormattedText.of("+" +enchLevel+ " Permanent Damage", Style.EMPTY.withColor(ChatFormatting.BLUE));
            event.getTooltipElements().add(Either.left(t));

        }

    }

    public static void apportTome(LivingDeathEvent event){

        LivingEntity living = event.getEntityLiving();
        BlockPos pos = living.blockPosition();
        DamageSource source = event.getSource();
        Level level = living.level;

        if(living instanceof WitherBoss
                && source.isExplosion()
                && !level.isClientSide
                && Math.random()<=0.07
                && Options.COMMON.DeathsKnell.get()){

            ItemStack stack = new ItemStack(Items.ENCHANTED_BOOK,1);
            EnchantedBookItem.addEnchantment(stack, new EnchantmentInstance(_RegisterEnchants.DEATHS_KNELL.get(),
                    (int) Math.floor(Math.random() * 3) + 1));

            ServerLevel server = (ServerLevel) level;
            server.addFreshEntity(new ItemEntity(server, pos.getX(), pos.getY(), pos.getZ(), stack, 0,0,0));

        }
    }
}
