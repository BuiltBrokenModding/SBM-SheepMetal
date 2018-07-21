package com.builtbroken.sheepmetal.client;

import com.builtbroken.sheepmetal.SheepMetal;
import com.builtbroken.sheepmetal.entity.EntityMetalSheep;
import net.minecraft.client.model.ModelSheep2;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 7/21/2018.
 */
public class RenderMetalSheep extends RenderLiving<EntityMetalSheep>
{
    private static final ResourceLocation SHEARED_SHEEP_TEXTURES = new ResourceLocation(SheepMetal.DOMAIN,"textures/entity/metal_sheep.png");

    public RenderMetalSheep(RenderManager renderManager)
    {
        super(renderManager, new ModelSheep2(), 0.7F);
        //this.addLayer(new LayerSheepWool(this));
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityMetalSheep entity)
    {
        return SHEARED_SHEEP_TEXTURES;
    }
}
