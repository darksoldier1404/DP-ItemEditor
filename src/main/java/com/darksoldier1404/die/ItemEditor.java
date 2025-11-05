package com.darksoldier1404.die;

import com.darksoldier1404.die.commands.DPIECommand;
import com.darksoldier1404.dppc.data.DPlugin;
import com.darksoldier1404.dppc.utils.PluginUtil;

public class ItemEditor extends DPlugin {
    public static ItemEditor plugin;

    public static ItemEditor getInstance() {
        return plugin;
    }

    public ItemEditor() {
        super(true);
        init();
        plugin = this;
    }

    @Override
    public void onLoad() {
        PluginUtil.addPlugin(plugin, 26325);
    }

    @Override
    public void onEnable() {
        getCommand("dpie").setExecutor(new DPIECommand().getExecutor());
    }

    @Override
    public void onDisable() {
        saveAllData();
    }
}
