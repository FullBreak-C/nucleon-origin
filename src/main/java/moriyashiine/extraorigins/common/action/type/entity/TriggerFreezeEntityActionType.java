/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.extraorigins.common.action.type.entity;

import io.github.apace100.apoli.action.ActionConfiguration;
import io.github.apace100.apoli.action.context.EntityActionContext;
import io.github.apace100.apoli.action.type.EntityActionType;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import moriyashiine.extraorigins.common.init.ModActionTypes;
import moriyashiine.extraorigins.common.init.ModEntityComponents;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;

public class TriggerFreezeEntityActionType extends EntityActionType {

    public static final TypedDataObjectFactory<TriggerFreezeEntityActionType> DATA_FACTORY = TypedDataObjectFactory.simple(
            new SerializableData()
                    .add("duration", SerializableDataTypes.INT, 100),
            data -> new TriggerFreezeEntityActionType(
                    data.get("duration")
            ),
            (actionType, serializableData) -> serializableData.instance()
                    .set("duration", actionType.duration)
    );

    private final int duration;

    public TriggerFreezeEntityActionType(int duration) {
        this.duration = duration;
    }

    @Override
    public @NotNull ActionConfiguration<?> getConfig() {
        return ModActionTypes.TRIGGER_FREEZE;
    }

    @Override
    public void accept(EntityActionContext context) {
        if (context.entity() instanceof PlayerEntity player) {
            ModEntityComponents.RANDOM_POWER_GRANTER.maybeGet(player).ifPresent(component -> {
                component.triggerFreeze(duration);
            });
        }
    }
}