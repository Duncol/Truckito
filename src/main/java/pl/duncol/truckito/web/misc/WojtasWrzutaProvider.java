package pl.duncol.truckito.web.misc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class WojtasWrzutaProvider {

	public String getWrzuta() {
		// DLA BEKI, DO WYWALENIA :D
		try {
			List<String> wrzuty = Files.readAllLines(Paths.get("C:\\Users\\Duncol\\Workspace\\Truckito\\wrzuty-wojtas.csv"));
			Random random = new Random();
			return wrzuty.get(random.nextInt(wrzuty.size()));
		} catch (IOException ex) {
			ex.printStackTrace();
			return "";
		}
	}
}
