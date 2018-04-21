package fossilsarcheology.server.entity.prehistoric;

import fossilsarcheology.server.entity.ai.LargeSwimNodeProcessor;
import fossilsarcheology.server.entity.ai.PathNavigateAmphibious;
import net.ilexiconn.llibrary.server.animation.Animation;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.pathfinding.SwimNodeProcessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class EntityPrehistoricSwimming extends EntityPrehistoric {
    public boolean movesOnLand;
    protected boolean isAmphibious;
    public Animation FISH_ANIMATION;
    private boolean isLandNavigator;
    public float onLandProgress;

    public EntityPrehistoricSwimming(World world, PrehistoricEntityType type, double baseDamage, double maxDamage, double baseHealth, double maxHealth, double baseSpeed, double maxSpeed) {
        super(world, type, baseDamage, maxDamage, baseHealth, maxHealth, baseSpeed, maxSpeed);
        this.switchNavigator(true);
        this.hasBabyTexture = false;
    }

    private void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveHelper = new EntityMoveHelper(this);
            this.navigator = new PathNavigateAmphibious(this, world);
            this.isLandNavigator = true;
        } else {
            this.moveHelper = new EntityPrehistoricSwimming.SwimmingMoveHelper();
            this.navigator = new EntityPrehistoricSwimming.PathNavigateLargeSwimmer(this, world);
            this.isLandNavigator = false;
        }
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    public boolean isDirectPathBetweenPoints(Vec3d vec1, Vec3d vec2) {
        RayTraceResult movingobjectposition = this.world.rayTraceBlocks(vec1, new Vec3d(vec2.x, vec2.y + (double) this.height * 0.5D, vec2.z), false, true, false);
        return movingobjectposition == null || movingobjectposition.typeOfHit != RayTraceResult.Type.BLOCK;
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public abstract double swimSpeed();

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public boolean isOnLadder() {
        return false;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.isInWater() && this.isLandNavigator && !this.world.isRemote) {
            switchNavigator(false);
        }
        if (!this.isInWater() && !this.isLandNavigator && !this.world.isRemote) {
            switchNavigator(true);
        }
        this.renderYawOffset = this.rotationYaw;
        if ((this.isSitting() || this.isSleeping()) && this.isInWater()) {
            this.setSitting(false);
            this.setSleeping(false);
        }
    }

    @Override
    public boolean isInWater() {
        return super.isInWater() || this.isInsideOfMaterial(Material.WATER) || this.isInsideOfMaterial(Material.CORAL);
    }


    @Override
    public void travel(float strafe, float vertical, float forward) {
        float f4;
        if (this.isSitting()) {
            super.travel(0, 0, 0);
            return;
        }
        if (this.isBeingRidden() && this.canBeSteered()) {
            EntityLivingBase controller = (EntityLivingBase) this.getControllingPassenger();
            if (controller != null) {
                strafe = controller.moveStrafing * 0.5F;
                forward = controller.moveForward;
                if (forward <= 0.0F) {
                    forward *= 0.25F;
                }
                this.fallDistance = 0;
                if (this.isInWater()) {
                    this.moveRelative(strafe, vertical, forward, 1F);
                    f4 = 0.8F;
                    double d0 = this.swimSpeed();
                    if (!this.onGround) {
                        d0 *= 0.5F;
                    }
                    if (d0 > 0.0F) {
                        f4 += (0.54600006F - f4) * d0 / 3.0F;
                    }
                    this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
                    this.motionX *= (double) f4;
                    this.motionX *= 0.900000011920929D;
                    this.motionY *= 0.900000011920929D;
                    this.motionY *= (double) f4;
                    this.motionZ *= 0.900000011920929D;
                    this.motionZ *= (double) f4;
                    motionY += 0.01185D;
                } else {
                    forward = controller.moveForward * 0.25F;
                    strafe = controller.moveStrafing * 0.125F;

                    this.setAIMoveSpeed(2);
                    super.travel(strafe, vertical, forward);
                    return;
                }
                this.setAIMoveSpeed(2);
                super.travel(strafe, vertical = 0, forward);
                this.prevLimbSwingAmount = this.limbSwingAmount;
                double deltaX = this.posX - this.prevPosX;
                double deltaZ = this.posZ - this.prevPosZ;
                double deltaY = this.posY - this.prevPosY;
                float delta = MathHelper.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) * 4.0F;
                if (delta > 1.0F) {
                    delta = 1.0F;
                }
                this.limbSwingAmount += (delta - this.limbSwingAmount) * 0.4F;
                this.limbSwing += this.limbSwingAmount;
                return;
            }
        }
        if (this.isServerWorld()) {
            float f5;
            if (this.isInWater()) {
                this.moveRelative(strafe, vertical, forward, 0.1F);
                f4 = 0.8F;
                double d0 = this.swimSpeed();
                if (!this.onGround) {
                    d0 *= 0.5F;
                }
                if (d0 > 0.0F) {
                    f4 += (0.54600006F - f4) * d0 / 3.0F;
                }this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
                this.motionX *= (double) f4;
                this.motionX *= 0.900000011920929D;
                this.motionY *= 0.900000011920929D;
                this.motionY *= (double) f4;
                this.motionZ *= 0.900000011920929D;
                this.motionZ *= (double) f4;
            } else {
                super.travel(strafe, vertical, forward);
            }
        }
        this.prevLimbSwingAmount = this.limbSwingAmount;
        double deltaX = this.posX - this.prevPosX;
        double deltaZ = this.posZ - this.prevPosZ;
        double deltaY = this.posY - this.prevPosY;
        float delta = MathHelper.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) * 4.0F;
        if (delta > 1.0F) {
            delta = 1.0F;
        }
        this.limbSwingAmount += (delta - this.limbSwingAmount) * 0.4F;
        this.limbSwing += this.limbSwingAmount;

    }

    public Vec3d getPositionVector() {
        return new Vec3d(this.posX, this.posY + 0.5D, this.posZ);
    }

    class PathNavigateLargeSwimmer extends PathNavigateSwimmer {

        public PathNavigateLargeSwimmer(EntityLiving entitylivingIn, World worldIn) {
            super(entitylivingIn, worldIn);
        }

        protected PathFinder getPathFinder() {
            return new PathFinder(new LargeSwimNodeProcessor());
        }

        protected Vec3d getEntityPosition() {
            return new Vec3d(this.entity.posX, this.entity.posY + 0.49F, this.entity.posZ);
        }
        protected boolean canNavigate() {
            return this.entity.isInWater();
        }

        protected boolean isDirectPathBetweenPoints(Vec3d posVec31, Vec3d posVec32, int sizeX, int sizeY, int sizeZ) {
            RayTraceResult raytraceresult = this.world.rayTraceBlocks(posVec31, new Vec3d(posVec32.x, posVec32.y + 0.49F, posVec32.z), false, true, false);
            return raytraceresult == null || raytraceresult.typeOfHit == RayTraceResult.Type.MISS;
        }
    }

    class SwimmingMoveHelper extends EntityMoveHelper {
        private EntityPrehistoricSwimming dinosaur = EntityPrehistoricSwimming.this;

        public SwimmingMoveHelper() {
            super(EntityPrehistoricSwimming.this);
        }

        @Override
        public void onUpdateMoveHelper() {
            if (this.action == EntityMoveHelper.Action.MOVE_TO && !this.dinosaur.getNavigator().noPath() && !this.dinosaur.isBeingRidden()) {
                double distanceX = this.posX - this.dinosaur.posX;
                double distanceY = this.posY - this.dinosaur.posY;
                double distanceZ = this.posZ - this.dinosaur.posZ;
                double distance = Math.abs(distanceX * distanceX + distanceZ * distanceZ);
                double distanceWithY = (double) MathHelper.sqrt(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ);
                distanceY = distanceY / distanceWithY;
                float angle = (float) (Math.atan2(distanceZ, distanceX) * 180.0D / Math.PI) - 90.0F;
                this.dinosaur.rotationYaw = this.limitAngle(this.dinosaur.rotationYaw, angle, 30.0F);
                this.dinosaur.setAIMoveSpeed((float) 2F);
                this.dinosaur.motionY += (double) this.dinosaur.getAIMoveSpeed() * distanceY * 0.1D;
                if (distance < (double) Math.max(1.0F, this.entity.width)) {
                    float f = this.dinosaur.rotationYaw * 0.017453292F;
                    this.dinosaur.motionX -= (double) (MathHelper.sin(f) * 0.35F);
                    this.dinosaur.motionZ += (double) (MathHelper.cos(f) * 0.35F);
                }
            } else if (this.action == EntityMoveHelper.Action.JUMPING) {
                this.entity.setAIMoveSpeed((float) (this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
                if (this.entity.onGround) {
                    this.action = EntityMoveHelper.Action.WAIT;
                }
            } else {
                this.dinosaur.setAIMoveSpeed(0.0F);
            }
        }
    }

}
