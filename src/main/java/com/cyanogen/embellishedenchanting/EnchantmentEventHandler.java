package com.cyanogen.embellishedenchanting;

import com.cyanogen.embellishedenchanting.enchantments.*;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
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
    public void onRenderCustomTooltip(RenderTooltipEvent.GatherComponents event){ DeathsKnellEnchant.onTooltip(event); }

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


}
