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
public class ModelMG1 {
    // Parámetros iniciales
    public double lambda, mu, ro, p0, pn, sigma;
    public double cn;
    
    // Variables finales
    public double l, lq, w, wq;
    
    // Capacidad
    public int n;
    
    // Modelo M/G/1
    public ModelMG1(double _lambda, double _mu, int _n, double _sigma) {
        lambda = _lambda;
        mu = _mu;
        ro = lambda / mu;
        n = _n;
        sigma = _sigma;
        calculate();
    }
    
    
    // Estabilidad del sistema
    public boolean isStable() {
        return ro > 0 && ro < 1;
    }
    
    // Cálculo de valores finales
    // M/G/1
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
    
    
    // Lq
    private double lq() {
        double num = Math.pow(lambda, 2) * Math.pow(sigma, 2) + Math.pow(ro, 2);
        double denum = (2 * (1 - ro));
        return num / denum;
    }
    // L
    private double l() {
        return ro + lq;
    }
    // Wq
    private double wq() {
        return lq / lambda;
    }
    
    // W
    private double w() {
        return wq + (1 / mu);
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
