package vollmed.models.dog;

public record ListDataDog(String name, String color) {
    // Implements the contructor method
    public ListDataDog(Dog d) {
        this(d.getName(), d.getColor());
    }

}
