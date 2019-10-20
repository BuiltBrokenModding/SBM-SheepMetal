package com.builtbroken.sheepmetal.entity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.builtbroken.sheepmetal.SheepMetal;
import com.builtbroken.sheepmetal.data.SheepParent;
import com.builtbroken.sheepmetal.data.SheepTypes;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 7/21/2018.
 */
public class EntityMetalSheep extends EntityAnimal implements IShearable
{
    private static final DataParameter<Byte> WOOL_TYPE = EntityDataManager.<Byte>createKey(EntityMetalSheep.class, DataSerializers.BYTE);
    private static final DataParameter<Boolean> IS_SHEARED = EntityDataManager.<Boolean>createKey(EntityMetalSheep.class, DataSerializers.BOOLEAN);

    public static final String NBT_SHEARED = "sheared";
    public static final String NBT_WOOL_TYPE = "wool_type";
    public static final String NBT_PARENT = "parent";

    private SheepParent parents;

    /**
     * Used to control movement as well as wool regrowth. Set to 40 on handleHealthUpdate and counts down with each
     * tick.
     */
    private int sheepTimer;
    private EntityAIEatGrass entityAIEatGrass;

    public EntityMetalSheep(World worldIn)
    {
        super(worldIn);
        this.setSize(0.9F, 1.3F);
    }

    @Override
    protected void initEntityAI()
    {
        this.entityAIEatGrass = new EntityAIEatGrass(this);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.25D));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        //TODO Replace wheat with metal ingot
        this.tasks.addTask(3, new EntityAITempt(this, 1.1D, Items.WHEAT, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
        this.tasks.addTask(5, this.entityAIEatGrass); //TODO disable eat grass?
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }

    @Override
    protected void updateAITasks()
    {
        this.sheepTimer = this.entityAIEatGrass.getEatingGrassTimer();
        super.updateAITasks();
    }

    @Override
    public void onLivingUpdate()
    {
        if (this.world.isRemote)
        {
            this.sheepTimer = Math.max(0, this.sheepTimer - 1);
        }

        super.onLivingUpdate();
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(WOOL_TYPE, (byte) 0);
        this.dataManager.register(IS_SHEARED, false);
    }

    @Override
    public String getName()
    {
        if (this.hasCustomName())
        {
            return this.getCustomNameTag();
        }
        else
        {
            String key = "entity." + SheepMetal.PREFIX + "sheep.wool." + getWoolType().name + ".name";
            key = I18n.translateToLocal(key);

            if (!key.isEmpty() && !key.contains("."))
            {
                return key;
            }

            String s = EntityList.getEntityString(this);

            if (s == null)
            {
                s = "generic";
            }

            return I18n.translateToLocal("entity." + s + ".name");
        }
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable()
    {
        if (this.getSheared())
        {
            return LootTableList.ENTITIES_SHEEP;
        }
        return getWoolType().entityDropTable;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        if (id == 10)
        {
            this.sheepTimer = 40;
        }
        else
        {
            super.handleStatusUpdate(id);
        }
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        return super.processInteract(player, hand);
    }

    @SideOnly(Side.CLIENT)
    public float getHeadRotationPointY(float p_70894_1_)
    {
        if (this.sheepTimer <= 0)
        {
            return 0.0F;
        }
        else if (this.sheepTimer >= 4 && this.sheepTimer <= 36)
        {
            return 1.0F;
        }
        else
        {
            return this.sheepTimer < 4 ? (this.sheepTimer - p_70894_1_) / 4.0F : -(this.sheepTimer - 40 - p_70894_1_) / 4.0F;
        }
    }

    @SideOnly(Side.CLIENT)
    public float getHeadRotationAngleX(float p_70890_1_)
    {
        if (this.sheepTimer > 4 && this.sheepTimer <= 36)
        {
            float f = (this.sheepTimer - 4 - p_70890_1_) / 32.0F;
            return ((float) Math.PI / 5F) + ((float) Math.PI * 7F / 100F) * MathHelper.sin(f * 28.7F);
        }
        else
        {
            return this.sheepTimer > 0 ? ((float) Math.PI / 5F) : this.rotationPitch * 0.017453292F;
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setBoolean(NBT_SHEARED, this.getSheared());
        compound.setByte(NBT_WOOL_TYPE, (byte) this.getWoolType().ordinal());
        compound.setTag(NBT_PARENT, getParent().serializeNBT());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setSheared(compound.getBoolean(NBT_SHEARED));
        this.setWoolType(SheepTypes.get(compound.getByte(NBT_WOOL_TYPE)));
        if (compound.hasKey(NBT_PARENT))
        {
            parents = SheepParent.load(compound.getCompoundTag(NBT_PARENT));
        }
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_SHEEP_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_SHEEP_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_SHEEP_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(SoundEvents.ENTITY_SHEEP_STEP, 0.15F, 1.0F);
    }

    /**
     * Gets the wool color of this sheep.
     */
    public SheepTypes getWoolType()
    {
        return SheepTypes.get(this.dataManager.get(WOOL_TYPE).byteValue());
    }

    /**
     * Sets the wool color of this sheep
     */
    public void setWoolType(SheepTypes color)
    {
        this.dataManager.set(WOOL_TYPE, (byte) color.ordinal());
    }

    /**
     * returns true if a sheeps wool has been sheared
     */
    public boolean getSheared()
    {
        return this.dataManager.get(IS_SHEARED);
    }

    /**
     * make a sheep sheared if set to true
     */
    public void setSheared(boolean sheared)
    {
        this.dataManager.set(IS_SHEARED, sheared);
    }


    @Override
    public EntityMetalSheep createChild(EntityAgeable ageable)
    {
        //Parent B, this entity is parent A
        final EntityMetalSheep mate = (EntityMetalSheep) ageable;

        //Create child
        final EntityMetalSheep child = new EntityMetalSheep(this.world);

        //Get metal outcome
        child.parents = SheepParent.merge(getParent(), mate.getParent());
        child.setWoolType(child.parents.getRandom(world.rand));
        return child;
    }

    public SheepParent getParent()
    {
        if (parents == null)
        {
            parents = SheepParent.buildRandomParent(4); //TODO config
        }
        return parents;
    }

    /**
     * This function applies the benefits of growing back wool and faster growing up to the acting entity. (This
     * function is used in the AIEatGrass)
     */
    @Override
    public void eatGrassBonus()
    {
        this.setSheared(false);

        if (this.isChild())
        {
            this.addGrowth(60);
        }
    }

    @Override
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setWoolType(SheepTypes.random());
        return livingdata;
    }

    @Override
    public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos)
    {
        return !this.getSheared() && !this.isChild(); //TODO check for support shears
    }

    @Override
    public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
    {
        //Update shear state
        this.setSheared(true);

        //Get random drop count
        int dropCount = 1 + this.rand.nextInt(3);

        //Collect items
        List<ItemStack> ret = new ArrayList<>();
        for (int i = 0; i < dropCount; ++i)
        {
            ret.add(this.getWoolType().getWoolItem());
        }

        //Play audio
        this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);


        return ret;
    }

    @Override
    public float getEyeHeight()
    {
        return 0.95F * this.height;
    }
}
