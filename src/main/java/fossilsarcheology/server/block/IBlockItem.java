package fossilsarcheology.server.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public interface IBlockItem {
    public ItemBlock getItemBlock(Block block);
}
