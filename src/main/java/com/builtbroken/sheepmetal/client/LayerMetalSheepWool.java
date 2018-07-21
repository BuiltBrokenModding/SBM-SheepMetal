package com.builtbroken.sheepmetal.client;

import com.builtbroken.sheepmetal.SheepMetal;
import com.builtbroken.sheepmetal.entity.EntityMetalSheep;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;

@SideOnly(Side.CLIENT)
public class LayerMetalSheepWool implements LayerRenderer<EntityMetalSheep>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(SheepMetal.DOMAIN, "textures/entity/metal_sheep_fur.png");

    private final RenderMetalSheep sheepRenderer;
    private final ModelMetalSheep1 sheepModel = new ModelMetalSheep1();

    public LayerMetalSheepWool(RenderMetalSheep sheepRendererIn)
    {
        this.sheepRenderer = sheepRendererIn;
    }

    @Override
    public void doRenderLayer(EntityMetalSheep entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (!entity.getSheared() && !entity.isInvisible())
        {
            //Texture
            this.sheepRenderer.bindTexture(TEXTURE);

            //Color
            Color color = entity.getWoolType().getWoolColor();
            GlStateManager.color(color.getRed() / 255f,  color.getGreen() / 255f, color.getBlue() / 255f);

            //Model setup
            this.sheepModel.setModelAttributes(this.sheepRenderer.getMainModel());
            this.sheepModel.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);

            //Rendering
            this.sheepModel.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    @Override
    public boolean shouldCombineTextures()
    {
        return true;
    }
}