package fossilsarcheology.client.render.tile;

import fossilsarcheology.client.model.ModelEmbryoGeneric;
import fossilsarcheology.client.model.ModelEmbryoPlant;
import fossilsarcheology.server.block.entity.TileEntityCultivate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TileEntityCultivateRender extends TileEntitySpecialRenderer<TileEntityCultivate> {
    public static final ResourceLocation textureEmbryoBasic = new ResourceLocation("fossil:textures/blocks/cultivate/embryo_generic.png");
    private static final ResourceLocation textureEmbryoLimbless = new ResourceLocation("fossil:textures/blocks/cultivate/embryo_legless.png");
    private static final ResourceLocation textureEmbryoPlant = new ResourceLocation("fossil:textures/blocks/cultivate/embryo_plant.png");
    private static final ResourceLocation textureEmbryoSpore = new ResourceLocation("fossil:textures/blocks/cultivate/embryo_spore.png");
    private ModelEmbryoGeneric model;
    private ModelEmbryoPlant modelPlant;

    public TileEntityCultivateRender() {
        this.model = new ModelEmbryoGeneric();
        modelPlant = new ModelEmbryoPlant();

    }

    public void renderCultureVatAt(TileEntityCultivate tileentity, double x, double y, double z, float partialTicks) {

        float rot = Minecraft.getMinecraft().player.ticksExisted;
        float bob = (float) (Math.sin(Minecraft.getMinecraft().player.ticksExisted * 0.1F) * 1 * 0.05F - 1 * 0.05F);

        GL11.glPushMatrix();
        GL11.glTranslated((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glRotatef(180, 0F, 0F, 1F);
        GL11.glPushMatrix();
        if (tileentity.isActive) {
            if (tileentity.getDNAType() == 1) {
                GL11.glPushMatrix();
                GL11.glTranslatef(0, 0.5F + bob, 0);
                GL11.glPushMatrix();
                GL11.glScalef(0.5F, 0.5F, 0.5F);
                GL11.glRotatef(rot, 0, 9, 0);
                this.bindTexture(textureEmbryoLimbless);
                model.render(0.0625F);
                GL11.glPopMatrix();
                GL11.glPopMatrix();
            } else if (tileentity.getDNAType() == 2) {
                GL11.glPushMatrix();
                GL11.glTranslatef(0, 0.5F + bob, 0);
                GL11.glPushMatrix();
                GL11.glScalef(0.5F, 0.5F, 0.5F);
                GL11.glRotatef(rot, 0, 9, 0);
                this.bindTexture(textureEmbryoPlant);
                modelPlant.render(0.0625F);
                GL11.glPopMatrix();
                GL11.glPopMatrix();
            } else {
                GL11.glPushMatrix();
                GL11.glTranslatef(0, 0.5F + bob, 0);
                GL11.glPushMatrix();
                GL11.glScalef(0.5F, 0.5F, 0.5F);
                GL11.glRotatef(rot, 0, 9, 0);
                this.bindTexture(textureEmbryoBasic);
                model.render(0.0625F);
                GL11.glPopMatrix();
                GL11.glPopMatrix();

            }

        }
        GL11.glPopMatrix();
        GL11.glPopMatrix();

    }

    @Override
    public void render(TileEntityCultivate tileentity, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        this.renderCultureVatAt(tileentity, x, y, z, partialTicks);
    }
}

