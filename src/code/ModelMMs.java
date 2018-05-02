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
public class ModelMMs {
    // Parámetros iniciales
    public double lambda, mu, ro, p0, pn;
    public double c0, cn;
    
    // Variables finales
    public double l, lq, w, wq;
    
    // s, K y capacidad
    public int servers, n;
    
    //Costo
    public double cw, cs, ct;
    
    // Modelo M/M/s
    public ModelMMs(double _lambda, double _mu, int _n, int _servers, double _cw, double _cs) {
        lambda = _lambda;
        mu = _mu;
        servers = _servers;
        ro = lambda / (servers * mu);
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
    
    // M/M/s
    public void calculate(){
        if (isStable()) {
            cn = calculateCn();
            p0 = calculateP0();
            pn = calculatePn();
            lq = lq();
            l = l();
            wq = wq();
            w = w();
        }
    }
    public double calculateCost(){
        return ((lq * cw) + (servers * cs));
    }
    
    // Lq
    private double lq() { 
        double res = (p0 * Math.pow(lambda/mu, servers)*ro) / 
                (factorial(servers) * Math.pow(1-ro, 2));
        return res;
    }
    // L
    private double l() {
        return lq + (lambda / mu);
    }
    // Wq
    private double wq() {
        return lq / lambda;
    }
    // W
    private double w() {
        return wq + (1/mu);
    }
    
    // Cn
    private double calculateCn() {
        double res = 1;
        if (n > 0 && n < servers) {
            res = Math.pow(lambda/mu, n) / factorial(n);
        } else if (n >= servers) {
            res = Math.pow(lambda/mu, n) / (factorial(servers)
                    * Math.pow(servers, n - servers));
        }
        return res;
    }
    
    // P0
    private double calculateP0() {
        double res, sum = 0;
        for (int i = 0; i < servers; i++) {
            sum += (Math.pow(lambda/mu, i) / factorial(i));
        }
        sum += ((Math.pow(lambda/mu, servers) / factorial(servers)) *
                    (1/(1-(lambda/(servers*mu)))));
        res = 1 / sum;
        return res;
    }
    
    // Pn
    private double calculatePn() { 
        return cn * p0;
    }
    
    // Factorial
    private int factorial(int n){
        if (n == 0) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }
}
