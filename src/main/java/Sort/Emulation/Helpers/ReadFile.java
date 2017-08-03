package Sort.Emulation.Helpers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Sort.Emulation.Gui.GuiStressTest;
import Sort.Emulation.Model.RootElementLogicModel;

public class ReadFile {
	public static ArrayList readFileToListByLine(String path) {
		String csvFile = path;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ";";
		ArrayList<RootElementLogicModel> tmp = new ArrayList<RootElementLogicModel>();
		int cnt = 0;
//		 System.out.println("Начинаю читать файл");
		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] EH = line.split(cvsSplitBy);
				switch(EH.length) {
			    case 1: 
			    	tmp.add(new RootElementLogicModel(EH[0]));
			    	cnt += 1;
					break;
				case 2: 
					tmp.add(new RootElementLogicModel(EH[0], EH[1]));
					cnt += 1;
					break;
				case 3: 
					tmp.add(new RootElementLogicModel(EH[0], EH[1], EH[2]));
					cnt += 1;
					break;
				case 4: 
					tmp.add(new RootElementLogicModel(EH[0], EH[1], EH[2], EH[3]));
					cnt += 1;
					break;
				case 5: 
					tmp.add(new RootElementLogicModel(EH[0], EH[1], EH[2], EH[3], EH[4]));
					cnt += 1;
					break;
				default: 
				    break;
			}
				
			}

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		GuiStressTest.end.setText("" + cnt);
//		 System.out.println("В файле найдено " + cnt + " номеров ЕН");
		return tmp;
	}
}
