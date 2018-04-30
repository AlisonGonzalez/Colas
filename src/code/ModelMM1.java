/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

/**
 *
 * @author AlisonGonzalez
 */
public class ModelMM1 {
    // Parámetros iniciales
    public double lambda, mu, ro, p0, pn;
    public double cn;
    
    // Variables finales
    public double l, lq, w, wq;
    
    // Capacidad
    public int n;
    
    //Costo
    public double cw, cs, ct;
    
    // Modelo M/M/1
    public ModelMM1(double _lambda, double _mu, int _n, double _cw, double _cs) {
        lambda = _lambda;
        mu = _mu;
        ro = lambda / mu;
        n = _n;
        cw = _cw;
        cs = _cs;
        calculate();
        ct = calculateCost();
    }
    
    // Estabilidad del sistema
    public boolean isStable() {
        return ro > 0 && ro < 1;
    }
    
    // Cálculo de valores finales
    // M/M/1
    public void calculate(){
        if(isStable()) {
            p0 = 1 - ro;
            pn = calculatePn();
            cn = calculateCn();
            lq = lq();
            l = l();
            wq = wq();
            w = w();
        }
    }
    public double calculateCost(){
        return ((lq * cw) + cs);
    }
    
    // Lq
    private double lq() {
        return Math.pow(lambda, 2) / (mu * (mu - lambda));
    }
    // L
    private double l() {
        return lambda / (mu - lambda);
    }
    // Wq
    private double wq() {
        return lq / lambda;
    }
    
    // W
    private double w() {
        return l / lambda;
    }
    
    // Pn
    private double calculatePn() { 
        double res = (p0)*Math.pow(ro, n);
        return res;
    }
    // Cn
    private double calculateCn() {
        double res = Math.pow(ro, n);
        return res;
    }
}
