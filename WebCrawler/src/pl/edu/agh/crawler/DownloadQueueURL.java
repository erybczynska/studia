package pl.edu.agh.crawler;

import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;

/* Klasa obsługująca kolejkę stron do odwiedzenia */
public class DownloadQueueURL implements DownloadQueue {
	private Queue<URL> queue = new LinkedList<>();

	/*
	 * Metoda dodająca stronę do kolejki
	 * 
	 * @param adres strony do dodania
	 */
	@Override
	public void addPage(URL pageURL) {
		queue.add(pageURL);
	}

	/*
	 * Metoda sprawdzająca czy kolejka stron do odwiedzenia jest pusta
	 * 
	 * @return zwraca true gdy kolejka jest pusta, w przeciwnym razie zwraca
	 * false
	 */
	@Override
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	/*
	 * Metoda zwracająca URL początku kolejki
	 *
	 * @return adres url strony, która jest pierwsza w kolejce
	 */
	@Override
	public URL getNextPage() {
		return queue.poll();
	}

	/*
	 * Metoda sprawdzająca długość kolejki
	 * 
	 * @return długość kolejki
	 */
	@Override
	public int size() {
		return queue.size();
	}

}
