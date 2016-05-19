package ru.mixa201.physics;

import android.os.AsyncTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Vector;
import ru.mixa201.physics.classes.Package;

public class Client {
	public static String ip;
	private String path;
	public static Package pack;
	public static String name1;

	public Client(String ip, String path) {
		this.ip = ip;
	}

	public pack[] getAvailablePackages() throws IOException {
		Vector<pack> v = new Vector<pack>();

		URLConnection url = new URL("http://" + ip + "/packages.psmf")
				.openConnection();
		Scanner in = new Scanner(url.getInputStream());
		try {
			String name= in.nextLine();
			int version = in.nextInt();
			v.add(new pack(name, version));
			in.nextLine();
		while (name != null) {
			v.add(new pack(name, version));
				name = in.nextLine();
				version = in.nextInt();
		}
		} catch (NoSuchElementException e){

		}

		pack[] pcks = new pack[v.size()];
		for (int i = 0; i < pcks.length; i++) {
			pcks[i] = v.get(i);
		}
		return pcks;
	}

	public Package getPackage(String name)
			throws IOException, ClassNotFoundException {
			name1=name;
			AsyncTask t=new AsyncTask() {
				@Override
				protected Object doInBackground(Object[] params) {
					URLConnection url = null;
					try {
						url = (HttpURLConnection) new URL("http://" + Client.ip + "/"
								+ Client.name1 + ".pipf").openConnection();
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						ObjectInputStream in=new ObjectInputStream(url.getInputStream());
						pack=(Package) in.readObject();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					return null;
				}
			};
		t.execute();

		while(pack==null){

		}


		return pack;
	}


}

