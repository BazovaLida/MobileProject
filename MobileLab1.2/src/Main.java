public class Main {
    public static void main(String[] args) {
        CoordinateLB coord1 = new CoordinateLB(Direction.Latitude);
        coord1.setDegrees(38);
        coord1.setSeconds(59);
        CoordinateLB coord2 = new CoordinateLB(Direction.Longitude, 0);
        coord2.setMinutes(2);
        CoordinateLB coord3 = new CoordinateLB(Direction.Latitude, 12, 31);
        CoordinateLB coord4 = new CoordinateLB(Direction.Longitude, 167, 47, 2);

        System.out.println("Координата 1:  " + coord1.toString() + "; хвилини задані за замовчуванням (через конструктор)");
        System.out.println("Координата 1:  " + coord1.toStringDec());
        System.out.println("Координата 2:  " + coord2.toString());
        System.out.println("Координата 3:  " + coord3.toString());
        System.out.println("Координата 4:  " + coord4.toString());

        CoordinateLB coord5 = coord2.middleWith(coord4);
        CoordinateLB coord6 = coord1.middleWith(coord3);
        System.out.println("Координата 5:  " + coord5.toString() + " (середнє між координатами 2 та 4)");
        System.out.println("Координата 6:  " + coord6.toString() + " (середнє між координатами 3 та 1)");

    }
}
