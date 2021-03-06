package mods.fossil.items;

import mods.fossil.Fossil;
import mods.fossil.client.LocalizationStrings;
import mods.fossil.entity.EntityStoneboard;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

public class ItemStoneBoard extends Item
{
    public ItemStoneBoard()
    {
        this.setCreativeTab(CreativeTabs.tabDecorations);
        setUnlocalizedName(LocalizationStrings.TABLET_NAME);
        setCreativeTab(Fossil.tabFItems);
    }

    public boolean onItemUse(ItemStack var1, EntityPlayer var2, World world, int x, int y, int z, int direction, float par8, float par9, float par10)
    {
        if (direction == 0 || direction == 1)
        {
            return false;
        }
        else
        {
            int var11 = Direction.facingToDirection[direction];
            EntityStoneboard var12 = new EntityStoneboard(world, x, y, z, var11);

            if (!var2.canPlayerEdit(x, y, z, direction, var1))
            {
                return false;
            }
            else
            {
                if (var12 != null && var12.onValidSurface())
                {
                    if (!world.isRemote)
                    {
                        world.spawnEntityInWorld(var12);
                    }

                    --var1.stackSize;
                }

                return true;
            }
        }
    }

    @Override
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon("fossil:Stone_Tablet");
    }
}
