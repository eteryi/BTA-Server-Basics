package cross.simplyhomes.config;


import cross.simplyhomes.modules.Modules;
import org.json.JSONObject;
import org.json.JSONWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Config  {

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
	private ArrayList<Modules.ID> activeModules;

	public Config() {
		this.activeModules = new ArrayList<>();
	}

	public static void writeConfig(File f) {
		if (!f.exists()) {
			try {
                f.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
			}

			try (FileWriter writer = new FileWriter(f)) {
				JSONObject configObject = new JSONObject();
				for (Modules.ID id : Modules.ID.values()) {
					configObject.put(id.toString(), true);
				}
				configObject.write(writer);
			} catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
	}

	public void init() {
		File configDir = new File("./config/basics/");
		if (!configDir.exists()) configDir.mkdir();
		File configFile = new File("./config/basics/config.json");
		if (!configFile.exists()) {
			Config.writeConfig(configFile);
		}

		this.configFile = configFile;
	}
}
