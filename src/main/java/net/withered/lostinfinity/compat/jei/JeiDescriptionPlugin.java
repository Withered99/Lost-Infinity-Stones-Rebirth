package net.withered.lostinfinity.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.withered.lostinfinity.item.ModItems;

import java.util.Collections;

@JeiPlugin
public class JeiDescriptionPlugin implements IModPlugin {

    @Override
    public Identifier getPluginUid() {
        return Identifier.of("lostinfinity", "jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        if (ModItems.CELESTIAL_DIAMOND != null) {
            Text descriptionText = Text.translatable("jei.lostinfinity.celestial_diamond.description");
            registration.addIngredientInfo(
                    new ItemStack(ModItems.CELESTIAL_DIAMOND),
                    VanillaTypes.ITEM_STACK,
                    descriptionText
            );
        }
        if (ModItems.DEVIANT_ENDERMAN_SPAWN_EGG != null) {
            Text descriptionText = Text.translatable("jei.lostinfinity.deviant_enderman_spawn_egg.description");
            registration.addIngredientInfo(
                    new ItemStack(ModItems.DEVIANT_ENDERMAN_SPAWN_EGG),
                    VanillaTypes.ITEM_STACK,
                    descriptionText
            );
        }
    }
}
