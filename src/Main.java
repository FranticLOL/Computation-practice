public class Main {

    public static void main(String[] args) {
        double[][] a = {{-0.95121,-0.09779,0.35843},{-0.09779,0.61545, 0.02229},{0.35843, 0.022286,-0.95729}};
        Matrix A = new Matrix(a);
        Matrix bufA = new Matrix(a);
        double[] b = {-1.340873, 4.179164, 5.478007};

        double[] resultGauss = SolveMatrix.GaussMainElement(bufA,b);
        for(int i = 0; i < resultGauss.length; ++i)
            System.out.print(resultGauss[i] + " ");
        System.out.println();

        double[][] e = {{1,0,0},{0,1,0},{0,0,1}};
        Matrix E = new Matrix(e);
        double[] y_1 = {-1, 0, 1};

        double h = IterationMethods.maxEigenValPow(A,y_1,0.1,-1.31641);

        System.out.println(h);
        /*Matrix D = A.getDiagonal();
        Matrix diagD = D.getInversion();
        Matrix H_D = IterationMethods.minus(E,IterationMethods.multiply(diagD,A));
        double[] g_d = IterationMethods.multiply(diagD,b);
        double normaH_D = H_D.getNorma();
        //System.out.println(normaH_D);
        double[] result = IterationMethods.simpleIteration(10,H_D,g_d);
        for(int i = 0; i < result.length; ++i) {
            System.out.print(result[i] + " ");
        }
        System.out.println();

        double factEvaluating = IterationMethods.getNormaVector(IterationMethods.minus(resultGauss,result));
        System.out.println(factEvaluating);
        double apostEvaluating = (normaH_D*IterationMethods.getNormaVector(IterationMethods.minus(result,IterationMethods.simpleIteration(9,H_D,g_d))))/(1-normaH_D);
        System.out.println(apostEvaluating);

        double[] result_zeidel = IterationMethods.zeidelIteration(10,H_D,g_d);
        for(int i = 0; i < result.length; ++i) {
            System.out.print(result_zeidel[i] + " ");
        }
        System.out.println();

        double[] result_relax = IterationMethods.upperRelaxation(10,A,b);
        for(int i = 0; i < result.length; ++i) {
            System.out.print(result_relax[i] + " ");
        }*/
    }
}
