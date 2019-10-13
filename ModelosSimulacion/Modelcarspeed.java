
import xfuzzy.PlantModel;

public class Modelcarspeed implements PlantModel {
 private double x;
 private double y;
 private double phi;
 private double error_v;
 private double vref=1.0;
 private double v;
 private double oldgamma;
 private double gamma;
 private double acel;

 public Modelcarspeed() {
 }

 public void init() {
  x = 0;
  phi = 0;
  y = 0;
  error_v = 0;
 }

 public void init(double val[]) {
  x = val[0];
  phi = val[1]*Math.PI/180;
  y = val[2];
  error_v = val[3];
 }

 public double[] state() {
  double state[] = new double[4];
  state[0] = x;
  state[1] = phi*180/Math.PI;
  state[2] = y;
  state[3] = error_v;
  return state;
 }

 public double[] compute(double val[]) {
  double LAPSE = 0.1;
  double P_TAU = 0.5; // 1.8
  double t = 0.0;
  double gref = 0.0;
  double acel = 1.0*val[0];
  oldgamma = gamma;
  v = vref - error_v;

  for(t=0.0; t <= LAPSE; t+=0.001) {
    x += v*Math.sin(phi)*0.001;
    y += v*Math.cos(phi)*0.001;
    phi += v*gamma*0.001;
    if( phi > Math.PI) phi -= 2*Math.PI;
    if( phi < -Math.PI) phi += 2*Math.PI;
    gamma = gref + (oldgamma-gref)*Math.exp(-t/P_TAU);
    if( gamma > 0.4) gamma = 0.4;
    if( gamma < -0.4) gamma = -0.4;
    v += acel*0.001;
  }
  error_v = vref - v;
  return state();
 }
}

