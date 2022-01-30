package net.onelikeandidie.bordergods.gods;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.onelikeandidie.bordergods.events.VillagerLeveledUpCallback;
import net.onelikeandidie.bordergods.util.Border;
import net.onelikeandidie.bordergods.util.config.gods.AnullisLoader;
import net.onelikeandidie.bordergods.util.config.gods.MerchetLoader;

public class Merchet implements IGod {
    static final String GOD_NAME = "Merchet";
    static final Text GOD_NAME_FORMATED = Text.of(GOD_NAME).copy().setStyle(Style.EMPTY.withColor(61101));
    float satisfaction;
    static final float maxSatisfaction = 20;
    static final float minSatisfaction = 1;
    float lastOfferingTime;
    double multiplier;

    Merchet(double multiplier) {
        this.multiplier = multiplier;
        satisfaction = 1;
        // Register stuff
        VillagerLeveledUpCallback.EVENT.register(this::evaluateOffering);
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

    public ActionResult evaluateOffering(VillagerEntity entity) {
        var config = MerchetLoader.getConfig();
        var world = entity.getWorld();
        var increase = config.baseIncrease;
        if (increase != 0) updateSatisfaction(increase > 0);
        if (satisfaction < maxSatisfaction) {
            Border.add(world, increase, 1000);
        }
        return ActionResult.PASS;
    }

    private void updateSatisfaction(boolean increase) {
        updateSatisfaction(increase, true);
    }

    private void updateSatisfaction(boolean increase, boolean notify) {
        if (increase) satisfaction += 2.4;
        if (!increase) satisfaction -= 1.2;
        if (satisfaction > maxSatisfaction) satisfaction = maxSatisfaction;
        if (satisfaction < minSatisfaction) satisfaction = minSatisfaction;
        if (notify) {
            GodManager.sendOfferMessage(this, increase);
        }
    }

    private double calculateNewBorder(double value, World world) {
        return value * multiplier;
    }
}
