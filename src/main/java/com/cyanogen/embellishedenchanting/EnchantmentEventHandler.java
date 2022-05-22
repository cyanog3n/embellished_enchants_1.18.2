package com.cyanogen.embellishedenchanting;

import com.cyanogen.embellishedenchanting.enchantments.DeathsKnellEnchant;
import com.cyanogen.embellishedenchanting.enchantments.DeflagrationCurseEnchant;
import com.cyanogen.embellishedenchanting.enchantments.ImmortalEnchant;
import com.cyanogen.embellishedenchanting.enchantments.VoidingEnchant;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
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
