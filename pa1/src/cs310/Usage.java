package cs310;

// One user's record on one line: how many times
// this user has been seen on this line
public class Usage {
	private String user;
	private int count;

	public Usage(String x) {
		user = x;
		count = 0;
	}

	public void increment() {
		count++;
	}

	public String getUser() {
		return user;
	}

	public int getCount() {
		return count;
	}
}
