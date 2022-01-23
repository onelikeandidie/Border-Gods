package net.onelikeandidie.bordergods.gods;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.onelikeandidie.bordergods.util.Border;

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
        satisfaction -= 15;
        if (satisfaction < 1) {
            satisfaction = 1;
        }
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
        Border.add(world, increase, 1000);
        return ActionResult.PASS;
    }

    private double calculateNewBorder(double value, World world) {
        return value / Border.get(world) * multiplier;
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
            var value = getItemValue(item.getItem());
            if (value != 0) {
                result += getItemValue(item.getItem()) / satisfaction;
                if (updateSatisfaction) {
                    if (value > 0) {
                        satisfaction *= 1.1;
                    } else if (value < 0) {
                        satisfaction *= 0.9;
                    }
                    if (satisfaction > maxSatisfaction) {
                        satisfaction = maxSatisfaction;
                    } else if (satisfaction < minSatisfaction) {
                        satisfaction = minSatisfaction;
                    }
                }
            }
        }
        return result;
    }

    private int getItemValue(Item item) {
        var itemName = item.getTranslationKey();
        return switch (itemName) {
            // ENORMA LIKES SHINY
            case "item.minecraft.diamond" -> 128;
            case "item.minecraft.gold_ingot" -> 32;
            case "item.minecraft.emerald" -> 64;
            case "item.minecraft.amethyst_shard" -> 8;
            // HAHA YOU CAN'T BURN THIS ONE IN LAVA
            case "item.minecraft.netherite_ingot" -> 128;
            // ENORMA DOESN'T LIKE PLANTS
            case "block.minecraft.lily_pad" -> -64;
            case "block.minecraft.sugar_cane" -> -64;
            default -> 0;
        };
    }
}
