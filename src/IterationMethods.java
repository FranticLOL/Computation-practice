public class IterationMethods {
    public static Matrix multiply(Matrix A, Matrix B) {
        int m = A.getMatrix().length;
        int n = B.getMatrix()[0].length;
        int o = B.getMatrix().length;
        double[][] res = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < o; k++) {
                    res[i][j] += A.getMatrix()[i][k] * B.getMatrix()[k][j];
                }
            }
        }
        return new Matrix(res);
    }

    public static double[] multiply(Matrix A, double[] b) {
        int o = b.length;
        double[] res = new double[o];
        for (int i = 0; i < o; i++) {
            for (int k = 0; k < o; k++) {
                res[i] += A.getMatrix()[i][k] * b[k];
            }
        }
        return res;
    }

    public static Matrix sum(Matrix A, Matrix B) {
        double[][] sum = new double[A.getMatrix().length][A.getMatrix().length];
        for(int i = 0; i < sum.length; ++i)
            for (int j = 0; j < sum.length; ++j) {
                sum[i][j] = A.getMatrix()[i][j] + B.getMatrix()[i][j];
            }
        return new Matrix(sum);
    }

    public static Matrix minus(Matrix A, Matrix B) {
        double[][] sum = new double[A.getMatrix().length][A.getMatrix().length];
        for(int i = 0; i < sum.length; ++i)
            for (int j = 0; j < sum.length; ++j) {
                sum[i][j] = A.getMatrix()[i][j] - B.getMatrix()[i][j];
            }
        return new Matrix(sum);
    }

    public static double[] sum(double[] a, double[] b) {
        double[] sum = new double[a.length];
        for(int i = 0; i < a.length; ++i) {
            sum[i] = a[i] + b[i];
        }
        return sum;
    }

    public static double[] minus(double[] a, double[] b) {
        double[] sum = new double[a.length];
        for(int i = 0; i < a.length; ++i) {
            sum[i] = a[i] - b[i];
        }
        return sum;
    }

    public static double getNormaVector(double[] matrix){
        double max = 0;
        for(int i = 0; i < matrix.length; ++i)
            if(matrix[i] > max)
                max = matrix[i];
        return max;
    }

    public static double[] simpleIteration(int iteration, Matrix H_D, double[] g_d) {
        double[] x_0 = {0,0,0};
        double[] x_10 = {0,0,0};
        for(int i = 0; i < iteration; ++i){
            x_0 = x_10;
            x_10 = sum(multiply(H_D,x_0),g_d);
        }
        return x_10;
    }

    public static double[] zeidelIteration(int iteration, Matrix H_D, double[] g_d) {
        double[][] e = {{1,0,0},{0,1,0},{0,0,1}};
        Matrix E = new Matrix(e);
        int length = H_D.getMatrix().length;
        double[][] h_l = new double[length][length];
        double[][] h_r = new double[length][length];

        for(int i = 0; i < length; ++i){
            for(int j = 0; j < length; ++j){
                if(i <= j){
                    h_l[i][j] = 0;
                    h_r[i][j] = H_D.getMatrix()[i][j];
                } else{
                    h_l[i][j] = H_D.getMatrix()[i][j];
                    h_r[i][j] = 0;
                }
            }
        }

        double[] x_0;
        double[] x_10 = {0,0,0};
        for(int i = 0; i < iteration; ++i){
            x_0 = x_10;
            x_10 = sum(multiply(multiply(minus(E,new Matrix(h_l)).getInversion(),new Matrix(h_r)),x_0), multiply(minus(E,new Matrix(h_l)).getInversion(),g_d));
        }
        return x_10;
    }

    public static double[] upperRelaxation(int iteration, Matrix A, double[] b) {
        double q = 2/(1+Math.sqrt(1-0));
        double[] x_0 = {0,0,0};
        double[] x_10 = {0,0,0};
        for(int i = 0; i < iteration; ++i){
            x_0 = x_10;
            for(int j = 0; j < x_10.length; ++j) {
                double sum = 0;
                for(int k = 0; k < x_10.length; ++k){
                    if(k>=j) {
                        sum += A.getMatrix()[j][k] * x_0[k];
                    } else {
                        sum += A.getMatrix()[j][k] * x_10[k];
                    }
                }
                x_10[j] = x_0[j] + q * (b[j] - sum)/A.getMatrix()[j][j];
            }
        }
        return x_10;
    }

    static double maxEigenValPow(Matrix A, double[] y_1, double epsilon, double h_fact) {
        double h = 0.0;
        int k = 0;
        double[] h_i = new double[3];
        double[] y_k = y_1;
        while(Math.abs(Math.abs(h) - Math.abs(h_fact)) > epsilon){
            ++k;
            double[] y_next = multiply(A,y_k);
            for(int j = 0; j < y_1.length;++j){
                h_i[j] = y_next[j]/y_1[j];
            }
            for(int j = 0; j < h_i.length; ++j) {
                if(Math.abs(h_i[j]) > h)
                    h = Math.abs(h_i[j]);
            }
            y_k = y_next;
        }
        return h;
    }

}
