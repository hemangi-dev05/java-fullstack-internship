package polymorphism;

public class Printer {

    public String show(Animal a) {
        return "show(Animal) was chosen";
    }

    public String show(Dog d) {
        return "show(Dog) was chosen";
    }
}