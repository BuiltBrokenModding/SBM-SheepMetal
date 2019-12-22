package com.builtbroken.sheepmetal.client;

import com.builtbroken.sheepmetal.SheepMetal;
import com.builtbroken.sheepmetal.entity.EntityMetalSheep;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 7/21/2018.
 */
public class RenderMetalSheep extends MobRenderer<EntityMetalSheep, ModelMetalSheep>
{
    private static final ResourceLocation SHEARED_SHEEP_TEXTURES = new ResourceLocation(SheepMetal.DOMAIN, "textures/entity/metal_sheep.png");

    public RenderMetalSheep(EntityRendererManager renderManager)
    {
        super(renderManager, new ModelMetalSheep(), 0.7F);
        this.addLayer(new LayerMetalSheepWool(this));
    }

    @Override
    public ResourceLocation getEntityTexture(EntityMetalSheep entity)
    {
        return SHEARED_SHEEP_TEXTURES;
    }
}
