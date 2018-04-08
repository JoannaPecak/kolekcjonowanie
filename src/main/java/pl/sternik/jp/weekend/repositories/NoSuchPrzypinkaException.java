package pl.sternik.jp.weekend.repositories;

public class NoSuchPrzypinkaException extends Exception {
    private static final long serialVersionUID = -8555511053844242536L;

    public NoSuchPrzypinkaException(String string) {
		super(string);
	}

	public NoSuchPrzypinkaException() {
	}


}
