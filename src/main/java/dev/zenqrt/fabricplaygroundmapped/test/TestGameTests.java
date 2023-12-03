package dev.zenqrt.fabricplaygroundmapped.test;

import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.Blocks;

public final class TestGameTests {

    @GameTest(template = FabricGameTest.EMPTY_STRUCTURE)
    public void someTest(GameTestHelper context) {
        context.setBlock(0, 0, 0, Blocks.DIAMOND_BLOCK);

        context.succeedWhenBlockPresent(Blocks.DIAMOND_BLOCK, 0, 2, 0);
    }

}
