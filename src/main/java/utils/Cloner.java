package utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import base.LogManager;

public class Cloner {
	// so that nobody can accidentally create an ObjectCloner object
			private Cloner() {
			}

			// returns a deep copy of an object
			public static Object deepCopy(Object object) {
				ObjectOutputStream oos = null;
				ObjectInputStream ois = null;
				try {
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					oos = new ObjectOutputStream(bos);
					// serialize and pass the object
					oos.writeObject(object);
					oos.flush();
					ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
					ois = new ObjectInputStream(bin);
					// return the new object
					return ois.readObject();
				} catch (Exception e) {
					LogManager.verifyAssert(false,"Exception in ObjectCloner = " + e);
				} finally {
					try {
						oos.close();
					} catch (IOException e) {
					}
					try {
						ois.close();
					} catch (IOException e) {
					}
				}
				return null;
			}
}
