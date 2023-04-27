package ProducerConsumer;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;

public class ProducerConsumer{
	
	static final int  N = 3;
	
	static Semaphore notFull = new Semaphore(N);
	static Semaphore notEmpty = new Semaphore(0);
	
	static Queue<Integer> queue = new LinkedList<Integer>();
	
	
	ProducerConsumer(){
		Producer p = new Producer();
		Consumer c = new Consumer();
		
		p.start();
		c.start();
	}
	
	class Producer extends Thread {
		
		public void run() {
			int c = 1;
			for(int i = 0; i < 10; i++) {
				try {
					notFull.acquire();
				} catch (InterruptedException e) {
				}
				System.out.println("Producing item: " + c);
				queue.add(c++);
				notEmpty.release();
			}
		}

	}
	
	class Consumer extends Thread {
		
		public void run() {
			for(int i = 0; i < 10; i++) {
				try {
					notEmpty.acquire();
				} catch (InterruptedException e) {
				}
				System.out.println("Consuming item: " + queue.remove());
				notFull.release();
			}
		}

	}


	
	public static void main(String[] args) {
		new ProducerConsumer();
	}
	
}




