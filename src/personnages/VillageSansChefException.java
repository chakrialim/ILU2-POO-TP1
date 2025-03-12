package personnages;

public class VillageSansChefException extends Exception {
	public VillageSansChefException() {

	}
	public VillageSansChefException(String message) {
		super(message);
	}
	public VillageSansChefException(Throwable cause) {
		super(cause);
	}
	public VillageSansChefException(String message, Throwable cause) {
		super(message, cause);
	}
}
