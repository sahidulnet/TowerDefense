package common.core;

import java.io.Serializable;

/**
 * Represents a displacement in 2-D space.
 * @version $revision $
 */
public class Vector2 implements Serializable {

    private double x;
    private double y;

    /**
     * Initializes a new instance of the Vector structure with x = 0 and y = 0
     */
    public Vector2() {
        x = 0;
        y = 0;
    }

    /**
     * Initializes a new instance of the Vector structure.
     *
     * @param x The Vector.x-offset of the new Vector.
     * @param y The Vector.y-offset of the new Vector.
     */
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the value of X.
     * @return Returns the X value of the vector.
     */
    public double getX() {
        return x;
    }

    /**
     * Set the value of X.
     * @param x The value to set.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Get the value of Y.
     * @return Returns the Y value of the vector.
     */
    public double getY() {
        return y;
    }

    /**
     * Set the value of Y.
     * @param y The value to set.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Set the value of X and Y.
     * @param x The value to set.
     * @param y The value to set.
     */
    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Set the value of X and Y from vector.
     * @param vector The vector to set.
     */
    public void setFromVector(Vector2 vector) {
        this.x = vector.getX();
        this.y = vector.getY();
    }

    /**
     * Creates a zero vector.
     *
     * @return  A vector with zero value of Vector.x and Vector.y.
     */
    public static Vector2 getZero() {
        return new Vector2(0, 0);
    }

    /**
     * Gets the length of this vector.
     *
     * @return Returns the square of the length of this vector.
     */
    public double getLength() {
        return x * x + y * y;
    }

    /**
     * Normalizes this vector.
     */
    public void normalize() {
        double length = this.getLength();
        x /= length;
        y /= length;
    }

    /**
     * Adds two vectors.
     *
     * @param vector   The vector to add.
     */
    public void add(Vector2 vector) {
        this.x += vector.x;
        this.y += vector.y;
    }

    /**
     * Adds scalar to the current vector.
     *
     * @param scalar   The scalar to add.
     */
    public void add(double scalar) {
        this.x += scalar;
        this.y += scalar;
    }

    /**
     * Subtracts two vectors.
     *
     * @param vector   The vector to subtract.
     */
    public void subtract(Vector2 vector) {
        this.x -= vector.x;
        this.y -= vector.y;
    }

    /**
     * Subtracts scalar from the current vector.
     *
     * @param scalar   The scalar to subtract.
     */
    public void subtract(double scalar) {
        this.x -= scalar;
        this.y -= scalar;
    }

    /**
     * Calculates dot product of two vectors.
     *
     * @param vector   The vector to multiply.
     */
    public void multiply(Vector2 vector) {
        this.x *= vector.x;
        this.y *= vector.y;
    }

    /**
     * Multiply scalar to the current vector.
     *
     * @param scalar   The scalar to multiply.
     */
    public void multiply(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
    }

    /**
     * Divides the current vector by the specified scalar.
     *
     * @param scalar   The scalar to divide.
     */
    public void divide(double scalar) {
        this.x /= scalar;
        this.y /= scalar;
    }

    /**
     * Negates this vector.
     */
    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }

    /**
     * Indicate whether the vector has zero value or not.
     * @return Returns true if vector is zero otherwise false.
     */
    public boolean isZero() {
        return x == 0 & y == 0;
    }

    /**
     * Compares two vectors for equality.
     *
     * @param vector The vector to compare with this vector.
     * @return true if value has the same Vector.x and Vector.y values as this vector; otherwise, false.
     */
    public boolean equals(Vector2 vector) {
        return (this.x == vector.x && this.y == vector.y);
    }

    /**
     * Returns the string representation of this Vector.
     *
     * @return A string that represents the Vector.x and Vector.y values of this Vector.
     */
    @Override
    public String toString() {
        return String.valueOf(x) + "," + String.valueOf(y);
    }




    /**
     * Utility method to calculate distance between two vectors
     *
     * @param source Source vector to calculate distance from
     * @param destination Destination vector to calculate distance to
     * @return Distance between both vectors
     */
    public static double Distance(Vector2 source, Vector2 destination){
        double distanceX = destination.getX() - source.getX();
        double distanceY = destination.getY() - source.getY();
        return Vector2.Magnitude(new Vector2(distanceX, distanceY));

    }

    /**
     * Calculates the magnitude of the vector2
     * @param vector2 Target to calculate magnitude for
     * @return Magnitude of the vector2
     */
    public static double Magnitude(Vector2 vector2) {
        double ret = Math.pow(vector2.getX(), 2) + Math.pow(vector2.getY(), 2);
        return Math.sqrt(ret);
    }


    /**
     * Method for knowing distance
     *
     * @param destination represents the destination
     * @return Returns the destination
     */
    public double distanceTo(Vector2 destination){
        return Distance(this, destination);
    }

    /**
     * Method for getting magnitide
     *
     * @return returns the magnitude
     */
    public double getMagnitude(){
        return Magnitude(this);
    }
}
