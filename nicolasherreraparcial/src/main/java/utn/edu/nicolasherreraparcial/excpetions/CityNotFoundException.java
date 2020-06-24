package utn.edu.nicolasherreraparcial.excpetions;

public class CityNotFoundException extends Throwable {
    
    public String getMessage() {
        return "Cities not found";
    }
}
