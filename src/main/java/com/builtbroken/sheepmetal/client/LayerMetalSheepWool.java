package com.builtbroken.sheepmetal.client;

import com.builtbroken.sheepmetal.SheepMetal;
import com.builtbroken.sheepmetal.entity.EntityMetalSheep;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.Color;

@OnlyIn(Dist.CLIENT)
public class LayerMetalSheepWool extends LayerRenderer<EntityMetalSheep, ModelMetalSheep2>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(SheepMetal.DOMAIN, "textures/entity/metal_sheep_fur.png");

    private final RenderMetalSheep sheepRenderer;
    private final ModelMetalSheep1 sheepModel = new ModelMetalSheep1();

    public LayerMetalSheepWool(IEntityRenderer<EntityMetalSheep, ModelMetalSheep2> p_i50925_1_)
    {
        super(p_i50925_1_);

        sheepRenderer = (RenderMetalSheep)p_i50925_1_;
    }

    @Override
    public void render(EntityMetalSheep entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (!entity.getSheared() && !entity.isInvisible())
        {
            //Texture
            this.sheepRenderer.bindTexture(TEXTURE);

            //Color
            Color color = entity.getWoolType().getWoolColor();
            GlStateManager.color3f(color.getRed() / 255f,  color.getGreen() / 255f, color.getBlue() / 255f);

            //Model setup
            this.getEntityModel().setModelAttributes(this.sheepModel);
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