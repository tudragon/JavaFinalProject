package motion;

/**
 * A double pair (x,y). Used to represent: location, velocity, acceleration and force magnitude
 * @author Nguyen Minh Tu
 *
 */
public class Vector2D {

	private double x = 0f;
    private double y = 0f;    

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double magnitude() {
        return (double) Math.sqrt(x * x + y * y);
    }

    public void add(Vector2D v) {
        x += v.x;
        y += v.y;
    }
    
	public void subtract(Vector2D v) {
		this.x -= v.x;
		this.y -= v.y;		
	}
    
    /**
     * Multiply vector by a scalar amount
     * @param n
     */
    public Vector2D multiplyVector(double n) {
        return new Vector2D(this.getX()*n, this.getY()*n);
    }
    
    
    /**
     * Divide vector by a scalar amount
     * @param n
     */
    public Vector2D divideVector(double n) {
        return new Vector2D(this.getX()/n, this.getY()/n);
    }

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
    
	@Override
	public String toString() {
		return String.format("(%.1f;%.1f)", this.getX(), this.getY());
	}    
	
	/**
	 * Check if two vectors are equal in magnitude
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		//check class
		if(this.getClass() != obj.getClass()) return false;
		//same class Vector2D
		Vector2D vector = (Vector2D) obj;
		return (this.getX() == vector.getX()) && (this.getY() == vector.getY());
	}
}
