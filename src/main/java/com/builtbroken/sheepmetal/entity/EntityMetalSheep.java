package com.builtbroken.sheepmetal.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.builtbroken.sheepmetal.SheepMetal;
import com.builtbroken.sheepmetal.SheepTypes;

import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.network.NetworkHooks;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 7/21/2018.
 */
public class EntityMetalSheep extends AnimalEntity implements IShearable
{
    private static final DataParameter<Byte> WOOL_TYPE = EntityDataManager.<Byte>createKey(EntityMetalSheep.class, DataSerializers.BYTE);
    private static final DataParameter<Boolean> IS_SHEARED = EntityDataManager.<Boolean>createKey(EntityMetalSheep.class, DataSerializers.BOOLEAN);
    public static final String NBT_SHEARED = "sheared";
    public static final String NBT_WOOL_TYPE = "wool_type";

    /**
     * Used to control movement as well as wool regrowth. Set to 40 on handleHealthUpdate and counts down with each
     * tick.
     */
    private int sheepTimer;
    private EatGrassGoal entityAIEatGrass;

    public EntityMetalSheep(EntityType<EntityMetalSheep> type, World worldIn)
    {
        super(type, worldIn);
    }

    public EntityMetalSheep(World worldIn)
    {
        this(SheepMetal.ENTITY_TYPE_METAL_SHEEP, worldIn);
    }

    @Override
    protected void registerGoals()
    {
        this.entityAIEatGrass = new EatGrassGoal(this);
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        //TODO Replace wheat with metal ingot
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, Ingredient.fromItems(Items.WHEAT), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, this.entityAIEatGrass); //TODO disable eat grass?
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    @Override
    protected void updateAITasks()
    {
        this.sheepTimer = this.entityAIEatGrass.getEatingGrassTimer();
        super.updateAITasks();
    }

    @Override
    public void livingTick()
    {
        if (this.world.isRemote)
        {
            this.sheepTimer = Math.max(0, this.sheepTimer - 1);
        }

        super.livingTick();
    }

    @Override
    protected void registerAttributes()
    {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
    }

    @Override
    protected void registerData()
    {
        super.registerData();
        this.dataManager.register(WOOL_TYPE, (byte) 0);
        this.dataManager.register(IS_SHEARED, false);
    }

    @Override
    public ITextComponent getName()
    {
        if (this.hasCustomName())
        {
            return this.getCustomName();
        }
        else
        {
            ITextComponent key = new TranslationTextComponent("entity." + SheepMetal.PREFIX + "sheep.wool." + getWoolType().name + ".name");
            String translated = key.getFormattedText();

            if (!translated.isEmpty() && !translated.contains("."))
            {
                return key;
            }

            String s = SheepMetal.ENTITY_TYPE_METAL_SHEEP.getTranslationKey();

            if (s == null)
            {
                s = "entity.generic.name";
            }

            return new TranslationTextComponent(s);
        }
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable()
    {
        if (this.getSheared())
        {
            return EntityType.SHEEP.getLootTable();
        }
        return getWoolType().entityDropTable;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
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
    public boolean processInteract(PlayerEntity player, Hand hand)
    {
        return super.processInteract(player, hand);
    }

    @OnlyIn(Dist.CLIENT)
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

    @OnlyIn(Dist.CLIENT)
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
    public void writeAdditional(CompoundNBT compound)
    {
        super.writeAdditional(compound);
        compound.putBoolean(NBT_SHEARED, this.getSheared());
        compound.putByte(NBT_WOOL_TYPE, (byte) this.getWoolType().ordinal());
    }

    @Override
    public void readAdditional(CompoundNBT compound)
    {
        super.readAdditional(compound);
        this.setSheared(compound.getBoolean(NBT_SHEARED));
        this.setWoolType(SheepTypes.get(compound.getByte(NBT_WOOL_TYPE)));
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
    protected void playStepSound(BlockPos pos, BlockState blockIn)
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

    /**
     * Chooses a "vanilla" sheep color based on the provided random.
     */
    public static SheepTypes getRandomSheepColor(Random random)
    {
        return SheepTypes.get(random.nextInt(SheepTypes.values().length - 1));
    }

    @Override
    public EntityMetalSheep createChild(AgeableEntity ageable)
    {
        EntityMetalSheep EntityMetalSheep = (EntityMetalSheep) ageable;
        EntityMetalSheep EntityMetalSheep1 = SheepMetal.ENTITY_TYPE_METAL_SHEEP.create(this.world);
        EntityMetalSheep1.setWoolType(EntityMetalSheep.getWoolType()); //TODO add metal mixing
        return EntityMetalSheep1;
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
    public ILivingEntityData onInitialSpawn(IWorld world, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT nbt)
    {
        livingdata = super.onInitialSpawn(world, difficulty, spawnReason, livingdata, nbt);
        this.setWoolType(getRandomSheepColor(this.world.rand));
        return livingdata;
    }

    @Override
    public boolean isShearable(ItemStack item, IWorldReader world, BlockPos pos)
    {
        return !this.getSheared() && !this.isChild(); //TODO check for support shears
    }

    @Override
    public List<ItemStack> onSheared(ItemStack item, IWorld world, BlockPos pos, int fortune)
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
    public IPacket<?> createSpawnPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
