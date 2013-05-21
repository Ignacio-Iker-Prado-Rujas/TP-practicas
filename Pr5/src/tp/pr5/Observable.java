package tp.pr5;

import java.util.ArrayList;

public class Observable<T> {
	// Añade un observador a la clase
	public void addObserver(T observer) {
		arrayObservers.add(observer);
	}

	// Elimina un observador de la clase
	public void removeObserver(T observer) {
		arrayObservers.remove(observer);
	}

	ArrayList<T> arrayObservers;
}
