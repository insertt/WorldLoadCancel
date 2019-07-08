package dev.insertt.wlc.mixin;

import net.minecraft.client.gui.screen.LevelLoadingScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.NarratorManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelLoadingScreen.class)
public abstract class LevelLoadingScreenMixin extends Screen {

    private boolean buttonAdded = false;

    protected LevelLoadingScreenMixin() {
        super(NarratorManager.EMPTY);
    }

    @Inject(method = "render(IIF)V", at = @At(value = "RETURN"))
    public void render(int a, int b, float c, CallbackInfo info) {
        if (! this.buttonAdded) {
            super.addButton(new ButtonWidget(this.width / 2 - 100, this.height / 2 + 100, 200, 20, "Cancel", action -> {
                this.minecraft.getServer().stop(true);
                this.minecraft.getServer().shutdown();
                this.minecraft.openScreen(new SelectWorldScreen(new TitleScreen()));
            }));

            this.buttonAdded = true;
        }

        super.render(a, b, c);
    }
}
