import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
																																																																																																																															

public class SortMyDesktop extends Frame implements ActionListener, WindowListener{
	
	
	private JTextField desktopField;
	private JLabel firstHeader, chooseFromOptions, error;
	private JPanel panelOuter;
	private JButton option1, option2, go, done;
	private static final long serialVersionUID = 1L;
	private int indexer = 0;
	private File picFolder, vidFolder, musFolder, docFolder;
	
	
	public SortMyDesktop(){
		firstHeader = new JLabel("Input the path to your Desktop or any directory you want to sort");
		desktopField = new JTextField("/home/taslim/Desktop", 20);
		desktopField.setEditable(true);
		chooseFromOptions = new JLabel("Choose from the options below:");
		error = new JLabel("File doesn't exist");
		panelOuter = new JPanel(new GridBagLayout());
		option1 = new JButton("Ask where to store each file");
		option2 = new JButton("Move files to their appropriate directories");
		go = new JButton("GO");
		done = new JButton("Done");
		
		setTitle("Sort my Desktop");
		setSize(600, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		
		addWindowListener(this);
	}
	
	
	public void sendToRootFolders(String path){
		
		File desktop = new File(path);
		File[] files = desktop.listFiles();
		
		GridBagConstraints con = new GridBagConstraints();
		con.insets = new Insets(10,  10,  10,  10);
		
		JPanel picPan = new JPanel();
		JLabel picLab = new JLabel("Default picture folder");
		JTextField picField = new JTextField("/home/taslim/Pictures", 30);
		picPan.add(picLab);
		picPan.add(picField);
		con.gridy = 0;
		panelOuter.add(picPan, con);
		
		JPanel musPan = new JPanel();
		JLabel musLab = new JLabel("Default music folder");
		JTextField musField = new JTextField("/home/taslim/Music", 30);
		musPan.add(musLab);
		musPan.add(musField);
		con.gridy = 1;
		panelOuter.add(musPan, con);
		
		JPanel vidPan = new JPanel();
		JLabel vidLab = new JLabel("Default video folder");
		JTextField vidField = new JTextField("/home/taslim/Videos", 30);
		vidPan.add(vidLab);
		vidPan.add(vidField);
		con.gridy = 2;
		panelOuter.add(vidPan, con);
		
		JPanel docPan = new JPanel();
		JLabel docLab = new JLabel("Default document folder");
		JTextField docField = new JTextField("/home/taslim/Documents", 30);
		docPan.add(docLab);
		docPan.add(docField);
		con.gridy = 3;
		panelOuter.add(docPan, con);
		
		
		con.gridy = 5;
		panelOuter.add(done, con);
		
		con.gridy = 6;
		con.insets = new Insets(30,  30,  30,  30);
		error.setVisible(false);
		panelOuter.add(error, con);
		
		done.addActionListener(new ActionListener(){
			@SuppressWarnings("boxing")
			@Override
			public void actionPerformed(ActionEvent arg0){
				
				if(new File(picField.getText().toString().trim()).exists()){
					error.setVisible(false);
					picFolder = new File(picField.getText().toString().trim());
					if(new File(vidField.getText().toString().trim()).exists()){
						error.setVisible(false);
						vidFolder = new File(vidField.getText().toString().trim());
						if(new File(musField.getText().toString().trim()).exists()){
							error.setVisible(false);
							musFolder = new File(musField.getText().toString().trim());
							if(new File(docField.getText().toString().trim()).exists()){
								error.setVisible(false);
								docFolder = new File(docField.getText().toString().trim());
								
								int p = 0, m = 0, d = 0, v = 0;
								
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
										
									}
								}
								
								panelOuter.removeAll();
								panelOuter.revalidate();
								panelOuter.repaint();
								
								JLabel finalLabel = new JLabel(String.format("%d pictures, %d videos, %d music and %d documents moved successfully!", p, v, m, d));
								panelOuter.add(finalLabel);
								JButton done = new JButton("Done");
								con.gridy = 1;
								panelOuter.add(done, con);
								
								done.addActionListener(new ActionListener(){
									@Override
									public void actionPerformed(ActionEvent arg0){
										System.exit(0);
									}
								});
							}
							
							else{
								error.setText("Inputed document folder does not exist");
								error.setVisible(true);
							}
						}
						else{
							error.setText("Inputed music folder does not exist");
							error.setVisible(true);
						}
					}
					else{
						error.setText("Inputed video folder does not exist");
						error.setVisible(true);
					}
				}
				else{
					error.setText("Inputed picture folder does not exist");
					error.setVisible(true);
				}
			}
		});
		
	}
	
	
	public void askForEveryFile(String path){
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 10, 10);
		
		File desktop = new File(path);
		File[] files = desktop.listFiles();
		
		JLabel label = new JLabel();
		c.gridy = 0;
		panelOuter.add(label, c);
		JTextField whereToStore = new JTextField("/home/taslim/", 20);
		c.gridy = 3;
		panelOuter.add(whereToStore, c);
		c.gridy = 6;
		JPanel ourButtons = new JPanel();
		JButton move = new JButton("Move");
		ourButtons.add(move);
		JButton skip = new JButton("Skip");
		ourButtons.add(skip);
		panelOuter.add(ourButtons, c);
		
