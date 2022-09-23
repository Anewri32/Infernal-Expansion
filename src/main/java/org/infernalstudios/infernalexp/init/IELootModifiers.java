/*
 * Copyright 2022 Infernal Studios
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.infernalstudios.infernalexp.init;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.config.InfernalExpansionConfig;
import org.jetbrains.annotations.NotNull;

public class IELootModifiers {

    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, InfernalExpansion.MOD_ID);

    public static final RegistryObject<Codec<HoglinLootModifier>> HOGLIN_LOOT_MODIFIER = LOOT_MODIFIERS.register("hoglin_loot_modifier", () -> HoglinLootModifier.CODEC);

    private static class HoglinLootModifier extends LootModifier {

        /**
         * Constructs a LootModifier.
         *
         * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
         */

        public static final Codec<HoglinLootModifier> CODEC = RecordCodecBuilder.create(builder -> codecStart(builder).apply(builder, HoglinLootModifier::new));

        protected HoglinLootModifier(LootItemCondition[] conditions) {
            super(conditions);
        }

        @Override
        protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
            if (!InfernalExpansionConfig.MobInteractions.USE_HOGCHOPS.getBoolean())
                return generatedLoot;

            int numChops = 0;
            int numCookedChops = 0;

            for (ItemStack loot : generatedLoot) {
                if (loot.sameItem(Items.PORKCHOP.getDefaultInstance())) {
                    numChops = loot.getCount();

                } else if (loot.sameItem(Items.COOKED_PORKCHOP.getDefaultInstance())) {
                    numCookedChops = loot.getCount();
                }
            }

            generatedLoot.removeIf(stack -> stack.is(Items.PORKCHOP));
            generatedLoot.removeIf(stack -> stack.is(Items.COOKED_PORKCHOP));

            if (context.getRandom().nextFloat() < 0.4)
                return generatedLoot;

            generatedLoot.add(new ItemStack(IEItems.COOKED_HOGCHOP.get(), Math.max(numCookedChops, 0)));
            generatedLoot.add(new ItemStack(IEItems.RAW_HOGCHOP.get(), Math.max(numChops, 1)));

            return generatedLoot;
        }

        @Override
        public Codec<? extends IGlobalLootModifier> codec() {
            return CODEC;
        }

    }

    public static void register(IEventBus eventBus) {
        LOOT_MODIFIERS.register(eventBus);
        InfernalExpansion.LOGGER.info("Infernal Expansion: Loot Modifiers Registered!");
    }

}
