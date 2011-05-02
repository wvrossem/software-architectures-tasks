/**
 * SoftLibrarySoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package be.ac.vub.soft;

import java.rmi.RemoteException;

import org.apache.axis.types.Language;

public class SoftLibrarySoapBindingImpl implements SoftLibraryPortType{
    public Book[] getAllBooks(Object all) throws RemoteException {
        Book[] result = new Book[2];
        
        Book b = new Book();
		b.setAuthor("William Sleator");
		b.setIsbn(810993562);
		b.setLanguage(new Language("en"));
		b.setPublisher("Amulet Books");
		b.setTitle("Test");
		b.setYear(2008);
		
		result[0] = b;
		
		b = new Book();
		b.setAuthor("Douglas Adams");
		b.setIsbn(345453743);
		b.setLanguage(new Language("en"));
		b.setPublisher("Del Rey");
		b.setTitle("The Ultimate Hitchhiker's Guide to the Galaxy");
		b.setYear(2002);
		
		result[1] = b;
		
		return result;
    }

    public Book[] searchBooks(String query) throws RemoteException {
        return getAllBooks(null);
    }

}
