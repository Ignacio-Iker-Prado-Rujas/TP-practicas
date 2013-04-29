package tp.pr5.cityLoader.cityLoaderExceptions;

import java.io.IOException;

public class WrongCityFormatException extends IOException {



	public WrongCityFormatException() {
		// TODO Auto-generated constructor stub
	}

	public WrongCityFormatException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	private String message;
	private static final long serialVersionUID = 1L;	//Daba warning
}
