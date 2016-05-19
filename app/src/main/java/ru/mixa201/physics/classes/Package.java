package ru.mixa201.physics.classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Vector;

public class Package implements Serializable {

	private static final long serialVersionUID = 4L;
	public boolean installTests = false;
	public boolean installPages = false;
	public boolean installQuests = false;
	public String name;
	public Vector<Test> tests = new Vector<Test>();
	public Vector<Page> pages = new Vector<Page>();
	public Manifest tm = new Manifest();
	public Manifest pm = new Manifest();
	public Vector<String> toQM = new Vector<String>();// Manifest
	public Vector<Quest> toQQ = new Vector<Quest>();// Quests
	public Vector<String> toQD = new Vector<String>();// Defaults

	public Package(String name, boolean it, boolean ip, boolean iq) {
		this.name = name;
		this.installTests = it;
		this.installPages = ip;
		this.installQuests = iq;
	}

	public void install(String APP_PATH) throws IOException,
			ClassNotFoundException {
		File f = new File(APP_PATH + "packages.pmf");
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
		Manifest main = (Manifest) in.readObject();
		String[] paths=new String[main.fileNames.size()];
		for(int i=0;i<paths.length;i++){
			paths[i]=main.fileNames.get(i);
		}
		Arrays.sort(paths);
		if(Arrays.binarySearch(paths, name)<0){
			main.registerFile(name);
		}

		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
		out.writeObject(main);
		out.close();
		in.close();
		if (installTests) {
			f = new File(APP_PATH + "/tests/" + name + "/");
			f.mkdirs();
			f = new File(APP_PATH + "/tests/" + name + "/main.pmf");
			f.createNewFile();
			out = new ObjectOutputStream(new FileOutputStream(f));
			out.writeObject(tm);
			out.close();
			for (int i = 0; i < tests.size(); i++) {
				f = new File(APP_PATH + "/tests/" + name + "/"
						+ tests.get(i).name + ".ptf");
				out = new ObjectOutputStream(new FileOutputStream(f));
				out.writeObject(tests.get(i));
				out.close();
			}
		}
		if (installPages) {
			f = new File(APP_PATH + "/pages/" + name + "/");
			f.mkdirs();
			f = new File(APP_PATH + "/pages/" + name + "/main.pmf");
			f.createNewFile();
			out = new ObjectOutputStream(new FileOutputStream(f));
			out.writeObject(pm);
			out.close();
			for (int i = 0; i < pages.size(); i++) {
				f = new File(APP_PATH + "/pages/" + name + "/"
						+ pages.get(i).name + ".ppf");
				out = new ObjectOutputStream(new FileOutputStream(f));
				out.writeObject(pages.get(i));
				out.close();
			}
		}
	}
	
	public void addTest(Test t){
		tests.add(t);
		tm.registerFile(t.name);
	}
	public void addPage(Page p){
		pages.add(p);
		pm.registerFile(p.name);
	}
}
