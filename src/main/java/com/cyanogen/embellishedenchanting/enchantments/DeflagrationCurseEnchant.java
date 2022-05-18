package com.cyanogen.embellishedenchanting.enchantments;

import com.cyanogen.embellishedenchanting.config.Options;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;

public class DeflagrationCurseEnchant extends Enchantment{

    protected DeflagrationCurseEnchant(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    public static boolean isEnabled =  Options.COMMON.DeflagrationCurse.get();

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
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public boolean isCurse() {
        return true;
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
    public boolean canEnchant(ItemStack pStack) {

        if(pStack.getItem() instanceof ArmorItem){
            return false;
        }
        else{
            return canApplyAtEnchantingTable(pStack);
        }
    }

    @Override
    protected boolean checkCompatibility(Enchantment pOther) {

        return pOther != Enchantments.BINDING_CURSE && pOther != Enchantments.VANISHING_CURSE && pOther != this;

    }

    public static void itemDestroyed(PlayerDestroyItemEvent event) {

        ItemStack i = event.getOriginal();
        Player player = event.getPlayer();
        Level l = player.getLevel();

        BlockPos pos = event.getPlayer().blockPosition();

        Explosion boom = new Explosion(
                l,
                event.getPlayer(),
                pos.getX(),
                pos.getY(),
                pos.getZ(),
                3,
                true,
                Explosion.BlockInteraction.DESTROY);

        int level = EnchantmentHelper.getItemEnchantmentLevel(_RegisterEnchants.DEFLAGRATION_CURSE.get(),i);
        if(level == 1){
            l.explode(player, pos.getX(), pos.getY(), pos.getZ(), 3, true, Explosion.BlockInteraction.DESTROY);
            player.hurt(DamageSource.explosion(boom), 6);
        }

    }

}
