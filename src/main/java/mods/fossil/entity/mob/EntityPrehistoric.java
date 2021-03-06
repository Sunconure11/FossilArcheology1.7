package mods.fossil.entity.mob;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import mods.fossil.client.gui.GuiPedia;
import mods.fossil.fossilEnums.EnumOrderType;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityPrehistoric extends EntityTameable {

	public static final int OWNER_DISPLAY_NAME_INDEX = 24;

	protected static final ResourceLocation pediaclock = new ResourceLocation("fossil:textures/gui/PediaClock.png");
	protected static final ResourceLocation pediafood = new ResourceLocation("fossil:textures/gui/PediaFood.png");
	protected static final ResourceLocation pediaheart = new ResourceLocation("fossil:textures/gui/PediaHeart.png");

	private boolean inHerd = false;
	private float awarenessRadius;
	private int maxHerdSize;
	private float herdWanderRadius;

	public EntityPrehistoric(World par1World) {
		super(par1World);
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(OWNER_DISPLAY_NAME_INDEX, "");
	}

	public boolean isInHerd() {
		return inHerd;
	}

	public float getAwarenessRadius() {
		return awarenessRadius;
	}

	public int getMaxHerdSize() {
		return maxHerdSize;
	}
	
	public float getHerdWanderRadius() {
		return herdWanderRadius;
	}

	/**
	 * Override this and set temporary variables to the attributes.
	 */
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(19.0D);
		getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
		setBaseValues();
	}

	/**
	 * Overrided in unique entity classes.
	 */
	private void setBaseValues()
	{
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1.0D);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0D);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.0D);

	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);
		compound.setString("OwnerDisplayName", this.getOwnerDisplayName());  
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		String s = "";

		if (compound.hasKey("Owner", 8))
		{
			s = compound.getString("Owner");
			this.setOwnerDisplayName(s);
		}
		else
		{
			this.setOwnerDisplayName(compound.getString("OwnerDisplayName"));
		}

		super.readEntityFromNBT(compound);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable) {
		// TODO Auto-generated method stub
		return null;
	}

	public EntityPlayer getRidingPlayer()
	{
		if (riddenByEntity instanceof EntityPlayer)
		{
			return (EntityPlayer) riddenByEntity;
		}
		else
		{
			return null;
		}
	}

	@SideOnly(Side.CLIENT)
	public void ShowPedia2(GuiPedia p0, String mobName)
	{
		p0.reset();
		p0.AddStringLR("", 150, false);
		String translatePath = "assets/fossil/dinopedia/" + Minecraft.getMinecraft().gameSettings.language +"/";
		String bioFile = String.valueOf(mobName) + ".txt";

		if(getClass().getClassLoader().getResourceAsStream( translatePath ) == null)
		{
			translatePath = "assets/fossil/dinopedia/" + "en_US" + "/";
		}

		if(getClass().getClassLoader().getResourceAsStream( translatePath + bioFile ) != null)
		{
			InputStream fileReader = getClass().getClassLoader().getResourceAsStream( translatePath + bioFile );
			try {
				BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(fileReader));
				StringBuffer stringBuffer = new StringBuffer();
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					GL11.glPushMatrix();
					GL11.glScalef(0.5F, 0.5F, 0.5F);
					p0.AddStringLR(line, 150, false);
					GL11.glPopMatrix();
				}
				fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else
		{
			p0.AddStringLR("File not found.", false);
			GL11.glPushMatrix();
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			p0.AddStringLR(translatePath + bioFile, 150, false);
			GL11.glPopMatrix();
		}
	}

	public void onWhipRightClick() {
		// TODO Auto-generated method stub

	}


	public String getOwnerDisplayName()
	{
		String s = this.dataWatcher.getWatchableObjectString(OWNER_DISPLAY_NAME_INDEX);
		return s;
	}

	public void setOwnerDisplayName(String displayName)
	{
		this.dataWatcher.updateObject(OWNER_DISPLAY_NAME_INDEX, displayName);
	}

}
