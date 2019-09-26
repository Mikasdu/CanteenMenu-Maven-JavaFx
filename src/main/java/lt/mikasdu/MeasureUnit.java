package lt.mikasdu;

public enum MeasureUnit {
    KILOGRAM(1, "Kilogramas"),
    LITRE(2, "Litras"),
    UNIT(3, "Vienetas");

    int id;
    String name;

    MeasureUnit(int i, String name) {
        setId(i);
        setName(name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
