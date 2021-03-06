package net.onelikeandidie.bordergods.gods;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.onelikeandidie.bordergods.events.DroppedInLavaCallback;
import net.onelikeandidie.bordergods.util.Border;
import net.onelikeandidie.bordergods.util.config.gods.EnormaLoader;

public class Enorma implements IGod {
    static final String GOD_NAME = "Enorma";
    static final Text GOD_NAME_FORMATED = Text.of(GOD_NAME).copy().setStyle(Style.EMPTY.withColor(7811093));
    float satisfaction;
    static final float maxSatisfaction = 20;
    static final float minSatisfaction = 1;
    float lastOfferingTime;
    double multiplier;

    Enorma(double multiplier) {
        this.multiplier = multiplier;
        satisfaction = 1;
        // Register stuff
        DroppedInLavaCallback.EVENT.register(this::evaluateOffering);
    }

    @Override
    public String getName() {
        return GOD_NAME;
    }

    @Override
    public Text getFormattedName() {
        return GOD_NAME_FORMATED;
    }

    @Override
    public float getSatisfaction() {
        return satisfaction;
    }

    @Override
    public void resetSatisfaction() {
        satisfaction = 1;
    }

    @Override
    public void decaySatisfaction() {
        if (satisfaction > minSatisfaction) {
            satisfaction -= satisfaction * 0.2;
            if (satisfaction < minSatisfaction) {
                satisfaction = 1;
            }
        }
    }

    @Override
    public float getLastOfferingTime() {
        return this.lastOfferingTime;
    }

    public ActionResult evaluateOffering(PlayerEntity player, BlockEntity block, ItemStack item) {
        var stack_value = getItemStackValue(item);
        var world = player.getWorld();
        var increase = calculateNewBorder(stack_value, world);
        Border.add(world, increase, 1000);
        return ActionResult.PASS;
    }

    private double calculateNewBorder(double value, World world) {
        return value / Border.get(world) * multiplier;
    }

    private double getItemStackValue(ItemStack item) {
        double result = 0;
        for (int i = 0; i < item.getCount(); i++) {
            var value = getItemValue(item.getItem());
            if (value != 0) {
                result += getItemValue(item.getItem()) / satisfaction;
                updateSatisfaction(value > 0);
            }
        }
        return result;
    }

    private void updateSatisfaction(boolean increase) {
        updateSatisfaction(increase, true);
    }

    private void updateSatisfaction(boolean increase, boolean notify) {
        if (increase) satisfaction *= 1.2;
        if (!increase) satisfaction *= 0.8;
        if (satisfaction > maxSatisfaction) satisfaction = maxSatisfaction;
        if (satisfaction < minSatisfaction) satisfaction = minSatisfaction;
        if (notify) {
            GodManager.sendOfferMessage(this, increase);
        }
    }

    private int getItemValue(Item item) {
        var itemName = item.getTranslationKey();
        var config = EnormaLoader.getConfig();
        return config.valueSet.getOrDefault(itemName, 0);
    }
}
