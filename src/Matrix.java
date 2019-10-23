class Matrix {
    private double[][] matrix = new double[3][3];
    private int[][] varCoordinate = new int[3][3];

    Matrix() {
        for (int i = 0; i < matrix.length; ++i)
            for (int j = 0; j < matrix.length; ++j)
                matrix[i][j] = 0;
    }

    Matrix(double[][] matrix){
        for(int i = 0; i < matrix.length; ++i)
            for(int j = 0; j < matrix.length; ++j)
                for(int k = 0; k < 2; ++k)
                    varCoordinate[i][j] = j;
        this.matrix = matrix;
    }

    double[][] getMatrix(){
        return matrix;
    }

    Matrix getDiagonal() {
        double[][] diagonal = new double[3][3];
        for(int i = 0; i < matrix.length; ++i)
            for(int j = 0; j < matrix.length; ++j)
                {
                    if(i!=j){
                        diagonal[i][j] = 0;
                    } else {
                        diagonal[i][j] = matrix[i][j];
                    }
                }
        return new Matrix(diagonal);
    }

    int[][] getVarCoordinate(){
        return varCoordinate;
    }

    double getNorma(){
        double[] elementsSum = new double[matrix.length];
        for(int i = 0; i < matrix.length; ++i)
            elementsSum[i] = 0;

        for(int j = 0; j < matrix.length; ++j)
            for(int i = 0; i < matrix.length; ++i){
                elementsSum[j] += Math.abs(matrix[j][i]);  //| |
            }

        double max = 0;
        for(int i = 0; i < matrix.length; ++i)
            if(elementsSum[i] > max)
                max = elementsSum[i];
        return max;
    }



    double getConditionNumber(){
        return this.getNorma() * this.getInversion().getNorma();
    }

    void printMatrix(){
        for(int i = 0; i < matrix.length; ++i) {
            System.out.println();
            for (int j = 0; j < matrix.length; ++j)
                System.out.print(matrix[i][j] + " ");
        }
    }

    Matrix getInversion(){
        double temp;

        double [][] E = new double[matrix.length][matrix.length];

        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix.length; j++)
            {
                E[i][j] = 0d;

                if (i == j)
                    E[i][j] = 1d;
            }

        for (int k = 0; k < matrix.length; k++)
        {
            temp = matrix[k][k];

            for (int j = 0; j < matrix.length; j++)
            {
                matrix[k][j] /= temp;
                E[k][j] /= temp;
            }

            for (int i = k + 1; i < matrix.length; i++)
            {
                temp = matrix[i][k];

                for (int j = 0; j < matrix.length; j++)
                {
                    matrix[i][j] -= matrix[k][j] * temp;
                    E[i][j] -= E[k][j] * temp;
                }
            }
        }

        for (int k = matrix.length - 1; k > 0; k--)
        {
            for (int i = k - 1; i >= 0; i--)
            {
                temp = matrix[i][k];

                for (int j = 0; j < matrix.length; j++)
                {
                    matrix[i][j] -= matrix[k][j] * temp;
                    E[i][j] -= E[k][j] * temp;
                }
            }
        }

        return new Matrix(E);
    }
}
