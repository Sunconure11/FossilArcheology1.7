package fossilsarcheology.server.event;

import fossilsarcheology.server.item.FAItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.nio.Buffer;

public class FossilHelmetOverlayEvent {

    private static final ResourceLocation ANCIENT_HELMET = new ResourceLocation("fossil:textures/gui/ancienthelmetblur.png");
    private static final ResourceLocation SKULL_HELMET = new ResourceLocation("fossil:textures/gui/skullhelmetblur.png");
    private Minecraft mc;

    public FossilHelmetOverlayEvent() {
        super();
        this.mc = Minecraft.getMinecraft();
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderOverlay(RenderGameOverlayEvent event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.HELMET) {
            return;
        }

        if (event.isCancelable()) {
            return;
        }

        if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {
            if (Minecraft.getMinecraft().player.getItemStackFromSlot(EntityEquipmentSlot.HEAD) != ItemStack.EMPTY) {
                if (Minecraft.getMinecraft().player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() != null) {
                    if (Minecraft.getMinecraft().player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == FAItemRegistry.ANCIENT_HELMET) {
                        ScaledResolution res = new ScaledResolution(mc);
                        GlStateManager.disableDepth();
                        GlStateManager.depthMask(false);
                        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                        GlStateManager.disableAlpha();
                        this.mc.getTextureManager().bindTexture(ANCIENT_HELMET);
                        Tessellator tessellator = Tessellator.getInstance();
                        BufferBuilder bufferbuilder = tessellator.getBuffer();
                        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
                        bufferbuilder.pos(0.0D, (double)res.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
                        bufferbuilder.pos((double)res.getScaledWidth(), (double)res.getScaledHeight(), -90.0D).tex(1.0D, 1.0D).endVertex();
                        bufferbuilder.pos((double)res.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
                        bufferbuilder.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
                        tessellator.draw();
                        GlStateManager.depthMask(true);
                        GlStateManager.enableDepth();
                        GlStateManager.enableAlpha();
                        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    }
                }
            }
        if (Minecraft.getMinecraft().player.getItemStackFromSlot(EntityEquipmentSlot.HEAD) != ItemStack.EMPTY) {
            if (Minecraft.getMinecraft().player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() != null) {
                if (Minecraft.getMinecraft().player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == FAItemRegistry.SKULL_HELMET) {
                    ScaledResolution res = new ScaledResolution(mc);
                    GlStateManager.disableDepth();
                    GlStateManager.depthMask(false);
                    GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    GlStateManager.disableAlpha();
                    this.mc.getTextureManager().bindTexture(SKULL_HELMET);
                    Tessellator tessellator = Tessellator.getInstance();
                    BufferBuilder bufferbuilder = tessellator.getBuffer();
                    bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
                    bufferbuilder.pos(0.0D, (double)res.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
                    bufferbuilder.pos((double)res.getScaledWidth(), (double)res.getScaledHeight(), -90.0D).tex(1.0D, 1.0D).endVertex();
                    bufferbuilder.pos((double)res.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
                    bufferbuilder.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
                    tessellator.draw();
                    GlStateManager.depthMask(true);
                    GlStateManager.enableDepth();
                    GlStateManager.enableAlpha();
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                }
            }
        }
    }

    }

}
