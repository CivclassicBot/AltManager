package com.civclassic.altmanager;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import vg.civcraft.mc.prisonpearl.PrisonPearlPlugin;
import vg.civcraft.mc.prisonpearl.events.PrisonPearlEvent;

public class ExilePearlHandler extends ImprisonmentHandler {

	ExilePearlHandler(AltManager plugin) {
		super(plugin);
	}

	@EventHandler
	public void onExilePearl(PrisonPearlEvent event) {
		UUID imprisoned = event.getPrisonPearl().getImprisonedId();
		Set<UUID> alts = plugin.getAlts(imprisoned);
		int count = getImprisonedCount(alts);
		if(count < plugin.getMaxImprisoned()) {
			return;
		}
		alts.remove(imprisoned);
		for(UUID id : alts) {
			Player player = Bukkit.getPlayer(id);
			if(player != null && player.isOnline()) {
				plugin.kickPlayer(player);
			}
		}
	}
	
	@Override
	public int getImprisonedCount(Set<UUID> alts) {
		Set<UUID> imprisoned = new HashSet<UUID>();
		for(UUID alt : alts) {
			if(PrisonPearlPlugin.getPrisonPearlManager().isImprisoned(alt)) {
				imprisoned.add(alt);
			}
		}
		return imprisoned.size();
	}

	@Override
	public boolean isImprisoned(UUID player) {
		return PrisonPearlPlugin.getPrisonPearlManager().isImprisoned(player);
	}
}
