package lt.mikasdu;

public enum MeasureUnit {
    UNKNOWN(0, "Nenustatytas"),
    KILOGRAM(1, "Kilogramas"),
    LITRE(2, "Litras"),
    UNIT(3, "Vienetas");

    private int id;
    private String name;

    MeasureUnit(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
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

    public static MeasureUnit[] measureUnitsList() {
        return new MeasureUnit[]{KILOGRAM, LITRE, UNIT};
    }

    public static MeasureUnit getById(int id) {
        for(MeasureUnit measure: values()) {
            if(measure.getId() == id) return measure;
        }
        return UNKNOWN;
    }



}
