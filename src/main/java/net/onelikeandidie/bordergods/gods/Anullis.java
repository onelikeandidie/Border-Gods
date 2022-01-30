package net.onelikeandidie.bordergods.gods;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.onelikeandidie.bordergods.events.DroppedInLavaCallback;
import net.onelikeandidie.bordergods.events.KilledByLavaCallback;
import net.onelikeandidie.bordergods.util.Border;
import net.onelikeandidie.bordergods.util.config.gods.AnullisLoader;

public class Anullis implements IGod {
    static final String GOD_NAME = "Anullis";
    static final Text GOD_NAME_FORMATED = Text.of(GOD_NAME).copy().setStyle(Style.EMPTY.withColor(255191160));
    float satisfaction;
    static final float maxSatisfaction = 20;
    static final float minSatisfaction = 1;
    float lastOfferingTime;
    double multiplier;

    Anullis(double multiplier) {
        this.multiplier = multiplier;
        satisfaction = 1;
        // Register stuff
        KilledByLavaCallback.EVENT.register(this::evaluateOffering);
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

    public ActionResult evaluateOffering(LivingEntity entity) {
        var entityClass =  entity.getClass();
        var config = AnullisLoader.getConfig();
        try {
            var entityType = entityClass.getTypeName();
            var value = config.valueSet.get(entityType);
            var calc = value / satisfaction;
            if (value != 0) updateSatisfaction(value > 0);
            var world = entity.getWorld();
            var increase = calculateNewBorder(calc, world);
            Border.add(world, increase, 1000);
        } catch (NullPointerException ignored) {}
        return ActionResult.PASS;
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

    private double calculateNewBorder(double value, World world) {
        return value / Border.get(world) * multiplier;
    }
}
