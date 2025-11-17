package onlinestore;

public enum ProductType {
    Electronics,
    Clothes,
    Shoes,
    Books,
    Beauty;

    @Override
    public String toString() {
        switch (this) {
            case Electronics -> {
                return "Электроника";
            }
            case Clothes -> {
                return "Одежда";
            }
            case Shoes -> {
                return "Обувь";
            }
            case Books -> {
                return "Книги";
            }
            case Beauty -> {
                return "Красота";
            }
            default -> {
                return null;
            }
        }
    }
}
