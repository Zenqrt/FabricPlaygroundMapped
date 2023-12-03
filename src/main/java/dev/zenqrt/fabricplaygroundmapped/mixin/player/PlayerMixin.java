package dev.zenqrt.fabricplaygroundmapped.mixin.player;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Unique
    private boolean keepInventory;

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void addAdditionalSaveData(CompoundTag compoundTag, CallbackInfo callbackInfo) {
        compoundTag.putBoolean("KeepInventory", keepInventory);

        for (int i = 0; i < 1000; i++) {
            compoundTag.putInt("Hello" + i, i);
        }
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void readAdditionalSaveData(CompoundTag compoundTag, CallbackInfo callbackInfo) {
        keepInventory = compoundTag.getBoolean("KeepInventory");

        for (int i = 0; i < 1000; i++) {
            compoundTag.getInt("Hello" + i);
        }
    }

    public boolean isKeepInventory() {
        return keepInventory;
    }

    public void setKeepInventory(boolean keepInventory) {
        this.keepInventory = keepInventory;
    }
}
