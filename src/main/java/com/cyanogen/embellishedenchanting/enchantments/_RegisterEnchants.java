package com.cyanogen.embellishedenchanting.enchantments;

import com.cyanogen.embellishedenchanting.EmbellishedEnchanting;
import com.cyanogen.embellishedenchanting.config.Options;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class _RegisterEnchants {

    public static final DeferredRegister<Enchantment> ENCHANTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, EmbellishedEnchanting.MOD_ID);

    public static Enchantment.Rarity Rarity(String name){
        return Enchantment.Rarity.valueOf(name);
    }

    //---ONLY OBTAINABLE THROUGH LOOT---//

    public static final RegistryObject<Enchantment> THUNDERBOLT = ENCHANTS.register("thunderbolt", () -> new ThunderboltEnchant(
            Rarity(Options.COMMON.ThunderboltRarity.get()),
            EnchantmentCategory.BOW,
            new EquipmentSlot[]{ EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND }

    ));

    //---TREASURE---//

    public static final RegistryObject<Enchantment> ANNIHILATION = ENCHANTS.register("annihilation", () -> new AnnihilationEnchant(
            Rarity(Options.COMMON.AnnihilationRarity.get()),
            EnchantmentCategory.WEAPON,
            new EquipmentSlot[]{ EquipmentSlot.MAINHAND}
    ));

    public static final RegistryObject<Enchantment> ANTIGRAVITY = ENCHANTS.register("antigravity", () -> new AntigravityEnchant(
            Rarity(Options.COMMON.AntigravRarity.get()),
            EnchantmentCategory.WEAPON,
            new EquipmentSlot[]{ EquipmentSlot.MAINHAND}
    ));

    public static final RegistryObject<Enchantment> DEFLAGRATION_CURSE = ENCHANTS.register("deflagration_curse", () -> new DeflagrationCurseEnchant(
            Rarity(Options.COMMON.DeflagrationCurseRarity.get()),
            EnchantmentCategory.BREAKABLE,
            new EquipmentSlot[]{ EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND }
    ));

    //---OBTAINABLE NORMALLY---//

    public static final RegistryObject<Enchantment> DECAY = ENCHANTS.register("decay", () -> new DecayEnchant(
            Rarity(Options.COMMON.DecayRarity.get()),
            EnchantmentCategory.WEAPON,
            new EquipmentSlot[]{ EquipmentSlot.MAINHAND}
    ));


    public static final RegistryObject<Enchantment> HEX = ENCHANTS.register("hex", () -> new HexEnchant(
            Rarity(Options.COMMON.HexRarity.get()),
            EnchantmentCategory.WEAPON,
            new EquipmentSlot[]{ EquipmentSlot.MAINHAND}

    ));

    public static final RegistryObject<Enchantment> STUN = ENCHANTS.register("stun", () -> new StunEnchant(
            Rarity(Options.COMMON.StunRarity.get()),
            EnchantmentCategory.WEAPON,
            new EquipmentSlot[]{ EquipmentSlot.MAINHAND}

    ));

    public static final RegistryObject<Enchantment> GRAPPLING = ENCHANTS.register("grappling", () -> new GrapplingEnchant(
            Rarity(Options.COMMON.GrapplingRarity.get()),
            EnchantmentCategory.CROSSBOW,
            new EquipmentSlot[]{ EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND }
    ));

    public static final RegistryObject<Enchantment> SHATTERING = ENCHANTS.register("blurse_of_shattering", () -> new ShatteringEnchant(
            Rarity(Options.COMMON.ShatteringRarity.get()),
            EnchantmentCategory.WEAPON,
            new EquipmentSlot[]{ EquipmentSlot.MAINHAND }
    ));

    public static final RegistryObject<Enchantment> SHOCKWAVE = ENCHANTS.register("shockwave", () -> new ShockwaveEnchant(
            Rarity(Options.COMMON.ShockwaveRarity.get()),
            EnchantmentCategory.BOW,
            new EquipmentSlot[]{ EquipmentSlot.MAINHAND }
    ));

    public static final RegistryObject<Enchantment> VOIDING = ENCHANTS.register("voiding", () -> new VoidingEnchant(
            Rarity(Options.COMMON.VoidingRarity.get()),
            EnchantmentCategory.DIGGER,
            new EquipmentSlot[]{ EquipmentSlot.MAINHAND }
    ));

    public static final RegistryObject<Enchantment> INCISIVE = ENCHANTS.register("incisive", () -> new IncisiveEnchant(
            Rarity(Options.COMMON.IncisiveRarity.get()),
            EnchantmentCategory.TRIDENT,
            new EquipmentSlot[]{ EquipmentSlot.MAINHAND }
    ));

    //---APPORT---//

    public static final RegistryObject<Enchantment> DEATHS_KNELL = ENCHANTS.register("deaths_knell", () -> new DeathsKnellEnchant(
            Enchantment.Rarity.VERY_RARE,
            EnchantmentCategory.WEAPON,
            new EquipmentSlot[]{ EquipmentSlot.MAINHAND }
    ));

    public static final RegistryObject<Enchantment> IMMORTAL = ENCHANTS.register("immortal", () -> new ImmortalEnchant(
            Enchantment.Rarity.VERY_RARE,
            EnchantmentCategory.DIGGER,
            new EquipmentSlot[]{ EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND }
    ));

    public static void register(IEventBus eventBus){
        ENCHANTS.register(eventBus);
    }

}
