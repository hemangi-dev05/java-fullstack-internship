package polymorphism;

public class Animal {

    protected final String name;

    public Animal(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String makeSound() {
        return "Some generic animal sound";
    }

    public String move() {
        return name + " moves";
    }

    public void describe() {
        System.out.println(name + " (" + getClass().getSimpleName() + ") says: " + makeSound());
    }
}