package com.epam.rd.autotasks.segments;
import java.awt.geom.Line2D;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
import static java.lang.Math.min;
import static java.lang.Math.max;
import static java.lang.StrictMath.pow;

class Segment {
    public Point start;
    public Point end;

    public Segment(Point start, Point end) {
        if(start.getY()==end.getY() && start.getY()== end.getY()){
            throw new IllegalArgumentException();
        }
        this.start = start;
        this.end = end;
    }

    double length() {
        double length= sqrt(pow(end.getX()-start.getX(),2)+pow(end.getY()-start.getY(),2));
        return length;
    }

    Point middle() {
        double x = (end.getX()+start.getX())/2;
        double y = (end.getY()+start.getY())/2;
        Point middle = new Point(x, y);
        return middle;

    }

    double vectorProduct(Point X, Point Y, Point Z){
        double x = Z.getX() - X.getX();
        double y = Z.getY() - X.getY();
		double x2 = Y.getX() - X.getX();
        double y2 = Y.getY() - X.getY();
	    return x*y2 - x2*y;
    }

    boolean check(Point X, Point Y, Point Z){
        boolean check = min(X.getX(), Y.getX()) <= Z.getX() && Z.getX() <= max(X.getX(), Y.getX()) 
		&& min(X.getY(), Y.getY()) <= Z.getY() && Z.getY() <= max(X.getY(), Y.getY());
        return check;
    }

    Point intersection(Segment another) {

        double vector1 = vectorProduct(another.start, another.end, this.start);
        double vector2 = vectorProduct(another.start, another.end, this.end);
        double vector3 = vectorProduct(this.start, this.end, another.start);
        double vector4 = vectorProduct(this.start, this.end, another.end);

        //checking if the lines intersect
        if(((vector1>0 && vector2<0 || vector1<0 && vector2>0) && ( vector3>0 && vector4<0 || vector3<0 && vector4>0)) || 
            (vector1 == 0 && check(another.start,another.end,this.start)) || //checking if the first point is on the second segment
            (vector2 == 0 && check(another.start,another.end,this.end)) ||
            (vector3 == 0 && check(this.start,this.end,another.start)) ||
            (vector4 == 0 && check(this.start,this.end,another.end))){

                double Ax = this.start.getX();
                double Ay = this.start.getY();
                double Bx = this.end.getX();
                double By = this.end.getY();
                double Cx = another.start.getX();
                double Cy = another.start.getY();
                double Dx = another.end.getX();
                double Dy = another.end.getY();
                
                //checking for division by zero
                if(((By-Ay)*(Dx-Cx)-(Dy-Cy)*(Bx-Ax)==0) || ((Dy-Cy)*(Bx-Ax)-(By-Ay)*(Dx-Cx)==0)){
                    return null;
                }
                else{
                    double x=((Bx-Ax)*(Dx*Cy-Dy*Cx)-(Dx-Cx)*(Bx*Ay-By*Ax))/((By-Ay)*(Dx-Cx)-(Dy-Cy)*(Bx-Ax));
                    double y=((Dy-Cy)*(Bx*Ay-By*Ax)-(By-Ay)*(Dx*Cy-Dy*Cx))/((Dy-Cy)*(Bx-Ax)-(By-Ay)*(Dx-Cx));
                    return new Point(x, y);
                }       
            }
            else{
                return null;
            }
    }
}
