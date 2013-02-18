package tp.pr3.cityLoader.cityLoaderExceptions;

import java.io.IOException;

public class WrongCityFormatException extends IOException{

	public WrongCityFormatException() {
		// TODO Auto-generated constructor stub
	}
	
	public WrongCityFormatException(String message) {
		this.message = message;
	}
	
	public String getMessage(){
		return this.message;
	}
	
	private String message;
}
