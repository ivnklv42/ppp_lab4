package onlinestore;

public class InputCheck {
    public static boolean productTitleCheck(String title) {
        if (title.length() > 30 || title.isEmpty()) {
            return false;
        }

        if (!title.matches("[\\wа-яА-ЯёЁ\\p{Punct} ]+")) {
            return false;
        }

        return true;
    }

    public static boolean productPriceCheck(String strPrice) {
        double price;
        try {
            price = Double.parseDouble(strPrice);
        } catch (NumberFormatException e) {
            return false;
        }

        if (price < 1 || price > 5_000_000) {
            return false;
        }

        return true;
    }

    public static boolean orderPriceCheck(String strPrice) {
        double price;
        try {
            price = Double.parseDouble(strPrice);
        } catch (NumberFormatException e) {
            return false;
        }

        if (price < 1 || price > 1_000_000_000) {
            return false;
        }

        return true;
    }

    public static boolean customerCheck(String name) {
        if (name.length() > 30 || name.length() < 3) {
            return false;
        }

        if (!name.matches("[A-ZА-ЯЁ][a-zа-яё]* [A-ZА-ЯЁ][a-zа-яё]*")) {
            return false;
        }

        return true;
    }

    public static boolean amountCheck(String amount) {
        int intAmount = 0;
        try {
            intAmount = Integer.parseInt(amount);
            if (intAmount < 1 || intAmount > 10000) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
