package util;

public class AreaChecker {
    double x;
    double y;
    double r;

    public boolean check(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
        if (validateVars()) {
            return validateCircle() ||
                    validateRectangle() ||
                    validateTriangle();
        }
        return false;
    }

    private boolean validateVars() {
        return isVarInRange(x, -4, 4) &&
                isVarInRange(y, -5, 3) &&
                isVarInRange(r, 1, 4);
    }

    private boolean validateRectangle() {
        return isVarInRange(x, -r, 0) &&
                isVarInRange(y, -r / 2, 0);
    }

    private boolean validateTriangle() {
        // kx + b < y
        return isVarInRange(x, 0, r / 2) &&
                isVarInRange(y, -r, 0) &&
                x * 2 - r <= y;
    }

    private boolean validateCircle() {
        // x^2 + y^2 < r - формула круга + ограничение чтобы сделать его четвертью
        return pow(x) + pow(y) < pow(r) && x > 0 && y > 0;
    }

    private boolean isVarInRange(double var, double min, double max) {
        return var >= min && var <= max;
    }

    private double pow(double var) {
        return var * var;
    }
}
