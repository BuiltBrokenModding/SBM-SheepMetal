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
        this.field_78148_b = new RendererModel(this, 28, 8);
        this.field_78148_b.addBox(-4.0F, -10.0F, -7.0F, 8, 16, 6, 1.75F);
        this.field_78148_b.setRotationPoint(0.0F, 5.0F, 2.0F);
        this.field_78149_c = new RendererModel(this, 0, 16);
        this.field_78149_c.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.5F);
        this.field_78149_c.setRotationPoint(-3.0F, 12.0F, 7.0F);
        this.field_78146_d = new RendererModel(this, 0, 16);
        this.field_78146_d.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.5F);
        this.field_78146_d.setRotationPoint(3.0F, 12.0F, 7.0F);
        this.field_78147_e = new RendererModel(this, 0, 16);
        this.field_78147_e.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.5F);
        this.field_78147_e.setRotationPoint(-3.0F, 12.0F, -5.0F);
        this.field_78144_f = new RendererModel(this, 0, 16);
        this.field_78144_f.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.5F);
        this.field_78144_f.setRotationPoint(3.0F, 12.0F, -5.0F);
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