//		yayornay.com
		
		indexer = 0;
		
		looper(panelOuter, files, label, move, skip, whereToStore);
		
		}
	
	
	public static void main(String[] args){
		SortMyDesktop sod = new SortMyDesktop();
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		
		constraints.gridy = 0;
		sod.panelOuter.add(sod.firstHeader, constraints);
		
		constraints.gridy = 1;
		JPanel panelInner = new JPanel();
		panelInner.add(sod.desktopField, constraints);
		panelInner.add(sod.go, constraints);
		sod.panelOuter.add(panelInner, constraints);
		
		constraints.gridy = 2;
		sod.error.setVisible(false);
		sod.panelOuter.add(sod.error, constraints);
		
		constraints.gridy = 4;
		sod.add(sod.panelOuter, BorderLayout.CENTER);
		
		
		sod.go.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String link = sod.desktopField.getText().toString().trim();
				
				if(new File(link).exists()){
					sod.error.setVisible(false);
					sod.panelOuter.removeAll();
					sod.panelOuter.revalidate();
					sod.panelOuter.repaint();
					
					constraints.gridy = 4;
					sod.panelOuter.add(sod.chooseFromOptions, constraints);
					constraints.gridy = 12;
					sod.panelOuter.add(sod.option1, constraints);
					constraints.gridy = 14;
					sod.panelOuter.add(sod.option2, constraints);
					
					sod.option1.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0){
							sod.panelOuter.removeAll();
							sod.panelOuter.revalidate();
							sod.panelOuter.repaint();
							sod.askForEveryFile(link);
						}
					});
					
					sod.option2.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0){
							sod.panelOuter.removeAll();
							sod.panelOuter.revalidate();
							sod.panelOuter.repaint();
							sod.sendToRootFolders(link);
						}
					});
					
					
				}
				else{
					sod.error.setVisible(true);
				}
				
			}
		});
		
		
	}

	
	public void looper(JPanel panelOuter, File[] files, JLabel label, JButton save, JButton skip, JTextField whereToStore){
		label.setText(String.format("Where do you want to store the file: %s", files[indexer].getName()));
		
		save.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				if(new File(whereToStore.getText().toString().trim()).exists()){
					files[indexer].renameTo(new File(whereToStore.getText().toString().trim() + "//"+ files[indexer].getName()));
					indexer++;
					if(indexer < files.length){
						looper(panelOuter, files, label, save, skip, whereToStore);
					}
					else{
						panelOuter.removeAll();
						panelOuter.revalidate();
						panelOuter.repaint();
						
						JButton done = new JButton("Done!");
						panelOuter.add(done);
						
						done.addActionListener(new ActionListener(){
							@Override
							public void actionPerformed(ActionEvent arg0){
								System.exit(0);
							}
						});
					}
				}
				else{
					JOptionPane.showInputDialog("Wrong input");
				}
				
			}
		});
		
		skip.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				indexer++;
				if(indexer < files.length){
					looper(panelOuter, files, label, save, skip, whereToStore);
				}
				else{
					panelOuter.removeAll();
					panelOuter.revalidate();
					panelOuter.repaint();
					
					JButton done = new JButton("Done!");
					panelOuter.add(done);
					
					done.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0){
							System.exit(0);
						}
					});
				}
			}
		});
		
	}

	
	@Override
	public void actionPerformed(ActionEvent arg0){}

	
	@Override
	public void windowActivated(WindowEvent arg0){}

	
	@Override
	public void windowClosed(WindowEvent arg0){}

	
	@Override
	public void windowClosing(WindowEvent arg0){
		System.exit(0);
	}
	
	
	@Override
	public void windowDeactivated(WindowEvent arg0){}

	
	@Override
	public void windowDeiconified(WindowEvent arg0){}

	
	@Override
	public void windowIconified(WindowEvent arg0){}

	
	@Override
	public void windowOpened(WindowEvent arg0){}

	
}
