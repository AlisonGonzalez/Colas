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
public class ModelMMsK {
    // Parámetros iniciales
    public double lambda, mu, ro, p0, pn, effectiveLambda;
    public double c0, cn;
    
    // Variables finales
    public double l, lq, w, wq;
    
    // s, K y capacidad
    public int servers, k, n;
    
    //Costo
    public double cw, cs, ct;
    
    // Modelo M/M/s/k
    public ModelMMsK(double _lambda, double _mu, int _n, int _servers, int _k, double _cw, double _cs){
        lambda = _lambda;
        mu = _mu;
        servers = _servers;
        ro = lambda / (servers * mu);
        n = _n;
        k = _k;
        cw = _cw;
        cs = _cs;
        calculate();
        ct = calculateCost();
    }
    
    // Estabilidad del sistema
    public boolean isStable() {
        return ro > 0 && ro < 1;
    }
    
    // M/M/s/K
    public void calculate(){
        if (isStable()) {
            cn = cn();
            p0 = p0();
            pn = pn();
            effectiveLambda = effectiveLambda();
            lq = lq();
            wq = wq();
            w = w();
            l = l();
        }
    }
    public double calculateCost(){
        return ((lq * cw) + (servers * cs));
    }
    
    // Lq
    private double lqMMs() { 
        double res = (p0 * Math.pow(lambda/mu, servers)*ro) / 
                (factorial(servers) * Math.pow(1-ro, 2));
        return res;
    }
    private double lq() {
        double res = lqMMs() * 
                (1-(Math.pow(ro, k - servers))-((k-servers)*
                (Math.pow(ro, k-servers))*
                (1-ro)));
        return res;
    }
    //L
    private double l(){
        return (effectiveLambda * w);
    }
    //Wq
    private double wq(){
        return (lq / effectiveLambda);
    }
    //W
    private double w(){
        return (wq + (1/mu));
    }
    // Cn
    private double cn() {
        double res = 0;
        if (n >= 0 && n < servers) {
            res = Math.pow(lambda/mu, n) / factorial(n);
        } else if (n <= k) {
            res = Math.pow(lambda/mu, n) / ((factorial(servers)
                    * Math.pow(servers, n - servers)));
        }
        return res;
    }
    
    // P0
    private double p0() {
        double res, sum = 0, sum2 = 0;
        for (int i = 0; i <= servers; i++) {
            sum += (Math.pow(lambda/mu, i) / factorial(i));
        }
        for (int j = servers + 1; j <= k; j++){
            sum2 += (Math.pow(ro, j - servers));
        }
        sum += ((Math.pow(lambda/mu, servers) / factorial(servers)) * sum2);
        res = 1 / sum;
        return res;
    }
    
    // Pn
    private double pn() { 
        if (n <= k) {
            return cn * p0;
        } else {
            return 0;
        }
    }
    
    // Lambda efectiva
    private double effectiveLambda(){
        double pk = (Math.pow(lambda/mu, k) / (factorial(servers)
                    * Math.pow(servers, k - servers))) * p0;
        double res = lambda * (1 - pk);
        return res;
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
