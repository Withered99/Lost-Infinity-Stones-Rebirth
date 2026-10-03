package net.withered.lostinfinity.item.custom.stones.natural;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class AspirationStoneItem extends Item {
    public AspirationStoneItem(Settings settings) {
        super(settings);
    }

    private Text getAspirationGradient() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.world == null) {
            return Text.literal("Aspiration");
        }

        long time = client.world.getTime();
        String word = "Aspiration";
        MutableText aspiration = Text.empty();

        int colorA = 0x00AAAA;
        int colorB = 0x0000FF;

        for (int i = 0; i < word.length(); i++) {
            char character = word.charAt(i);

            float ratio = (float) ((Math.sin((time * 0.1f) + (i * 0.15f)) + 1.0) / 2.0);
            int rgb = blendColors(colorA, colorB, ratio);

            aspiration.append(Text.literal(String.valueOf(character)).setStyle(Style.EMPTY.withColor(rgb)));
        }

        return aspiration;
    }

    private int blendColors(int c1, int c2, float ratio) {
        int r = (int) (((c1 >> 16) & 0xFF) + ratio * (((c2 >> 16) & 0xFF) - ((c1 >> 16) & 0xFF)));
        int g = (int) (((c1 >> 8) & 0xFF) + ratio * (((c2 >> 8) & 0xFF) - ((c1 >> 8) & 0xFF)));
        int b = (int) ((c1 & 0xFF) + ratio * ((c2 & 0xFF) - (c1 & 0xFF)));
        return (r << 16) | (g << 8) | b;
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.translatable(this.getTranslationKey(stack), getAspirationGradient());
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal("A powerful ancient cube, containing an essence of consciousness.").formatted(Formatting.GOLD));

        super.appendTooltip(stack, context, tooltip, type);
    }
}
