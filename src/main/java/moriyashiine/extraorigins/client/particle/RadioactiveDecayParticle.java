package moriyashiine.extraorigins.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;

public class RadioactiveDecayParticle extends SpriteBillboardParticle {


    protected RadioactiveDecayParticle(ClientWorld clientWorld, double posX, double posY, double posZ, double velocityX, double velocityY, double velocityZ) {
        super(clientWorld, posX, posY, posZ, 0, 0, 0);
        scale = 0.01F + (float) (Math.random() * 0.01F);
        this.velocityX = (Math.random() - 0.5) * 0.005;
        this.velocityY = (Math.random() - 0.5) * 0.005;
        this.velocityZ = (Math.random() - 0.5) * 0.005;

        this.maxAge = (int) (10.0 + Math.random() * 15.0);

        this.alpha = 0.7F;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        prevPosX = x;
        prevPosY = y;
        prevPosZ = z;

        if (this.age++ >= this.maxAge) {
            this.markDead();
            return;
        }

        this.alpha = 0.7F - (0.7F * ((float)this.age / (float)this.maxAge));

        this.move(this.velocityX, this.velocityY, this.velocityZ);
        this.velocityX *= 1.03;
        this.velocityY *= 1.03;
        this.velocityZ *= 1.03;
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