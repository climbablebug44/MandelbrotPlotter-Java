import java.awt.*;
import java.util.ArrayList;




public class mandelbrotDeviationCalculator {
    private final ArrayList<Color> colors;

    public mandelbrotDeviationCalculator() {
        colors = new ArrayList<>();
        generateTriplets();
    }

    private triplet<Integer> generate(int deviation) {
        return switch (deviation) {
            case 0 -> new triplet<>(66, 30, 15);
            case 1 -> new triplet<>(25, 7, 26);
            case 2 -> new triplet<>(9, 1, 47);
            case 3 -> new triplet<>(4, 4, 73);
            case 4 -> new triplet<>(0, 7, 100);
            case 5 -> new triplet<>(12, 44, 138);
            case 6 -> new triplet<>(24, 82, 117);
            case 7 -> new triplet<>(57, 125, 209);
            case 8 -> new triplet<>(134, 181, 229);
            case 9 -> new triplet<>(211, 236, 248);
            case 10 -> new triplet<>(241, 233, 191);
            case 11 -> new triplet<>(248, 201, 95);
            case 12 -> new triplet<>(255, 170, 0);
            case 13 -> new triplet<>(204, 128, 0);
            case 14 -> new triplet<>(153, 87, 0);
            case 15 -> new triplet<>(106, 52, 3);
            default -> new triplet<>(0, 0, 0);
        };
    }

    private void generateTriplets() {
        for (int i = 0; i < 17; i++) {
            triplet<Integer> temp = generate(i);
            colors.add(new Color(temp.getx(),temp.gety(),temp.getz()));
        }
    }

    public Color mandPlot(final double compX, final double compY, final int maxIter) {
        int deviation = 0;
        double zRe = 0, zComp = 0;
        while (deviation <= maxIter && ((zRe * zRe) + (zComp * zComp)) < 4.0) {
            final double temp = (zRe * zRe) - (zComp * zComp) + compX;
            zComp = (2.0 * (zRe * zComp)) + compY;
            zRe = temp;
            deviation++;
        }
        if(deviation==256)
            return colors.get(16);
        return colors.get(deviation%16);
    }
}
