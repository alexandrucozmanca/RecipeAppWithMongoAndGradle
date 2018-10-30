package ro.alex.learning.RecipeApplication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RecipeNumberFormatException extends RuntimeException {

    public RecipeNumberFormatException(){
            super();
        }

    public RecipeNumberFormatException(String message){
            super(message);
        }

    public RecipeNumberFormatException(String message, Throwable cause){
            super(message,cause);
        }

}
