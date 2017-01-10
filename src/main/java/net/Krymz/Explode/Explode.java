package net.Krymz.Explode;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Explode implements Listener{

	private Main plugin;
	public Explode(Main listener) {
		this.plugin = listener;		
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void Tnt(final PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK
				|| e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (e.getPlayer().getItemInHand() != null) {
				if (e.getPlayer().getItemInHand().getType() == Material.TNT) {
					final Entity tnt = e.getPlayer().getWorld().spawnEntity(e.getPlayer().getEyeLocation(), EntityType.PRIMED_TNT);
					tnt.setVelocity(e.getPlayer().getLocation().getDirection().multiply(1.5));
				}
				}
			}
		}
	
	public static HashMap<Block, Block> blocklist = new HashMap<Block, Block>();
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockEvent(EntityExplodeEvent e){
		e.setCancelled(true);
		if(e.getEntity() instanceof TNTPrimed){
		for(final Block block : e.blockList()){
				e.getEntity().remove();
			FallingBlock falling = block.getLocation().getWorld().spawnFallingBlock(block.getLocation(), block.getType(), block.getData());
			float x = (float) (Math.random() * Util.randomNum(-2,2));
            float y = (float) (Math.random() * Util.randomNum(1,2));
            float z = (float) (Math.random() * Util.randomNum(-2,2));
			falling.setVelocity(falling.getLocation().getDirection().setX(x).setY(y).setZ(z));
			
			
			int time = Util.randomNum(1, 5) * 20 + Util.randomNum(1, 5);
           
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    public void run() {
                            block.getState().update(true);
                            block.getWorld().playEffect(block.getLocation().add(0, 1, 0), Effect.FIREWORKS_SPARK, Integer.MAX_VALUE);
                    }
            }, time);
		}
		}
	}
	
	@EventHandler
	public void onChange(EntityChangeBlockEvent e){
		e.setCancelled(true);
	}
}
