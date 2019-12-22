package com.builtbroken.sheepmetal.client;

import com.builtbroken.sheepmetal.entity.EntityMetalSheep;

import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelMetalSheepWool extends QuadrupedModel<EntityMetalSheep>
{
    private float headRotationAngleX;

    public ModelMetalSheepWool()
    {
        super(12, 0.0F, false, 8.0F, 4.0F, 2.0F, 2.0F, 24);
        this.headModel = new ModelRenderer(this, 0, 0);
        this.headModel.func_228301_a_(-3.0F, -4.0F, -4.0F, 6.0F, 6.0F, 6.0F, 0.6F);
        this.headModel.setRotationPoint(0.0F, 6.0F, -8.0F);
        this.body = new ModelRenderer(this, 28, 8);
        this.body.func_228301_a_(-4.0F, -10.0F, -7.0F, 8.0F, 16.0F, 6.0F, 1.75F);
        this.body.setRotationPoint(0.0F, 5.0F, 2.0F);
        this.legBackRight = new ModelRenderer(this, 0, 16);
        this.legBackRight.func_228301_a_(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.5F);
        this.legBackRight.setRotationPoint(-3.0F, 12.0F, 7.0F);
        this.legBackLeft = new ModelRenderer(this, 0, 16);
        this.legBackLeft.func_228301_a_(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.5F);
        this.legBackLeft.setRotationPoint(3.0F, 12.0F, 7.0F);
        this.legFrontRight = new ModelRenderer(this, 0, 16);
        this.legFrontRight.func_228301_a_(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.5F);
        this.legFrontRight.setRotationPoint(-3.0F, 12.0F, -5.0F);
        this.legFrontLeft = new ModelRenderer(this, 0, 16);
        this.legFrontLeft.func_228301_a_(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.5F);
        this.legFrontLeft.setRotationPoint(3.0F, 12.0F, -5.0F);
    }

    @Override
    public void setLivingAnimations(EntityMetalSheep entityIn, float limbSwing, float limbSwingAmount, float partialTick)
    {
        super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
        this.headModel.rotationPointY = 6.0F + entityIn.getHeadRotationPointY(partialTick) * 9.0F;
        this.headRotationAngleX = entityIn.getHeadRotationAngleX(partialTick);
    }

    @Override
    public void func_225597_a_(EntityMetalSheep p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_)
    {
        super.func_225597_a_(p_225597_1_, p_225597_2_, p_225597_3_, p_225597_4_, p_225597_5_, p_225597_6_);
        this.headModel.rotateAngleX = this.headRotationAngleX;
    }
}