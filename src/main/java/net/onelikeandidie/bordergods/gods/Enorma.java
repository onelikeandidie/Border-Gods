package net.onelikeandidie.bordergods.gods;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.onelikeandidie.bordergods.util.Border;

public class Enorma implements IGod {
    static final String GOD_NAME = "Enorma";
    static final Text GOD_NAME_FORMATED = Text.of(GOD_NAME);
    float satisfaction;
    static final float maxSatisfaction = 20;
    float lastOfferingTime;

    Enorma() {
        satisfaction = 0;
    }

    @Override
    public String getName() {
        return GOD_NAME;
    }

    @Override
    public float getSatisfaction() {
        return satisfaction;
    }

    @Override
    public float getLastOfferingTime() {
        return this.lastOfferingTime;
    }

    @Override
    public ActionResult evaluateOffering(PlayerEntity player, BlockEntity block, ItemStack item) {
        var stack_value = getItemStackValue(item, true);
        if (stack_value > 0) {
            sendOfferMessage(player, item, true);
        } else if (stack_value < 0){
            sendOfferMessage(player, item, false);
        }
        var world = player.getWorld();
        var increase = calculateNewBorder(stack_value, world);
        player.sendMessage(Text.of(Double.toString(increase)), false);
        Border.add(world, increase, 120);
        return ActionResult.PASS;
    }

    private double calculateNewBorder(double value, World world) {
        return value / Border.get(world) * 1;
    }

    private void sendOfferMessage(PlayerEntity player, ItemStack item, boolean good) {
        var item_name = item.getItem().getName();
        var item_count = item.getCount();
        net.minecraft.text.MutableText message;
        if (good) {
            message = Text.of("You just offered ").copy();
        } else {
            message = Text.of("You just angered with ").copy();
        }
        message.append(Integer.toString(item_count, 10));
        message.append(" ");
        message.append(item_name);
        message.append(" to ");
        message.append(GOD_NAME);
        player.sendMessage(message, true);
    }

    private double getItemStackValue(ItemStack item, boolean updateSatisfaction) {
        double result = 0;
        for (int i = 0; i < item.getCount(); i++) {
            result += getItemValue(item.getItem()) / satisfaction;
            if (updateSatisfaction) {
                if (satisfaction > maxSatisfaction) {
                    satisfaction = maxSatisfaction;
                } else {
                    satisfaction *= 1.2;
                }
            }
        }
        return result;
    }

    private int getItemValue(Item item) {
        var itemName = item.getTranslationKey();
        return switch (itemName) {
            case "item.minecraft.diamond" -> 64;
            case "item.minecraft.netherite_ingot" -> 128;
            case "block.minecraft.lily_pad" -> -64;
            default -> 0;
        };
    }
}
