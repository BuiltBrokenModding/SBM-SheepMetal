package com.builtbroken.sheepmetal.client;

import com.builtbroken.sheepmetal.entity.EntityMetalSheep;

import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelMetalSheep1 extends QuadrupedModel<EntityMetalSheep>
{
    private float headModelRotationAngleX;

    public ModelMetalSheep1()
    {
        super(12, 0.0F);
        this.headModel = new RendererModel(this, 0, 0);
        this.headModel.addBox(-3.0F, -4.0F, -4.0F, 6, 6, 6, 0.6F);
        this.headModel.setRotationPoint(0.0F, 6.0F, -8.0F);
        this.body = new RendererModel(this, 28, 8);
        this.body.addBox(-4.0F, -10.0F, -7.0F, 8, 16, 6, 1.75F);
        this.body.setRotationPoint(0.0F, 5.0F, 2.0F);
        this.legBackRight = new RendererModel(this, 0, 16);
        this.legBackRight.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.5F);
        this.legBackRight.setRotationPoint(-3.0F, 12.0F, 7.0F);
        this.legBackLeft = new RendererModel(this, 0, 16);
        this.legBackLeft.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.5F);
        this.legBackLeft.setRotationPoint(3.0F, 12.0F, 7.0F);
        this.legFrontRight = new RendererModel(this, 0, 16);
        this.legFrontRight.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.5F);
        this.legFrontRight.setRotationPoint(-3.0F, 12.0F, -5.0F);
        this.legFrontLeft = new RendererModel(this, 0, 16);
        this.legFrontLeft.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.5F);
        this.legFrontLeft.setRotationPoint(3.0F, 12.0F, -5.0F);
    }

    @Override
    public void setLivingAnimations(EntityMetalSheep entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
        this.headModel.rotationPointY = 6.0F + entitylivingbaseIn.getHeadRotationPointY(partialTickTime) * 9.0F;
        this.headModelRotationAngleX = entitylivingbaseIn.getHeadRotationAngleX(partialTickTime);
    }

    @Override
    public void setRotationAngles(EntityMetalSheep entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netheadModelYaw, float headModelPitch, float scaleFactor)
    {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netheadModelYaw, headModelPitch, scaleFactor);
        this.headModel.rotateAngleX = this.headModelRotationAngleX;
    }
}