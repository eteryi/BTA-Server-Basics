package cross.simplyhomes.config;


import cross.simplyhomes.SimpleHomes;
import cross.simplyhomes.modules.Homes;
import cross.simplyhomes.modules.Modules;
import cross.simplyhomes.modules.Warps;
import org.apache.logging.log4j.core.util.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Config {

	/*
	config.json

	{
	"warp": true,
	"home": true,
	"tpa": true
	}
	 */

	/*
	homes.json

	{
		"username: "etery",
		"homes": [
			{
				"name":"home1",
				"location": [ -24, 54, 2 ]
			},
			{
				"name":"home2",
				"location": [ -24, 54, 2 ]
			}
	}
	 */

	/*
	warps.json

	[
		{
			"name":"",
			"location" : [ ]
		}
	]
	 */

	private File configFile;
	private static String PATH = "./config/basics/";
	private static String CONFIG_PATH = PATH + "config.json";
	private static String WARP_PATH = PATH + "warps.json";
	private static String PLAYER_PATH = PATH + "players/";

	private HashMap<Modules.ID, Boolean> activeModules;

	public Config() {
		this.activeModules = new HashMap<>();
	}

	private static void writeConfig(File f) {
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		try (FileWriter writer = new FileWriter(f)) {
			System.out.println("file writer");
			JSONObject configObject = new JSONObject();
			System.out.println("jason");
			for (Modules.ID id : Modules.ID.values()) {
				configObject.put(id.toString(), true);
			}
			configObject.write(writer);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void writePlayerData(File f) {
		JSONObject playerData = new JSONObject();
		JSONArray homesArray = new JSONArray();

		playerData.put("homes", homesArray);

		try (FileWriter writer = new FileWriter(f)) {
			playerData.write(writer);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void writeWarp(File f) {
		JSONArray array = new JSONArray();
		try (FileWriter writer = new FileWriter(f)) {
			f.createNewFile();
			array.write(writer);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	private File getPlayerFile(String username) {
		File playerFile = new File(PLAYER_PATH + username + ".json");
		if (!playerFile.exists()) {
			try {
				playerFile.createNewFile();
				writePlayerData(playerFile);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return playerFile;
	}

	public void loadPlayerData(String username) {
		// {
		// 		"homes": [
		//			{
		//				"name": "a"
		//				"location": [0.5, 79.0, 25.0, 1]
		//			}
		// 		]
		// }
		Runnable r = () -> {
			File playerFile = getPlayerFile(username);

			JSONObject playerData = null;
			try (FileReader reader = new FileReader(playerFile)) {
				String jsonString = IOUtils.toString(reader);
				playerData = new JSONObject(jsonString);
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}

			JSONArray array = playerData.getJSONArray("homes");
			Homes homes = (Homes) SimpleHomes.getModuleRegistry().getModule(Modules.ID.HOME);

			for (int i = 0; i < array.length(); i++) {
				JSONObject homeSerialized = array.getJSONObject(i);
				Homes.Home h = Homes.Home.deserialize(homeSerialized, username);
				homes.registerFromConfig(username, h);
			}
		};
		Thread t = new Thread(r);
		t.start();
	}

	public void updateHomes(String username, ArrayList<Homes.Home> homes) {
		Runnable r = () -> {
            File playerFile = getPlayerFile(username);
			JSONObject playerData = new JSONObject();

			JSONArray homesArr = new JSONArray();
			for (Homes.Home h : homes) {
				JSONObject obj = Homes.Home.serialize(h);
				homesArr.put(obj);
			}
			playerData.put("homes", homesArr);

			try (FileWriter writer = new FileWriter(playerFile)) {
				playerData.write(writer);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
        };
		Thread t = new Thread(r);
		t.start();
	}

	public void init() {
		File configDir = new File(PATH);
		File configFile = new File(CONFIG_PATH);
		File playerDir = new File(PLAYER_PATH);
		File warpFile = new File(WARP_PATH);

		if (!configDir.exists()) configDir.mkdir();
		if (!playerDir.exists()) playerDir.mkdir();

		if (!configFile.exists()) Config.writeConfig(configFile);
		if (!warpFile.exists()) this.writeWarp(warpFile);
		System.out.println("but this does");


		JSONObject configObject = null;
		try (FileReader reader = new FileReader(configFile)) {
			String jsonString = IOUtils.toString(reader);
			configObject = new JSONObject(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

        for (Modules.ID id : Modules.ID.values()) {
			activeModules.put(id, configObject.getBoolean(id.toString()));
		}
		this.configFile = configFile;
	}

	public void loadWarp() {
		File f = new File(WARP_PATH);
		JSONArray arr;
		try (FileReader reader = new FileReader(f)) {
			String jsonString = IOUtils.toString(reader);
			arr = new JSONArray(jsonString);
		} catch (IOException e) {
            throw new RuntimeException(e);
        }
		Warps warpModule = (Warps) SimpleHomes.getModuleRegistry().getModule(Modules.ID.WARP);
		if (warpModule == null) return;
		for (int i = 0; i < arr.length(); i++) {
			JSONObject obj = arr.getJSONObject(i);
			Warps.Warp warp = Warps.Warp.deserialize(obj);
			warpModule.loadFromConfig(warp);
		}
    }

	public boolean isModuleActive(Modules.ID m) {
		if (activeModules.get(m) == null) activeModules.put(m, true);
		return activeModules.get(m);
	}

	public void updateWarp(ArrayList<Warps.Warp> warps) {
		Runnable r = () -> {
			File f = new File(WARP_PATH);
			JSONArray arr = new JSONArray();
			for (Warps.Warp w : warps) {
				arr.put(Warps.Warp.serialize(w));
			}
			try (FileWriter writer = new FileWriter(f)) {
				arr.write(writer);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		};

		Thread t = new Thread(r);
		t.start();
	}
}
