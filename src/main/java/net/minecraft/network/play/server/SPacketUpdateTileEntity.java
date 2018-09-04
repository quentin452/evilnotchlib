package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SPacketUpdateTileEntity implements Packet<INetHandlerPlayClient>
{
    protected BlockPos blockPos;
    /** Used only for vanilla tile entities */
    protected int tileEntityType;
    protected NBTTagCompound nbt;
    
    public static Set<BlockPos> toIgnore = new HashSet();

    public SPacketUpdateTileEntity()
    {
    }

    public SPacketUpdateTileEntity(BlockPos blockPosIn, int tileEntityTypeIn, NBTTagCompound compoundIn)
    {
        this.blockPos = blockPosIn;
        this.tileEntityType = tileEntityTypeIn;
        this.nbt = compoundIn;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.blockPos = buf.readBlockPos();
        this.tileEntityType = buf.readUnsignedByte();
        this.nbt = buf.readCompoundTag();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeBlockPos(this.blockPos);
        buf.writeByte((byte)this.tileEntityType);
        buf.writeCompoundTag(this.nbt);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler)
    {
    	if(this.toIgnore.contains(this.blockPos))
    	{
//    		System.out.println("Ignoring packet for tile:" + this.blockPos);
    		this.toIgnore.remove(this.blockPos);
    		return;
    	}
        handler.handleUpdateTileEntity(this);
    }

    @SideOnly(Side.CLIENT)
    public BlockPos getPos()
    {
        return this.blockPos;
    }

    @SideOnly(Side.CLIENT)
    public int getTileEntityType()
    {
        return this.tileEntityType;
    }

    @SideOnly(Side.CLIENT)
    public NBTTagCompound getNbtCompound()
    {
        return this.nbt;
    }
}