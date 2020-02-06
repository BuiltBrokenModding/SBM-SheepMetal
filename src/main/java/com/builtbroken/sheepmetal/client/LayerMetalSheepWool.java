package com.builtbroken.sheepmetal.client;

import java.awt.Color;

import com.builtbroken.sheepmetal.SheepMetal;
import com.builtbroken.sheepmetal.entity.EntityMetalSheep;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LayerMetalSheepWool extends LayerRenderer<EntityMetalSheep, ModelMetalSheep>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(SheepMetal.DOMAIN, "textures/entity/metal_sheep_fur.png");

    private final ModelMetalSheepWool sheepModel = new ModelMetalSheepWool();

    public LayerMetalSheepWool(IEntityRenderer<EntityMetalSheep, ModelMetalSheep> renderer)
    {
        super(renderer);
    }

    @Override
    public void render(MatrixStack stack, IRenderTypeBuffer buffer, int p_225628_3_, EntityMetalSheep entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
    {
        if (!entity.getSheared() && !entity.isInvisible())
        {
            //Color
            Color color = entity.getWoolType().getWoolColor();

            //Rendering
            renderCopyCutoutModel(this.getEntityModel(), this.sheepModel, TEXTURE, stack, buffer, p_225628_3_, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, color.getRed() / 255f,  color.getGreen() / 255f, color.getBlue() / 255f);
        }
    }
}