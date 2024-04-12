package weapon.cats.main.Entities.ai;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

import org.jetbrains.annotations.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Vec3d;

public class FollowEntityGoal extends Goal{
	private final MobEntity mob;
    private final Predicate<Entity> targetPredicate;
    @Nullable
    private Entity target;
    private final Class<? extends Entity> entityClass;
    private final double speed;
    private final EntityNavigation navigation;
    private int updateCountdownTicks;
    private final float minDistance;
    private float oldWaterPathFindingPenalty;
    private final float maxDistance;

    public FollowEntityGoal(MobEntity mob, double speed, float minDistance, float maxDistance, Class<? extends Entity> entityClass) {
        this.mob = mob;
        this.entityClass = entityClass;
        this.targetPredicate = target -> target != null && entityClass.isInstance(target);
        this.speed = speed;
        this.navigation = mob.getNavigation();
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        if (!(mob.getNavigation() instanceof MobNavigation) && !(mob.getNavigation() instanceof BirdNavigation)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowMobGoal");
        }
    }

    @Override
    public boolean canStart() {
        List<? extends Entity> list = this.mob.getWorld().getEntitiesByClass(entityClass, this.mob.getBoundingBox().expand(this.maxDistance), this.targetPredicate);
        if (!list.isEmpty()) {
            for (Entity mobEntity : list) {
                if (mobEntity.isInvisible()) continue;
                this.target = mobEntity;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean shouldContinue() {
        return this.target != null && !this.navigation.isIdle() && this.mob.squaredDistanceTo(this.target) > (double)(this.minDistance * this.minDistance);
    }

    @Override
    public void start() {
        this.updateCountdownTicks = 0;
        this.oldWaterPathFindingPenalty = this.mob.getPathfindingPenalty(PathNodeType.WATER);
        this.mob.setPathfindingPenalty(PathNodeType.WATER, 0.0f);
    }

    @Override
    public void stop() {
        this.target = null;
        this.navigation.stop();
        this.mob.setPathfindingPenalty(PathNodeType.WATER, this.oldWaterPathFindingPenalty);
    }

    @Override
    public void tick() {
        double f;
        double e;
        if (this.target == null || this.mob.isLeashed()) {
            return;
        }
        this.mob.getLookControl().lookAt(this.target, 10.0f, this.mob.getMaxLookPitchChange());
        if (--this.updateCountdownTicks > 0) {
            return;
        }
        this.updateCountdownTicks = this.getTickCount(10);
        double d = this.mob.getX() - this.target.getX();
        double g = d * d + (e = this.mob.getY() - this.target.getY()) * e + (f = this.mob.getZ() - this.target.getZ()) * f;
        if (g <= (double)(this.minDistance * this.minDistance)) {
            this.navigation.stop();
            Vec3d eyePos = this.target.getEyePos();
            if (g <= (double)this.minDistance || eyePos.x == this.mob.getX() && eyePos.y == this.mob.getY() && eyePos.z == this.mob.getZ()) {
                double h = this.target.getX() - this.mob.getX();
                double i = this.target.getZ() - this.mob.getZ();
                this.navigation.startMovingTo(this.mob.getX() - h, this.mob.getY(), this.mob.getZ() - i, this.speed);
            }
            return;
        }
        this.navigation.startMovingTo(this.target, this.speed);
    }
}
