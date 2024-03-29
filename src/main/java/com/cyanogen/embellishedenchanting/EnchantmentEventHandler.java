package com.cyanogen.embellishedenchanting;

import com.cyanogen.embellishedenchanting.enchantments.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EnchantmentEventHandler {

    @SubscribeEvent
    public void onItemBroken(PlayerDestroyItemEvent event) {
        ImmortalEnchant.itemDestroyed(event);
        DeflagrationCurseEnchant.itemDestroyed(event);
    }

    @SubscribeEvent
    public void onDestroyBlock(BlockEvent.BreakEvent event){
        VoidingEnchant.onBreakBlock(event);
    }

    @SubscribeEvent
    public void onRenderCustomTooltip(RenderTooltipEvent.GatherComponents event){
        DeathsKnellEnchant.onTooltip(event);
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event){
        DeathsKnellEnchant.apportTome(event);
    }

    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event){
        DeathsKnellEnchant.onAttack(event);
        AnnihilationEnchant.onAttack(event);
        ShatteringEnchant.onAttack(event);
        HexEnchant.onAttack(event);
    }

    @SubscribeEvent
    public void onLivingDamage(LivingDamageEvent event){
        ImmortalEnchant.apportTome(event);
    }


    @SubscribeEvent
    public void debugEnchantmentAttackDmg(PlayerInteractEvent.EntityInteractSpecific event){
        Entity e = event.getTarget();
        Player player = event.getPlayer();

        if(player.getMainHandItem().is(Items.SEA_PICKLE) && e instanceof LivingEntity target){

            System.out.println("Health: "+target.getHealth());

        }

    }


}
