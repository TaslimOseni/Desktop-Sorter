import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JOptionPane;


public class SortMyDesktop extends JOptionPane{
	
	
	public static void sendToRootFolders(String path){
	
		int p = 0, m = 0, d = 0, v = 0;
		
		File desktop = new File(path);
		File[] files = desktop.listFiles();
		
		File picFolder = new File(JOptionPane.showInputDialog("Input the pictures folder"));
		File musFolder = new File(JOptionPane.showInputDialog("Input the music folder"));
		File docFolder = new File(JOptionPane.showInputDialog("Input the document folder"));
		File vidFolder = new File(JOptionPane.showInputDialog("Input the video folder"));
		
		
		for(File i: files){
			try{
				String[] str = (Files.probeContentType(Paths.get(i.getPath()))).split("/");
				switch(str[0]){
				 	case "image":
				 		i.renameTo(new File(picFolder+ "//" + i.getName()));
				 		p++;
				 		break;
				 		
				 	case "video":
				 		i.renameTo(new File(vidFolder+ "//" + i.getName()));
				 		v++;
				 		break;
				 		
				 	case "audio":
				 		i.renameTo(new File(musFolder+ "//" + i.getName()));
				 		m++;
				 		break;
				 		
				 	case "document":
				 		i.renameTo(new File(docFolder+ "//" + i.getName()));
				 		d++;
				 		break;
				 		
				 	default:
				 		continue;
				 		
				}
				
			}
			catch(Exception e){
				JOptionPane.showInputDialog("Failed to move "+ i.getName() +" because of a "+ e.toString());
			}
		}
		
		JOptionPane.showInputDialog(String.format("%d pictures, %d videos, %d music and %d documents sucessfully moved.\n", p, v, m, d));
	}
	
	
	public static void askForEveryFile(String path){
		File desktop = new File(path);
		File[] files = desktop.listFiles();
		
		for(File j: files){
			String where = JOptionPane.showInputDialog(String.format("Where do you want to store the file %s. Type 'skip' to cancel\n", j.getName()));
			if(!(where.equals("skip"))){
				try{
					j.renameTo(new File(where + "//" + j.getName()));
				}
				catch(Exception e){
					JOptionPane.showInputDialog(JOptionPane.showInputDialog("Failed to move "+ j.getName() +" because of a "+ e.toString()));
				}
			}
		}
	}
	
	
	public static void main(String[] args){
		
		String path = JOptionPane.showInputDialog("Input the path to your Desktop or any directory you want to sort\n\n");
		
		if(new File(path).exists()){
			switch(JOptionPane.showInputDialog("Choose from the options below:\n\n1.\tAsk where to store each file\n2.\tMove files to default video, image, document and music directory\n\n")){
				case "2":
					sendToRootFolders(path);
					break;
				case "1":
					askForEveryFile(path);
					break;
				default:
					System.out.println("Wrong input.");
				}
			}
		else{
			JOptionPane.showInputDialog("Path doesn''t exist");
			main(args);
		}
		
		
		
	}

	
}
