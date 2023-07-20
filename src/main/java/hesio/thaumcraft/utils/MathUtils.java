package hesio.thaumcraft.utils;

import org.joml.Matrix4f;
import org.joml.Vector3d;
import org.joml.Vector4f;

public class MathUtils {

    /**
     * Performs linear interpolation between two values.
     *
     * @param v0 the starting value
     * @param v1 the ending value
     * @param t  the interpolation parameter (between 0 and 1)
     * @return   the interpolated value
     */
    public static float lerp(float v0, float v1, float t){
        return (1.0f - t) * v0 + t * v1;
    }

    /**
     * Performs linear interpolation between two double values.
     *
     * @param v0 the starting value
     * @param v1 the ending value
     * @param t  the interpolation parameter (between 0 and 1)
     * @return   the interpolated value
     */
    public static double lerpDouble(double v0, double v1, double t){
        return (1.0 - t) * v0 + t * v1;
    }

    /**
     * Clamps a value between a minimum and a maximum.
     *
     * @param value the value to clamp
     * @param min   the minimum value
     * @param max   the maximum value
     * @return      the clamped value
     */
    public static float clamp(float value, float min, float max){
        return Math.max(Math.min(max, value), min);
    }

    /**
     * Checks if a string represents a numeric value.
     *
     * @param str the string to check
     * @return    {@code true} if the string is numeric, {@code false} otherwise
     */
    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  // match a number with optional '-' and decimal
    }

    /**
     * Checks if a string represents an integer value.
     *
     * @param str the string to check
     * @return    {@code true} if the string is an integer, {@code false} otherwise
     */
    public static boolean isInteger(String str){
        return str.matches("^([+-]?[1-9]\\d*|0)$");
    }

    /**
     * Transforms a 3D vector using a 4x4 matrix.
     *
     * @param mat    the transformation matrix
     * @param vecIn  the input vector
     * @return       the transformed vector
     */
//    public static Vector3d getTranslation(Matrix4f mat, Vector3d vecIn){
//        Vector4f vec = new Vector4f((float)vecIn.x, (float)vecIn.y, (float)vecIn.y(), 1.0f);
//        vec.transform(mat);
//        return new Vector3d(vec.x, vec.y, vec.y);
//    }

    /**
     * Calculates the offset for the arctangent function based on the given coordinates.
     *
     * @param y the y-coordinate
     * @param x the x-coordinate
     * @return  the offset for the arctangent function
     */
    public static double getAtanOffset(double y, double x){
        // Explanation of offset calculation...
        if (x > 0){
            return 0.0;
        } else if (y >= 0 && x < 0){
            return Math.PI;
        } else if (y < 0 && x < 0){
            return -Math.PI;
        } else if (y > 0 && x == 0){
            return Math.PI / 2.0;
        } else if (y < 0 && x == 0){
            return -Math.PI / 2.0;
        }
        return 0.0;
    }

    /**
     * Calculates the angle around the Y-axis based on the given coordinates.
     *
     * @param zCoord the z-coordinate
     * @param xCoord the x-coordinate
     * @return       the angle around the Y-axis
     */
    public static double getAngleAroundYAxis(double zCoord, double xCoord){
        double value = Math.atan2(zCoord, xCoord);
        if (value < 0){
            value += (2.0 * Math.PI);
        }
        return value;
    }

    /**
     * Calculates the cosine value from the sine value and angle.
     *
     * @param sin   the sine value
     * @param angle the angle in radians
     * @return      the cosine value
     */
    public static double cosFromSin(double sin, double angle) {
        double cos = Math.sqrt(1.0 - sin * sin);
        double a = angle + (Math.PI / 2.0);
        double b = a - (int)(a / (2.0 * Math.PI)) * 2.0 * Math.PI;
        if (b < 0.0)
            b = 2.0 * Math.PI + b;
        if (b >= Math.PI)
            return -cos;
        return cos;
    }
}