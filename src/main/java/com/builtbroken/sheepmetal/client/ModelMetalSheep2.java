package com.builtbroken.sheepmetal.client;

import com.builtbroken.sheepmetal.entity.EntityMetalSheep;

import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelMetalSheep2 extends QuadrupedModel<EntityMetalSheep>
{
    private float headRotationAngleX;

    public ModelMetalSheep2()
    {
        super(12, 0.0F);
        this.headModel = new RendererModel(this, 0, 0);
        this.headModel.addBox(-3.0F, -4.0F, -6.0F, 6, 6, 8, 0.0F);
        this.headModel.setRotationPoint(0.0F, 6.0F, -8.0F);
        this.body = new RendererModel(this, 28, 8);
        this.body.addBox(-4.0F, -10.0F, -7.0F, 8, 16, 6, 0.0F);
        this.body.setRotationPoint(0.0F, 5.0F, 2.0F);
    }

    @Override
    public void setLivingAnimations(EntityMetalSheep entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
        this.headModel.rotationPointY = 6.0F + entitylivingbaseIn.getHeadRotationPointY(partialTickTime) * 9.0F;
        this.headRotationAngleX = entitylivingbaseIn.getHeadRotationAngleX(partialTickTime);
    }

    @Override
    public void setRotationAngles(EntityMetalSheep entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
    {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        this.headModel.rotateAngleX = this.headRotationAngleX;
    }
}