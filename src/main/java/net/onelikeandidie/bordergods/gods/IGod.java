package net.onelikeandidie.bordergods.gods;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public interface IGod {
    String getName();
    Text getFormattedName();
    float getSatisfaction();
    void resetSatisfaction();
    void decaySatisfaction();
    float getLastOfferingTime();
}
