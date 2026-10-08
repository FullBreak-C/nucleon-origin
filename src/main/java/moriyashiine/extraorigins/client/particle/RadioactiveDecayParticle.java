package moriyashiine.extraorigins.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;

public class RadioactiveDecayParticle extends SpriteBillboardParticle {

    private final float baseScale;

    protected RadioactiveDecayParticle(ClientWorld clientWorld, double posX, double posY, double posZ, double velocityX, double velocityY, double velocityZ) {
        super(clientWorld, posX, posY, posZ, 0, 0, 0); // Ignore initial velocity

        // Cloud chamber trails are made of tiny condensation drops
        this.scale = 0.08F + (float) (Math.random() * 0.04F);
        this.baseScale = this.scale;

        // Give it a tiny bit of random drift so the trail isn't perfectly rigid
        this.velocityX = (Math.random() - 0.5) * 0.01;
        this.velocityY = (Math.random() - 0.5) * 0.01;
        this.velocityZ = (Math.random() - 0.5) * 0.01;

        this.maxAge = (int) (40.0 / (Math.random() * 0.8 + 0.2)); // Lives for roughly 2 seconds

        // White with a very slight icy-blue/grey alcohol vapour tint
        this.setColor(0.9F, 0.95F, 1.0F);
        this.alpha = 0.7F; // Start semi-transparent
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;

        if (this.age++ >= this.maxAge) {
            this.markDead();
            return;
        }

        // 1. Expand over time: Vapour puffs get larger as they dissipate
        this.scale = this.baseScale + (((float)this.age / (float)this.maxAge) * 0.15F);

        // 2. Fade out: Smoothly transition alpha to 0 as it nears maxAge
        this.alpha = 0.7F - (0.7F * ((float)this.age / (float)this.maxAge));

        this.move(this.velocityX, this.velocityY, this.velocityZ);

        // 3. High friction: Stops the particle from moving much so it forms a clean line
        this.velocityX *= 0.85;
        this.velocityY *= 0.85;
        this.velocityZ *= 0.85;
    }

    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientWorld world, double x, double y, double z, double vX, double vY, double vZ) {
            RadioactiveDecayParticle particle = new RadioactiveDecayParticle(world, x, y, z, vX, vY, vZ);
            particle.setSprite(this.spriteProvider);
            return particle;
        }
    }
}