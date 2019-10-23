class SolveMatrix {

    static double[] GaussSimple(Matrix matrix, double[] b_vector){
        double[] resultVector = new double[matrix.getMatrix().length];
        double temp;

        for (int k = 0; k < matrix.getMatrix().length; ++k) {
            temp = matrix.getMatrix()[k][k];

            for (int j = 0; j < matrix.getMatrix().length; ++j) {
                matrix.getMatrix()[k][j] /= temp;
            }

            b_vector[k] /= temp;

            for (int i = k + 1; i < matrix.getMatrix().length; ++i) {
                temp = matrix.getMatrix()[i][k];
                for (int j = 0; j < matrix.getMatrix().length; ++j) {
                    matrix.getMatrix()[i][j] -= matrix.getMatrix()[k][j] * temp;
                }
                b_vector[i] -= b_vector[k] * temp;
            }
        }

        resultVector[matrix.getMatrix().length - 1] = b_vector[matrix.getMatrix().length - 1];

        for(int i = matrix.getMatrix().length - 2; i > -1; --i) {
            double sum = 0;
            for(int j = i + 1; j < matrix.getMatrix().length; ++j){
                sum += matrix.getMatrix()[i][j] * resultVector[j];
            }
            resultVector[i] = b_vector[i] - sum;
        }
        return resultVector;
    }

    static double[] GaussWithLU(Matrix matrix, double[] b_vector){
        double[] resultVector = new double[matrix.getMatrix().length];
        double temp = 0.0;

        Matrix L = new Matrix();
        Matrix U = new Matrix();

        for(int i = 0; i < matrix.getMatrix().length; ++i){
            for(int j = i; j < matrix.getMatrix().length; ++j){
                if(i == j)
                    U.getMatrix()[i][j] = 1;
                for(int k = 0; k < i; ++k){
                    temp += L.getMatrix()[j][k] * U.getMatrix()[k][i];
                }
                L.getMatrix()[j][i] = matrix.getMatrix()[j][i] - temp;
                temp = 0.0;
                for(int k = 0; k < i; ++k){
                    temp += L.getMatrix()[i][k] * U.getMatrix()[k][j];
                }
                U.getMatrix()[i][j] = (matrix.getMatrix()[i][j] - temp)/L.getMatrix()[i][i];
                temp = 0.0;
            }
        }

        double[] y;
        y = GaussSimple(L,b_vector);

        resultVector = GaussSimple(U,y);

        return  resultVector;
    }

    static double[] GaussMainElement(Matrix matrix, double[] b_vector){
        double[] resultVector;
        double temp;

            for(int i = 0; i < matrix.getMatrix().length; ++i){
                temp = matrix.getMatrix()[i][i];
                for(int j = i; j < matrix.getMatrix().length; ++j){
                    if (Math.abs(temp) < Math.abs(matrix.getMatrix()[i][j])){ //меняем столбцы
                        for(int k = 0; k < matrix.getMatrix().length; ++k){
                            double buffer = matrix.getMatrix()[k][j];
                            matrix.getMatrix()[k][j] = matrix.getMatrix()[k][i];
                            matrix.getMatrix()[k][i] = buffer;
                            buffer = matrix.getVarCoordinate()[k][i];
                            matrix.getVarCoordinate()[k][i] = matrix.getVarCoordinate()[k][j];
                            matrix.getVarCoordinate()[k][j] = (int)buffer;
                        }
                        temp = matrix.getMatrix()[i][i];
                    }
                    if (Math.abs(temp) < Math.abs(matrix.getMatrix()[j][i])){ //меняем строки
                        for(int k = 0; k < matrix.getMatrix().length; ++k){
                            double buffer = matrix.getMatrix()[k][j];
                            matrix.getMatrix()[j][k] = matrix.getMatrix()[i][k];
                            matrix.getMatrix()[i][k] = buffer;
                            buffer = matrix.getVarCoordinate()[k][i];
                            matrix.getVarCoordinate()[k][i] = matrix.getVarCoordinate()[k][j];
                            matrix.getVarCoordinate()[k][j] = (int)buffer;
                        }
                        temp = matrix.getMatrix()[i][i];
                    }
               }
            }

            resultVector = GaussSimple(matrix,b_vector);
            double[] absResultVector = new double[resultVector.length];

            for(int i = 0; i < resultVector.length; ++i)
                for(int j = 0; j < resultVector.length; ++j)
                    if(i == matrix.getVarCoordinate()[0][j])
                        absResultVector[i] = resultVector[j];

        return absResultVector;
    }
}
