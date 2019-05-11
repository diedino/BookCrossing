package krasnov.bookcrossing;
/*
* Decided do not use this class, but it leaves for memory
* by diedino
* */
public class Author {
    private String name, surname, patronymic;

    public Author(String name, String surname, String patronymic) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String toString() {
        if (getPatronymic().isEmpty() && getName().isEmpty())
                return getSurname();
        if (getPatronymic().isEmpty() && !getName().isEmpty())
            return String.valueOf(getName().charAt(0))+
                    "."+" "+getSurname();
        if (!getPatronymic().isEmpty() && getName().isEmpty())
            return getSurname();
        return String.valueOf(getName().charAt(0)) +
                "." +
                String.valueOf(getPatronymic().charAt(0)) +
                "." +
                " " + getSurname();
    }
}
