import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class NoteRpad {
	
	JFrame f;
	JMenuBar mbr;
	JTextArea txtarea;
	
	NoteRpad() {
		f = new JFrame("NoteRpad");
		txtarea = new JTextArea(10,10);
		mbr = new MenuBar().mbr;
		
		JScrollPane scrollableTextArea = new JScrollPane(txtarea);
		
		scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		
		
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				switch(JOptionPane.showConfirmDialog(f, "Exit?")) {
					case 0: {
						e.getWindow().dispose();
						break;
					}
					
					case 1: {
						f.setDefaultCloseOperation(0);
						break;
					}
					
					case 2: {
						f.setDefaultCloseOperation(0);
						break;
					}
					
					default: {
						f.setDefaultCloseOperation(0);
						break;
					}
				}
			}
		});
		f.setPreferredSize(new Dimension(600,600));
		f.pack();
		f.add(scrollableTextArea, BorderLayout.CENTER);
		f.setJMenuBar(mbr);
		f.setVisible(true);
	}
	
	
	public class MenuBar {
		
		JMenuBar mbr;
		FileOperations fileOperation = new FileOperations();
		
		MenuBar() {
			mbr = new JMenuBar();
			
			JMenu file = createMenu("File", KeyEvent.VK_F);
			JMenu edit = createMenu("Edit", KeyEvent.VK_E);
			JMenu help = createMenu("Help", KeyEvent.VK_H);
			
			/// file manipulations
			
			createMenuItem("New", file, KeyEvent.VK_N);
			createMenuItem("Open", file, KeyEvent.VK_O);
			createMenuItem("Save", file, KeyEvent.VK_S);
			
			
			//edit file
			
			createMenuItem("Cut", edit, KeyEvent.VK_X);
			createMenuItem("Copy", edit, KeyEvent.VK_C);
			createMenuItem("Paste", edit, KeyEvent.VK_V);
			createMenuItem("Select All", edit, KeyEvent.VK_A);
			createMenuItem("Background Color", edit, KeyEvent.VK_F2);
			createMenuItem("Font Color", edit, KeyEvent.VK_F3);
			
			//help
			
			createMenuItem("Version", help, KeyEvent.VK_F1);
			
			mbr.add(file); mbr.add(edit); mbr.add(help);
		}
		
		public JMenu createMenu(String name, int key) {
			JMenu temp = new JMenu(name);
			temp.setMnemonic(key);
			
			return temp;
		}
		
		public void createMenuItem(String name, JMenu toMenu, int key) {
			JMenuItem temp = new JMenuItem(name);
			temp.setMnemonic(key);
			temp.addActionListener(fileOperation);
			toMenu.add(temp);
		}
	}
	
	public class FileOperations implements ActionListener {
		
		public void changeBackgroundColor() {
			Color initialColor = Color.white;
			Color color = JColorChooser.showDialog(f, "Backgeound Color", initialColor);
			txtarea.setBackground(color);
		}
		
		public void changeFontColor() {
			Color initialColor = Color.black;
			Color color = JColorChooser.showDialog(f, "Font Color", initialColor);
			txtarea.setForeground(color);
		}
		
		public void SaveFile() {
			JFileChooser filechooser = new JFileChooser();
			filechooser.setCurrentDirectory(new File("."));
			int FileChooserMessage = filechooser.showSaveDialog(f);
			
			if (FileChooserMessage == JFileChooser.APPROVE_OPTION) {
				File file = filechooser.getSelectedFile();
				try {
					FileWriter fileW = new FileWriter(file); 
					fileW.write(txtarea.getText());
					fileW.close();
				} 
				catch (Exception ex) {
					JOptionPane.showMessageDialog(f, ex);
				}
				JOptionPane.showMessageDialog(f, "File saved at " + file.getPath());
			}
		}
		
		public void OpenFile() {
			JFileChooser filechooser = new JFileChooser();
			filechooser.setCurrentDirectory(new File("."));
			int FileChooserMessage = filechooser.showOpenDialog(f);
			
			if (FileChooserMessage == JFileChooser.APPROVE_OPTION) {
				File file = filechooser.getSelectedFile();
				
				try {
					BufferedReader br = new BufferedReader(new FileReader(file));
					String s1="", s2="";
					while ((s1=br.readLine())!=null) {
						s2 += s1 + "\n";
					}
					txtarea.setText(s2);
					br.close();
					
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(f, ex);
				}
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
				case "New": {
					switch(JOptionPane.showConfirmDialog(f, "Save this File")) {
						case 0: {
							SaveFile();
							txtarea.setText(null);
							break;
						}
						
						case 1: {
							txtarea.setText(null);
							break;
						}
						
						case 2: {
							break;
						}
							
						
					}
					break;
				}
				
				case "Open": {
					OpenFile();
					break;
				}
				
				case "Save": {
					SaveFile();
					break;
				}
				
				case "Cut": {
					txtarea.cut();
					break;
				}
				
				case "Copy": {
					txtarea.copy();
					break;
				}
				
				case "Paste": {
					txtarea.paste();
					break;
				}
				
				case "Select All": {
					txtarea.selectAll();
					break;
				}
				
				case "Background Color": {
					changeBackgroundColor();
					break;
				}
				
				case "Font Color": {
					changeFontColor();
					break;
				}
				
				case "Version": {
					JOptionPane.showMessageDialog(f, 
												  "Version: 1.0.0", 
												  "Version", 
												  JOptionPane.INFORMATION_MESSAGE);
					break;
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + e.getActionCommand());
				}
				
		}

	}
	
	public static void main(String[] args) {
		new NoteRpad();
	}
}


