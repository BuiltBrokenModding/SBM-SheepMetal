package com.builtbroken.sheepmetal.content;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class TagSmeltingRecipe extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<FurnaceRecipe>
{
    //https://github.com/Tfarcenim/Unstable-Tools/blob/1.14.x/src/main/java/com/tfar/unstabletools/crafting/RecipeDivision.java

    @Override
    public FurnaceRecipe read(ResourceLocation recipeId, JsonObject json)
    {
        final String group = JSONUtils.getString(json, "group", "");
        final float experience = JSONUtils.getFloat(json, "experience", 0.0F);
        final int burnTime = JSONUtils.getInt(json, "cookingtime", 200);

        //Ingredents
        final JsonElement jsonelement = JSONUtils.isJsonArray(json, "ingredient")
                ? JSONUtils.getJsonArray(json, "ingredient")
                : JSONUtils.getJsonObject(json, "ingredient");
        final Ingredient ingredient = Ingredient.deserialize(jsonelement);

        //Result
        final String result = JSONUtils.getString(json, "result");
        ResourceLocation resourcelocation = new ResourceLocation(result);
        ItemStack itemstack = new ItemStack(Registry.ITEM.getValue(resourcelocation).
                orElseGet(() ->
                {
                    final Tag<Item> tag = new ItemTags.Wrapper(new ResourceLocation(result));
                    if (tag.getAllElements().size() > 0)
                    {
                        return tag.getAllElements().iterator().next();
                    }
                    return Items.AIR;
                })
        );

        return new FurnaceRecipe(recipeId, group, ingredient, itemstack, experience, burnTime);
    }

    @Override
    public FurnaceRecipe read(ResourceLocation recipeId, PacketBuffer buffer)
    {
        return IRecipeSerializer.SMELTING.read(recipeId, buffer);
    }

    @Override
    public void write(PacketBuffer buffer, FurnaceRecipe recipe)
    {
        IRecipeSerializer.SMELTING.write(buffer, recipe);
    }
}