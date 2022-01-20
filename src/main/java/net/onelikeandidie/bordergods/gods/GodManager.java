package net.onelikeandidie.bordergods.gods;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.onelikeandidie.bordergods.events.DroppedInLavaCallback;

import java.util.HashMap;
import java.util.Map;

public class GodManager {
    static Map<String, IGod> gods;

    public static void initialize() {
        gods = new HashMap<>();
        gods.put("Enorma", new Enorma());
        // Listen for god offerings
        DroppedInLavaCallback.EVENT.register(GodManager::offeringGiven);
    }

    private static ActionResult offeringGiven(PlayerEntity playerEntity, BlockEntity blockEntity, ItemStack itemStack) {
        var world = playerEntity.getWorld();
        return ActionResult.PASS;
    }
}
