package net.pulga22.alreadynotified.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.pulga22.alreadynotified.util.IPlayerNotifications;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements IPlayerNotifications {

    @Unique
    private boolean newNotificationPending = false;

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void readData(NbtCompound nbt, CallbackInfo ci){
        this.newNotificationPending = nbt.getBoolean("notificationPending");
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    public void writeData(NbtCompound nbt, CallbackInfo ci){
        nbt.putBoolean("notificationPending", this.newNotificationPending);
    }

    @Override
    public void alreadynotified$markNewNotification() {
        this.newNotificationPending = true;
    }

    @Override
    public void alreadynotified$notificationRead() {
        this.newNotificationPending = false;
    }

    @Override
    public boolean alreadynotified$isPendingReadNewNotification() {
        return this.newNotificationPending;
    }

}