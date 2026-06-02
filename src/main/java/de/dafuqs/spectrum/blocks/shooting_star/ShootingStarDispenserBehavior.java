package de.dafuqs.spectrum.blocks.shooting_star;

import de.dafuqs.spectrum.entity.entity.*;
import net.minecraft.core.*;
import net.minecraft.core.dispenser.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.phys.*;
import javax.annotation.*;

public class ShootingStarDispenserBehavior extends DefaultDispenseItemBehavior {
	
	// offsets for parity with original values
	private static final double HORIZONTAL_DISPENSE_OFFSET = 0.225;
	private static final double VERTICAL_DISPENSE_OFFSET = 0.23;
	
	@Override
	public ItemStack execute(BlockSource pointer, ItemStack stack) {
		Direction direction = pointer.state().getValue(DispenserBlock.FACING);
		
		Level level = pointer.level();
		ShootingStarItem shootingStarItem = ((ShootingStarItem) stack.getItem());
		
		// spawn shooting star before calculating output position so we can get entity dimensions
		ShootingStarEntity shootingStarEntity = shootingStarItem.getEntityForStack(level, pointer.center(), stack);
		
		// pull from dimensions to make sure we're not inside the dispenser - (step value * (half a block + offset + half of relevant dimension))
		shootingStarEntity.setPos(
				shootingStarEntity.getX() + (direction.getStepX() * (0.5 + HORIZONTAL_DISPENSE_OFFSET + shootingStarEntity.getBbWidth() / 2)),
				shootingStarEntity.getY() + (direction.getStepY() * (0.5 + VERTICAL_DISPENSE_OFFSET + (direction == Direction.DOWN ? shootingStarEntity.getBbHeight() : 0))),
				shootingStarEntity.getZ() + (direction.getStepZ() * (0.5 + HORIZONTAL_DISPENSE_OFFSET + shootingStarEntity.getBbWidth() / 2))
		);
		
		shootingStarEntity.setYRot(direction.toYRot());
		shootingStarEntity.push(direction.getStepX() * 0.4, direction.getStepY() * 0.4, direction.getStepZ() * 0.4);
		level.addFreshEntity(shootingStarEntity);
		
		stack.shrink(1);
		return stack;
	}
	
}
