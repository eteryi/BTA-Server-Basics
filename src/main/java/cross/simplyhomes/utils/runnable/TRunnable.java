package cross.simplyhomes.utils.runnable;

abstract public class TRunnable implements Runnable {
	 @Override
	 abstract public void run();

	 private Thread thread;
	 public TRunnable start() {
		 thread = new Thread(this);
		 thread.start();
		 return this;
	 }

	 public void cancel() {
		 if (thread == null) return;
		 thread.stop();
	 }
 }
